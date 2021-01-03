/**
 *  Copyright 2018 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition(name: "Z-Wave Fan Controller V2", namespace: "smartthings", author: "SmartThings", ocfDeviceType: "oic.d.fan", genericHandler: "Z-Wave") {
		capability "Fan Speed"
		capability "Switch Level"
		capability "Switch"
		capability "Health Check"
		capability "Actuator"
		capability "Refresh"
		//capability "Sensor"
		capability "Indicator"

		command "low"
		command "medium"
		command "high"
		command "raiseFanSpeed"
		command "lowerFanSpeed"

		fingerprint mfr: "001D", prod: "1001", model: "0334", deviceJoinName: "Leviton 3-Speed Fan Controller"
		fingerprint mfr: "0063", prod: "4944", model: "3034", deviceJoinName: "GE In-Wall Smart Fan Control"
		fingerprint mfr: "0063", prod: "4944", model: "3131", deviceJoinName: "GE In-Wall Smart Fan Control"
		fingerprint mfr: "0039", prod: "4944", model: "3131", deviceJoinName: "Honeywell Z-Wave Plus In-Wall Fan Speed Control"
	}

	simulator {
		status "00%": "command: 2003, payload: 00"
		status "33%": "command: 2003, payload: 21"
		status "66%": "command: 2003, payload: 42"
		status "99%": "command: 2003, payload: 63"
	}

}

def installed() {
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID, offlinePingable: "1"])
	response(refresh())
}

def updated(){
	// Device-Watch simply pings if no device events received for 32min(checkInterval)
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "zwave", hubHardwareId: device.hub.hardwareID])
  switch (ledIndicator) {
        case "on":
            indicatorWhenOn()
            break
        case "off":
            indicatorWhenOff()
            break
        case "never":
            indicatorNever()
            break
        default:
            indicatorWhenOn()
            break
    }
}

def parse(String description) {
	def result = null
	if (description != "updated") {
		log.debug "parse() >> zwave.parse($description)"
		def cmd = zwave.parse(description, [0x20: 1, 0x26: 1])
		if (cmd) {
			result = zwaveEvent(cmd)
		}
	}
	if (result?.name == 'hail' && hubFirmwareLessThan("000.011.00602")) {
		result = [result, response(zwave.basicV1.basicGet())]
		log.debug "Was hailed: requesting state update"
	} else {
		log.debug "Parse returned ${result?.descriptionText}"
	}
	return result
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicReport cmd) {
	fanEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.basicv1.BasicSet cmd) {
	fanEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelReport cmd) {
	fanEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.switchmultilevelv1.SwitchMultilevelSet cmd) {
	fanEvents(cmd)
}

def zwaveEvent(physicalgraph.zwave.commands.hailv1.Hail cmd) {
	log.debug "received hail from device"
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
	// Handles all Z-Wave commands we aren't interested in
	log.debug "Unhandled: ${cmd.toString()}"
	[:]
}

def fanEvents(physicalgraph.zwave.Command cmd) {
	def rawLevel = cmd.value as int
	def result = []

	if (0 <= rawLevel && rawLevel <= 100) {
		def value = (rawLevel ? "on" : "off")
		result << createEvent(name: "switch", value: value)
		result << createEvent(name: "level", value: rawLevel == 99 ? 100 : rawLevel)

		def fanLevel = 0

		// The GE, Honeywell, and Leviton treat 33 as medium, so account for that
		if (1 <= rawLevel && rawLevel <= 32) {
			fanLevel = 1
		} else if (33 <= rawLevel && rawLevel <= 66) {
			fanLevel = 2
		} else if (67 <= rawLevel && rawLevel <= 100) {
			fanLevel = 3
		}
		result << createEvent(name: "fanSpeed", value: fanLevel)
	}

	return result
}

def zwaveEvent(physicalgraph.zwave.commands.configurationv1.ConfigurationReport cmd) {
	log.debug "ConfigurationReport $cmd"
	def value = "when off"
	if (cmd.configurationValue[0] == 1) {value = "when on"}
	if (cmd.configurationValue[0] == 2) {value = "never"}
	createEvent([name: "indicatorStatus", value: value])
}

def on() {
	state.lastOnCommand = now()
	delayBetween([
			zwave.switchMultilevelV3.switchMultilevelSet(value: 0xFF).format(),
			zwave.switchMultilevelV1.switchMultilevelGet().format()
		], 5000)
}

def off() {
	delayBetween([
			zwave.switchMultilevelV3.switchMultilevelSet(value: 0x00).format(),
			zwave.switchMultilevelV1.switchMultilevelGet().format()
		], 1000)
}

def getDelay() {
	// the leviton is comparatively well-behaved, but the GE and Honeywell devices are not
	zwaveInfo.mfr == "001D" ? 2000 : 5000
}

def setLevel(value, rate = null) {
	def cmds = []
	def timeNow = now()

	if (state.lastOnCommand && timeNow - state.lastOnCommand < delay ) {
		// because some devices cannot handle commands in quick succession, this will delay the setLevel command by a max of 2s
		log.debug "command delay ${delay - (timeNow - state.lastOnCommand)}"
		cmds << "delay ${delay - (timeNow - state.lastOnCommand)}"
	}

	def level = value as Integer
	level = level == 255 ? level : Math.max(Math.min(level, 99), 0)
	log.debug "setLevel >> value: $level"

	cmds << delayBetween([
				zwave.switchMultilevelV3.switchMultilevelSet(value: level).format(),
				zwave.switchMultilevelV1.switchMultilevelGet().format()
			], 5000)

	return cmds
}

def setFanSpeed(speed) {
	if (speed as Integer == 0) {
		off()
	} else if (speed as Integer == 1) {
		low()
	} else if (speed as Integer == 2) {
		medium()
	} else if (speed as Integer == 3) {
		high()
	}
}

def raiseFanSpeed() {
	setFanSpeed(Math.min((device.currentValue("fanSpeed") as Integer) + 1, 3))
}

def lowerFanSpeed() {
	setFanSpeed(Math.max((device.currentValue("fanSpeed") as Integer) - 1, 0))
}

def low() {
	setLevel(32)
}

def medium() {
	setLevel(66)
}

def high() {
	setLevel(99)
}

def refresh() {
	zwave.switchMultilevelV1.switchMultilevelGet().format()
}

def ping() {
	refresh()
}

void indicatorWhenOn() {
	sendEvent(name: "indicatorStatus", value: "when on", displayed: false)
	sendHubCommand(new physicalgraph.device.HubAction(zwave.configurationV1.configurationSet(configurationValue: [1], parameterNumber: 3, size: 1).format()))
}

void indicatorWhenOff() {
	sendEvent(name: "indicatorStatus", value: "when off", displayed: false)
	sendHubCommand(new physicalgraph.device.HubAction(zwave.configurationV1.configurationSet(configurationValue: [0], parameterNumber: 3, size: 1).format()))
}

void indicatorNever() {
	sendEvent(name: "indicatorStatus", value: "never", displayed: false)
	sendHubCommand(new physicalgraph.device.HubAction(zwave.configurationV1.configurationSet(configurationValue: [2], parameterNumber: 3, size: 1).format()))
}