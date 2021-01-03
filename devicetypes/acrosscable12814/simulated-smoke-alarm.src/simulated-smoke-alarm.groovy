/**
 *  Copyright 2015 SmartThings
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
	definition (name: "Simulated Smoke Alarm", namespace: "acrosscable12814", author: "Dieter Rothhardt") {
		capability "Carbon Monoxide Detector"
		capability "Smoke Detector"
        capability "Switch"
        capability "Sensor"
        capability "refresh"
		capability "Health Check"

        command "carbon"
        command "smoke"
        command "test"
        command "clear"
	}

}

def refresh() {
	log.trace "Executing 'refresh'"
	initialize()
}

def installed() {
	log.trace "Executing 'installed'"
	initialize()
}

def updated() {
	log.trace "Executing 'updated'"
	initialize()
}

private initialize() {
	log.trace "Executing 'initialize'"

	sendEvent(name: "switch", value: "off")
    sendEvent(name: "smoke", value: "clear", descriptionText: "$device.displayName clear")
    sendEvent(name: "carbonMonoxide", value: "clear", descriptionText: "$device.displayName clear")

	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
}

def parse(String description) {
	
}

def carbon() {
	log.debug "smoke()"
	sendEvent(name: "carbonMonoxide", value: "detected", descriptionText: "$device.displayName CO detected!")
}

def smoke() {
	log.debug "smoke()"
	sendEvent(name: "smoke", value: "detected", descriptionText: "$device.displayName smoke detected!")
}

def test() {
	log.debug "test()"
	sendEvent(name: "smoke", value: "tested", descriptionText: "$device.displayName tested")
    sendEvent(name: "carbonMonoxide", value: "tested", descriptionText: "$device.displayName tested!")   
}

def clear() {
	log.debug "clear()"
	sendEvent(name: "smoke", value: "clear", descriptionText: "$device.displayName clear")
    sendEvent(name: "carbonMonoxide", value: "clear", descriptionText: "$device.displayName clear")
}

def on() {
	sendEvent(name: "switch", value: "on")
}

def off() {
	sendEvent(name: "switch", value: "off")
}