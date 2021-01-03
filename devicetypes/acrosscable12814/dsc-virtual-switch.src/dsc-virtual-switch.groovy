/**
 *  DSC Virtual Switch for STHM
 *
 *  Copyright 2020 DIETER ROTHHARDT
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
metadata {
	definition (
        name:   "DSC Virtual Switch",
        author: "Dieter Rothhardt",
        namespace: 'acrosscable12814',
        cstHandler: true
    ) 

	{
		capability "Switch"
        capability "Sensor"
        capability "Actuator"
        capability "Contact Sensor"
	}
}

// handle commands
def toggle(value) {
	log.debug("toggle: ${value}")
    if (value == "on") {
    	on()
    } else {
    	off()
    }
}

def on(value) {
	log.debug "Executing 'on'"
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "contact", value: "open")
    
    if (state.switchstate != "on") {
        def type = device.deviceNetworkId.minus('dscvswitch')
        if (type.contains("away")) {
            parent.sendUrl('arm')
            log.info ("away")
        } else {
            parent.sendUrl('stayarm')
            log.info ("stay")
        }
    }    
    state.switchstate = "on"
}

def off(value) {
	log.debug "Executing 'off'"
    sendEvent(name: "switch", value: "off")
    sendEvent(name: "contact", value: "closed")

    if (state.switchstate != "off") {
    	parent.sendUrl('disarm')
    }    
    state.switchstate = "off"
}


def updated() {
	initialize()
}

def initialize() {
    state.switchstate = "off"
    off()
}