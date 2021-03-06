/**
 *  Z-Wave Garage Door Opener
 *  Z-Wave Garage Door Opener Modified for GD00Z by @Garyd, Copied and modifed by @Ron
 *
 *  Copyright 2014 SmartThings
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
 

preferences {
    input name: "enableSwitch", type: "enum", title: "Enable Switch Capability?", options: ["Disabled","On=Open","On=Close"], description: "Enable Switch Capability?", required: true
}


metadata {
    //definition(name: "RG Linear GD00Z Garage Door Opener", namespace: "gouldner", author: "Ronald Gouldner") {
    definition(name: "RG Linear GD00Z Garage Door Opener - V1B", 
    ocfDeviceType:"oic.d.garagedoor", 
    namespace: "acrosscable12814", 
    author: "Dieter Rothhardt",
    vid: "1bc5e167-a09e-3739-882f-0f8f830ba97c",
    mnmn: "SmartThingsCommunity")
	{
    	capability "Garage Door Control"
        //capability "Actuator"
        //capability "Door Control"
        capability "Contact Sensor"
        capability "Refresh"
//        capability "Sensor"
        capability "Polling"
        capability "Switch"
        //capability "Momentary"
        //capability "Relay Switch"
        //capability "Battery"
		//capability "Lock"
        
        
        attribute "lastBatteryStatus", "STRING"
        attribute "batteryStatus", "STRING"
        command "batteryReset"

        fingerprint deviceId: "0x4007", inClusters: "0x98"
        fingerprint deviceId: "0x4006", inClusters: "0x98"
    }

    simulator {
        status "closed": "command: 9881, payload: 00 66 03 00"
        status "opening": "command: 9881, payload: 00 66 03 FE"
        status "open": "command: 9881, payload: 00 66 03 FF"
        status "closing": "command: 9881, payload: 00 66 03 FC"
        status "unknown": "command: 9881, payload: 00 66 03 FD"

        reply "988100660100": "command: 9881, payload: 00 66 03 FC"
        reply "9881006601FF": "command: 9881, payload: 00 66 03 FE"
    }
    
    tiles {
        standardTile("toggle", "device.door", width: 2, height: 2) {
            state("unknown", label: '${name}', action: "refresh.refresh", icon: "st.doors.garage.garage-open", backgroundColor: "#ffa81e")
            state("closed", label: '${name}', action: "door control.open", icon: "st.doors.garage.garage-closed", backgroundColor: "#79b821", nextState: "opening")
            state("open", label: '${name}', action: "door control.close", icon: "st.doors.garage.garage-open", backgroundColor: "#ffa81e", nextState: "closing")
            state("opening", label: '${name}', icon: "st.doors.garage.garage-opening", backgroundColor: "#ffe71e")
            state("closing", label: '${name}', icon: "st.doors.garage.garage-closing", backgroundColor: "#ffe71e")

        }
        /*  Display only tile no longer needed in V2 APP, Keeping tile in case they change the app again and I want it back
        standardTile("displayOnly", "device.door", width: 1, height: 1) {
            state("unknown", label: '${name}', icon: "st.doors.garage.garage-open", backgroundColor: "#ffa81e")
            state("closed", label: '${name}', icon: "st.doors.garage.garage-closed", backgroundColor: "#79b821")
            state("open", label: '${name}', icon: "st.doors.garage.garage-open", backgroundColor: "#ffa81e")
            state("opening", label: '${name}', icon: "st.doors.garage.garage-opening", backgroundColor: "#ffe71e")
            state("closing", label: '${name}', icon: "st.doors.garage.garage-closing", backgroundColor: "#ffe71e")

        }
        */
        standardTile("open", "device.door", inactiveLabel: false, decoration: "flat") {
            state "default", label: 'open', action: "door control.open", icon: "st.doors.garage.garage-opening"
        }
        standardTile("close", "device.door", inactiveLabel: false, decoration: "flat") {
            state "default", label: 'close', action: "door control.close", icon: "st.doors.garage.garage-closing"
        }
        standardTile("refresh", "device.door", inactiveLabel: false, decoration: "flat") {
            state "default", label: '', action: "refresh.refresh", icon: "st.secondary.refresh"
        }
        valueTile("batteryStatus", "device.batteryStatus", inactiveLabel: false, decoration: "flat") {
            state "batteryStatus", label: 'Battery ${currentValue}', unit: ""
        }
        // Last lastBatteryStatus Tile
        valueTile("lastBatteryStatus", "device.lastBatteryStatus", inactiveLabel: false, decoration: "flat") {
            state "lastBatteryStatus", label:'${currentValue}', unit:""
        }
        valueTile("batteryReset", "device.batteryStatus", inactiveLabel: false, decoration: "flat") {
            state "default", label: 'Battery\nReset', action: "batteryReset"
        }
        standardTile("button", "device.switch", width: 1, height: 1, canChangeIcon: true) {
            state "off", label: 'Off', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff", nextState: "on"
            state "on", label: 'On', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821", nextState: "off"
        }
        
        standardTile("version", "device.version", inactiveLabel: false, decoration: "flat") {
            state "version", label: 'v2.1'
        }

        main(["toggle"])
        details(["toggle", "open", "close", "lastBatteryStatus", "batteryStatus", "batteryReset", "button", "refresh","version"])
    }
}

import physicalgraph.zwave.commands.barrieroperatorv1.*
import physicalgraph.zwave.commands.doorlockv1.*


def parse(String description) {
    def result = null
    if (description.startsWith("Err")) {
        if (state.sec) {
            result = createEvent(descriptionText: description, displayed: false)
        } else {
            result = createEvent(
                    descriptionText: "This device failed to complete the network security key exchange. If you are unable to control it via SmartThings, you must remove it from your network and add it again.",
                    eventType: "ALERT",
                    name: "secureInclusion",
                    value: "failed",
                    displayed: true,
            )
        }
    } else {
        //def cmd = zwave.parse(description, [0x98: 1, 0x72: 2])
        def cmd = zwave.parse(description, [ 0x98: 1, 0x62: 1, 0x63: 1, 0x71: 2, 0x72: 2, 0x80: 1, 0x85: 2, 0x86: 1 ])
        if (cmd) {
            result = zwaveEvent(cmd)
        }
    }
    log.debug "\"$description\" parsed to ${result.inspect()}"
    result
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityMessageEncapsulation cmd) {
    def encapsulatedCommand = cmd.encapsulatedCommand([0x71: 3, 0x80: 1, 0x85: 2, 0x63: 1, 0x98: 1])
    log.debug "encapsulated: $encapsulatedCommand"
    if (encapsulatedCommand) {
        zwaveEvent(encapsulatedCommand)
    }
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.NetworkKeyVerify cmd) {
    createEvent(name: "secureInclusion", value: "success", descriptionText: "Secure inclusion was successful")
}

def zwaveEvent(physicalgraph.zwave.commands.securityv1.SecurityCommandsSupportedReport cmd) {
    state.sec = cmd.commandClassSupport.collect { String.format("%02X ", it) }.join()
    if (cmd.commandClassControl) {
        state.secCon = cmd.commandClassControl.collect { String.format("%02X ", it) }.join()
    }
    log.debug "Security command classes: $state.sec"
    createEvent(name: "secureInclusion", value: "success", descriptionText: "$device.displayText is securely included")
}

def zwaveEvent(BarrierOperatorReport cmd) {
    def result = []
    def map = [name: "door"]
    def switchMap = [name: "switch"]

    switch (cmd.barrierState) {
        case BarrierOperatorReport.BARRIER_STATE_CLOSED:
            map.value = "closed"
            result << createEvent(name: "contact", value: "closed", displayed: false)
            result << createEvent(name: "switch", value: "off", displayed: false)
            result << createEvent(name: "lock", value: "locked", displayed: false)
            break
        case BarrierOperatorReport.BARRIER_STATE_UNKNOWN_POSITION_MOVING_TO_CLOSE:
            map.value = "closing"
            break
        case BarrierOperatorReport.BARRIER_STATE_UNKNOWN_POSITION_STOPPED:
            map.descriptionText = "$device.displayName door state is unknown"
            map.value = "unknown"
            break
        case BarrierOperatorReport.BARRIER_STATE_UNKNOWN_POSITION_MOVING_TO_OPEN:
            map.value = "opening"
            break
        case BarrierOperatorReport.BARRIER_STATE_OPEN:
            map.value = "open"
            result << createEvent(name: "contact", value: "open", displayed: false)
            result << createEvent(name: "switch", value: "on", displayed: false)
            result << createEvent(name: "lock", value: "unlocked", displayed: false)
            break
    }
    result + createEvent(map)
}

/**
 * Responsible for parsing DoorLockOperationReport command
 *
 * @param cmd: The DoorLockOperationReport command to be parsed
 *
 * @return The event(s) to be sent out
 *
 */
def zwaveEvent(DoorLockOperationReport cmd) {
	def result = []

	def map = [ name: "lock" ]
	map.data = [ lockName: device.displayName ]

		map.value = "locked"
		map.descriptionText = "Locked"
		return result ? [createEvent(map), *result] : createEvent(map)

}

def zwaveEvent(physicalgraph.zwave.commands.notificationv3.NotificationReport cmd) {
    log.debug "Aquiring Notification Report cmd=$cmd"
    def result = []
    def map = [:]
    if (cmd.notificationType == 6) {
        map.displayed = true
        switch (cmd.event) {
            case 0x40:
                if (cmd.eventParameter[0]) {
                    map.descriptionText = "$device.displayName performing initialization process"
                } else {
                    map.descriptionText = "$device.displayName initialization process complete"
                }
                break
            case 0x41:
                map.descriptionText = "$device.displayName door operation force has been exceeded"
                break
            case 0x42:
                map.descriptionText = "$device.displayName motor has exceeded operational time limit"
                break
            case 0x43:
                map.descriptionText = "$device.displayName has exceeded physical mechanical limits"
                break
            case 0x44:
                map.descriptionText = "$device.displayName unable to perform requested operation (UL requirement)"
                break
            case 0x45:
                map.descriptionText = "$device.displayName remote operation disabled (UL requirement)"
                break
            case 0x46:
                map.descriptionText = "$device.displayName failed to perform operation due to device malfunction"
                break
            case 0x47:
                if (cmd.eventParameter[0]) {
                    map.descriptionText = "$device.displayName vacation mode enabled"
                } else {
                    map.descriptionText = "$device.displayName vacation mode disabled"
                }
                break
            case 0x48:
                if (cmd.eventParameter[0]) {
                    map.descriptionText = "$device.displayName safety beam obstructed"
                } else {
                    map.descriptionText = "$device.displayName safety beam obstruction cleared"
                }
                break
            case 0x49:
                if (cmd.eventParameter[0]) {
                    map.descriptionText = "$device.displayName door sensor ${cmd.eventParameter[0]} not detected"
                } else {
                    map.descriptionText = "$device.displayName door sensor not detected"
                }
                break
            case 0x4A:
                if (cmd.eventParameter[0]) {
                    map.descriptionText = "$device.displayName door sensor ${cmd.eventParameter[0]} has a low battery"
                } else {
                    map.descriptionText = "$device.displayName door sensor has a low battery"
                }
                result << createEvent(name: "batteryStatus", value: "LOW", descriptionText: map.descriptionText)
                def now=new Date()
                def tz = location.timeZone
                def nowString = "Low:" + now.format("MMM/dd HH:mm",tz)
                result << createEvent(name:"lastBatteryStatus", value:nowString, descriptionText: map.descriptionText)
                break
            case 0x4B:
                map.descriptionText = "$device.displayName detected a short in wall station wires"
                break
            case 0x4C:
                map.descriptionText = "$device.displayName is associated with non-Z-Wave remote control"
                break
            default:
                map.descriptionText = "$device.displayName: access control alarm $cmd.event"
                map.displayed = false
                break
        }
    } else if (cmd.notificationType == 7) {
        switch (cmd.event) {
            case 1:
            case 2:
                map.descriptionText = "$device.displayName detected intrusion"
                break
            case 3:
                map.descriptionText = "$device.displayName tampering detected: product cover removed"
                break
            case 4:
                map.descriptionText = "$device.displayName tampering detected: incorrect code"
                break
            case 7:
            case 8:
                map.descriptionText = "$device.displayName detected motion"
                break
            default:
                map.descriptionText = "$device.displayName: security alarm $cmd.event"
                map.displayed = false
        }
    } else if (cmd.notificationType) {
        map.descriptionText = "$device.displayName: alarm type $cmd.notificationType event $cmd.event"
    } else {
        map.descriptionText = "$device.displayName: alarm $cmd.v1AlarmType is ${cmd.v1AlarmLevel == 255 ? 'active' : cmd.v1AlarmLevel ?: 'inactive'}"
    }
    result ? [createEvent(map), *result] : createEvent(map)
}

// RRG 1/8/2016
// This never gets called, I am pretty sure Linear doesn't support betteryGet
def zwaveEvent(physicalgraph.zwave.commands.batteryv1.BatteryReport cmd) {
    log.debug "Battery Reporting cmd=$cmd"
    def map = [name: "battery", unit: "%"]
    if (cmd.batteryLevel == 0xFF) {
        map.value = 1
        log.debug "Battery Level=low=1"
        map.descriptionText = "$device.displayName has a low battery"
    } else {
        log.debug "Battery Level=cmd.batteryLevel"
        map.value = cmd.batteryLevel
    }
    state.lastbatt = new Date().time
    createEvent(map)
}


def zwaveEvent(physicalgraph.zwave.commands.manufacturerspecificv2.ManufacturerSpecificReport cmd) {
    def result = []

    def msr = String.format("%04X-%04X-%04X", cmd.manufacturerId, cmd.productTypeId, cmd.productId)
    log.debug "msr: $msr"
    updateDataValue("MSR", msr)

    result << createEvent(descriptionText: "$device.displayName MSR: $msr", isStateChange: false)
    result
}

def zwaveEvent(physicalgraph.zwave.commands.versionv1.VersionReport cmd) {
    log.debug "cmd:$cmd"
    def fw = "${cmd.applicationVersion}.${cmd.applicationSubVersion}"
    updateDataValue("fw", fw)
    def text = "$device.displayName: firmware version: $fw, Z-Wave version: ${cmd.zWaveProtocolVersion}.${cmd.zWaveProtocolSubVersion}"
    createEvent(descriptionText: text, isStateChange: false)
}

def zwaveEvent(physicalgraph.zwave.commands.applicationstatusv1.ApplicationBusy cmd) {
    def msg = cmd.status == 0 ? "try again later" :
            cmd.status == 1 ? "try again in $cmd.waitTime seconds" :
                    cmd.status == 2 ? "request queued" : "sorry"
    createEvent(displayed: true, descriptionText: "$device.displayName is busy, $msg")
}

def zwaveEvent(physicalgraph.zwave.commands.powerlevelv1.PowerlevelReport cmd) {
    log.debug "Power Level Report cmd=$cmd"
    createEvent(displayed: true, descriptionText: "$device.displayName rejected the last request")
}

def zwaveEvent(physicalgraph.zwave.commands.applicationstatusv1.ApplicationRejectedRequest cmd) {
    createEvent(displayed: true, descriptionText: "$device.displayName rejected the last request")
}

def zwaveEvent(physicalgraph.zwave.commands.applicationcapabilityv1.CommandCommandClassNotSupported cmd) {
    log.debug "Command Class Not Supported cmd:$cmd"
}

def zwaveEvent(physicalgraph.zwave.Command cmd) {
    createEvent(displayed: false, descriptionText: "$device.displayName: $cmd")
}

def open() {
    secure(zwave.barrierOperatorV1.barrierOperatorSet(requestedBarrierState: BarrierOperatorSet.REQUESTED_BARRIER_STATE_OPEN))
}

def close() {
    secure(zwave.barrierOperatorV1.barrierOperatorSet(requestedBarrierState: BarrierOperatorSet.REQUESTED_BARRIER_STATE_CLOSE))
}

def on() {
    log.debug "on() was called treat this like Open"
    open()
}

def off() {
    log.debug "off() was called treat like Close"
    close()
}

def unlock() {
    log.debug "unlock() was called treat this like Open"
    open()
}

def lock() {
    log.debug "lock() was called treat like Close"
    close()
}

def refresh() {
    //secure(zwave.barrierOperatorV1.barrierOperatorGet())
    /* BatteryGet and NotificationGet not working */
    log.debug "Issuing Refresh (barrier state, and version report to log)"
    secureSequence([
                zwave.barrierOperatorV1.barrierOperatorGet()
                ,zwave.versionV1.versionGet()
                //,zwave.powerlevelV1.powerlevelGet()
                //,zwave.notificationV3.notificationGet()
                //,zwave.notificationV3.notificationSupportedGet()
        ], 4200)
    /* */
}

def poll() {
    secure(zwave.barrierOperatorV1.barrierOperatorGet())
}

def batteryReset() {
    log.debug "Battery Reset"
    def now=new Date()
    def tz = location.timeZone
    def nowString = "RESET:" + now.format("MMM/dd HH:mm",tz)
    sendEvent("name": "batteryStatus", "value":"OK", "descriptionText":"Battery Reset to OK")
    sendEvent("name":"lastBatteryStatus", "value":nowString)
}

def push() {

    // get the current "door" attribute value
    //
    // For some reason, I can't use "device.doorState" or just "doorState".  Not sure why not.

    def lastValue = device.latestValue("door");

    // if its open, then close the door
    if (lastValue == "open") {
        return close()

        // if its closed, then open the door
    } else if (lastValue == "closed") {
        return open()

    } else {
        log.debug "push() called when door state is $lastValue - there's nothing push() can do"
    }
}

private secure(physicalgraph.zwave.Command cmd) {
    zwave.securityV1.securityMessageEncapsulation().encapsulate(cmd).format()
}

private secureSequence(commands, delay = 200) {
    delayBetween(commands.collect { secure(it) }, delay)
}