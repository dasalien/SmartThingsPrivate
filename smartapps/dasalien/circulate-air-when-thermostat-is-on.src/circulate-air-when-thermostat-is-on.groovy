/**
 *  Whole House Fan
 *
 *  Copyright 2014 Brian Steere
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
definition(
    name: "Circulate Air when Thermostat is on",
    namespace: "dasalien",
    author: "Dieter Rothhardt",
    description: "Switch on fan devices when the thermostat kicks on. Remember previous setting",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Developers/whole-house-fan.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Developers/whole-house-fan%402x.png"
)


preferences {
    section("Fans") {
        input "fans", "capability.switchLevel", title: "Ceiling Fan", multiple: true
    }
    
    section("Thermostat") {
    	input "thermostat", "capability.thermostat", title: "Thermostat"
    }    
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
    subscribe(thermostat, "thermostatMode", runFans);
    state.level0 = 0
    state.level1 = 0
    state.level2 = 0
    state.level3 = 0
    state.state0 = 0
    state.state1 = 0
    state.state2 = 0
    state.state3 = 0
}

def runFans(evt) {
    def thermostatMode = settings.thermostat.currentValue('thermostatMode')
	def fanlevel = fans*.currentValue('level')
	def fanstate = fans*.currentValue('switch')
    
    log.debug "Thermostat: $thermostatMode"

    if(thermostatMode != 'off') {
		//Remember current status
		fanlevel.eachWithIndex { val, idx ->
            //There sure is a better way..
            if (idx == 0) {
            	state.level0 = val
                state.state0 = fanstate.getAt(idx)
                fans[idx].setLevel(80)
                fans[idx].on()
            }
            if (idx == 1) {
            	state.level1 = val
                state.state1 = fanstate.getAt(idx)
                fans[idx].setLevel(80)
                fans[idx].on()
            }
            if (idx == 2) {
            	state.level2 = val
                state.state2 = fanstate.getAt(idx)
                fans[idx].setLevel(80)
                fans[idx].on()
            }
            if (idx == 3) {
            	state.level3 = val
                state.state3 = fanstate.getAt(idx)
                fans[idx].setLevel(80)
                fans[idx].on()
            }           
		}
    } else {
    	log.debug "Not running due to thermostat mode"
        //Restore previous settings
		fanlevel.eachWithIndex { val, idx ->
            //There sure is a better way..
            if (idx == 0) {
                fans[idx].setLevel(state.level0)
                if(state.state0 == "on") {
                	fans[idx].on()
                } else {
                	fans[idx].off()
                }
            }
            if (idx == 1) {
            	fans[idx].setLevel(state.level1)
                if(state.state1 == "on") {
                	fans[idx].on()
                } else {
                	fans[idx].off()
                }
            }
            if (idx == 2) {
            	fans[idx].setLevel(state.level2)
                if(state.state2 == "on") {
                	fans[idx].on()
                } else {
                	fans[idx].off()
                }
            }
            if (idx == 3) {
            	fans[idx].setLevel(state.level3)
                if(state.state3 == "on") {
                	fans[idx].on()
                } else {
                	fans[idx].off()
                }
            }
		}
    }
}