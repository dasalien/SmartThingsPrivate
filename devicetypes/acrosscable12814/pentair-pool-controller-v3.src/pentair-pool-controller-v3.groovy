/**
 *  Pentair Pool Controller - eeddir
 *
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
import java.text.SimpleDateFormat
 

metadata {
	definition (name: "Pentair Pool Controller V3", namespace: "acrosscable12814", author: "Dieter Rothhardt", ocfDeviceType:"oic.d.thermostat", oauth: true, vid: "931659a0-dcfa-3550-a59d-df60e5a88277", mnmn: "SmartThingsCommunity") 
    {//oic.r.temperature

		//capability
        capability "Switch"
    	capability "Polling"        
        capability "Refresh"
        capability "Temperature Measurement"
		capability "acrosscable12814.operatingMode"
		capability "acrosscable12814.poolTemperature"
        capability "acrosscable12814.spaTemperature"
		capability "acrosscable12814.poolHeatMode"
        capability "acrosscable12814.spaHeatMode"
		capability "acrosscable12814.poolHeaterSetpoint"
        capability "acrosscable12814.spaHeaterSetpoint"
		capability "acrosscable12814.freezeProtection"
        capability "acrosscable12814.heaterOperation"
        capability "acrosscable12814.poolLight"
        capability "acrosscable12814.spaLight"
        capability "acrosscable12814.cleaningRobot"
        capability "acrosscable12814.scheduleList"
        capability "acrosscable12814.chlorinatorOperatingState"
        capability "acrosscable12814.chlorinatorSaltLevel"
        capability "acrosscable12814.chlorinatorCurrentOutput"
        capability "acrosscable12814.chlorinatorSetpoint"
        capability "acrosscable12814.chlorinatorSuperChlorination"
        capability "acrosscable12814.chlorinatorStatus"
        capability "acrosscable12814.updateDateAndTime"
//        capability "momentary"
        
		//commands
        command "toggleMasterControl" , ["string"]
        command "togglePoolOperatingMode" , ["string"]
        command "togglePoolLight" , ["string"]
        command "toggleSpaLight" , ["string"]
        

        
		//attribute
        attribute "masterState", "enum", ["on", "off"]
        attribute "poolOperatingMode", "enum", ["pool", "spa"]
        attribute "poolLightState", "enum", ["on", "off"]
        attribute "spaLightState", "enum", ["on", "off"]
	}
   
    preferences {
       	section("Select your controller") {
       		input "controllerIP", type:"text", title: "Controller IP address", required: true, displayDuringSetup: true
       		input "controllerPort", type:"number", title: "Controller port", required: true, defaultValue: "3000", displayDuringSetup: true
            input "temperatureUnit", type:"text", title: "Temperature Unit (C/F/auto)", required: false, defaultValue: "auto", displayDuringSetup: true
            input "updatePoolTimeAndDate", type:"boolean", title: "Daily update of Controller Time and Date", required: false, defaultValue: "false", displayDuringSetup: true
		}
    }
    
	simulator {
	}

	tiles {
	}

}

// GUI Element Actions
def updateDateAndTime() {
	log.debug("updateDateAndTime")
}

def setUpdateDateAndTime() {
	log.debug("updateDateAndTime")
}

def push() {
	log.debug("updateDateAndTime")
}

def on() {
	sendEvent(name: "toggleMasterControl", value: "on")
    def index = state.circuit["POOL"]
    if(state.operatingMode == "Spa") {
    	index = state.circuit["SPA"]
    }
    sendCommandCallBack("/circuit/${index}/set/1",'parseAction')
}

def off() {
    sendEvent(name: "toggleMasterControl", value: "off")
    def index = state.circuit["SPA"]
    sendCommandCallBack("/circuit/${index}/set/0",'parseAction')
    index = state.circuit["POOL"]
    sendCommandCallBack("/circuit/${index}/set/0",'parseAction')
}

def toggleMasterControl(value) {
	sendEvent(name: "masterState", value: value)
    log.debug("masterControl: ${value}")
    if (value == "on") {
		log.debug("masterControl: IS ON")
	} else {
    	log.debug("masterControl: IS OFF")
    }
}

def setOperatingMode(mode) {
    sendEvent(name: "togglePoolOperatingMode", value: mode)
    log.debug("setOperatingMode: ${mode}")
    state.operatingMode = mode    
    if(mode == "Spa") {
    	def index = state.circuit["SPA"]
    	sendCommandCallBack("/circuit/${index}/set/1",'parseAction')
        log.debug("setOperatingMode: IDX ${index}")
    	index = state.circuit["POOL"]
    	sendCommandCallBack("/circuit/${index}/set/0",'parseAction')
        log.debug("setOperatingMode: IDX ${index}")
	} else {
    	def index = state.circuit["POOL"]
    	sendCommandCallBack("/circuit/${index}/set/1",'parseAction')
        log.debug("setOperatingMode: IDX ${index}")
    	index = state.circuit["SPA"]
        log.debug("setOperatingMode: IDX ${index}")
    	sendCommandCallBack("/circuit/${index}/set/0",'parseAction')
	}    
}

def togglePoolOperatingMode(mode) {
	sendEvent(name: "poolOperatingMode", value: mode)
    log.debug("poolOperatingMode: ${mode}")
}

def setPoolHeatMode(mode) {
    sendCommandCallBack("/poolheat/mode/${state.heaterMapI[mode]}",'parseAction')
}

def setSpaHeatMode(mode) {
    sendCommandCallBack("/spaheat/mode/${state.heaterMapI[mode]}",'parseAction')
}

def setPoolHeaterSetpoint(setpoint) {
	log.debug("setPoolHeaterSetpoint")
    log.debug(setpoint)
    int setpointI = setpoint
    if(state.uom == "Fahrenheit") {
    	setpointI = celsiusToFahrenheit(setpoint)        
    }
    log.debug(setpointI)
    sendCommandCallBack("/poolheat/setpoint/${setpointI}",'parseAction')    
}

def setSpaHeaterSetpoint(setpoint) {
	log.debug("setSpaHeaterSetpoint")
    log.debug(setpoint)
    int setpointI = setpoint
    if(state.uom == "Fahrenheit") {
    	setpointI = celsiusToFahrenheit(setpoint)
    }
    log.debug(setpointI)
    sendCommandCallBack("/spaheat/setpoint/${setpointI}",'parseAction')
}

def setPoolLight(mode) {
    sendCommand(name: "togglePoolLight", value: mode)
    def index = state.circuit["POOL LIGHT"]
    sendCommandCallBack("/circuit/${index}/set/${state.onOffI[mode]}",'parseAction')
}

def togglePoolLight(mode) {
	sendEvent(name: "poolLight", value: mode)
    log.debug("togglePoolLight: ${mode}")
}

def setSpaLight(mode) {
    sendEvent(name: "toggleSpaLight", value: mode)
    def index = state.circuit["SPA LIGHT"]
    sendCommandCallBack("/circuit/${index}/set/${state.onOffI[mode]}",'parseAction')
}

def toggleSpaLight(mode) {
	sendEvent(name: "spaLight", value: mode)
    log.debug("toggleSpaLight: ${mode}")
}

def setCleaningRobot(mode) {
    def index = state.circuit["CLEANER"]    
    sendCommandCallBack("/circuit/${index}/set/${state.onOffI[mode]}",'parseAction')
}

def setChlorinatorOperatingState(mode) {
	log.debug("setChlorinatorOperatingState")
    log.debug(mode)
    sendEvent(name: "chlorinatorOperatingState", value: mode)
    //sendCommandCallBack("/spaheat/setpoint/${setpoint}",'parseAction')
}


// parse routines
def parseChlor(physicalgraph.device.HubResponse hubResponse) {
	//log.debug "------------- parseChlor -------------"
    
	def json = hubResponse.json
    def name
    def val

	state.chlorinator = json.chlorinator    
    json.chlorinator.each {k, v ->        	         
         //log.debug "Chlor Key:${k}  Val:${v}"
         name = k;
         val = v;         
         switch (k) {
        	case "outputPoolPercent":
            	def sp = json.chlorinator.outputSpaPercent + "%"
            	String text = "${v}% / " + sp
                sendEvent(name: "chlorinatorSetpoint", value: "Pool: ${v}% / Spa: ${sp}", unit: "")//"${val}", unit: "%"")
            	break; 
            case "superChlorinate":
            	val = v ? "yes": "no"
                String sc_hrs = json.chlorinator.superChlorinateHours
                if(val == "yes") {
                	val = sc_hrs
				}                	
                sendEvent(name: "superChlorinate", value: val)
            	break
            case "status":
				sendEvent(name: "chlorinatorStatus", value: v)
            	break
            case "saltPPM":
                sendEvent(name: "chlorinatorSaltLevel", value: "${v} ppm", unit: "")
            	break
            case "currentOutput":
                String sw = v==0 ? "Off":"Active"
                def cur = v + "%"
				sendEvent(name: "chlorinatorOperatingState", value: sw)
				sendEvent(name: "chlorinatorCurrentOutput", value: "- ${cur} -", unit: "")
                break;
        }
	}
}


def parseAction(physicalgraph.device.HubResponse hubResponse) {
	//log.debug "------------- parseAction -------------"  
	def json = hubResponse.json
	def celsiusUnit = "°C" //°
    def fahrenheitUnit = "°F"
    def unit = temperatureUnit == "C" ? celsiusUnit : fahrenheitUnit

	if (json) {
        //log.debug "text: ${json.text}"
//		log.debug "status: ${json.status}"
//		log.debug "value: ${json.value}"
              
		if (json.text.contains("POOL LIGHT")) { 
			sendEvent(name: "poolLight", value: json.status)
		} else if (json.text.contains("SPA LIGHT")) {
		    sendEvent(name: "spaLight", value: json.status)
		} else if (json.text.contains("CLEANER")) {
		    sendEvent(name: "cleaningRobot", value: json.status)
		} else if (json.text.contains("Request to set spa heat mode to")) {
			state.spaMode = json.value
			state.spaStatus = json.status
            sendEvent(name: "spaHeatMode", value: state.heaterMap["${json.value}"])
		} else if (json.text.contains("User request to update spa heat set point")) {
			state.spaStatus = json.status
			state.spaSetPoint = json.value
            def spaSetPoint = state.spaSetPoint
			if (temperatureUnit == "auto") {
		    	unit = celsiusUnit
			    spaSetPoint = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(state.spaSetPoint) : state.spaSetPoint).toDouble().round(0)
			}
			sendEvent(name: "spaHeaterSetpoint", value: spaSetPoint, unit: unit)
		} else if (json.text.contains("Request to set spa heat setpoint")) {
			state.spaStatus = json.status
			state.spaSetPoint = json.value
            def spaSetPoint = state.spaSetPoint
			if (temperatureUnit == "auto") {
		    	unit = celsiusUnit
			    spaSetPoint = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(state.spaSetPoint) : state.spaSetPoint).toDouble().round(0)
			}
			sendEvent(name: "spaHeaterSetpoint", value: spaSetPoint, unit: unit)
		} else if (json.text.contains("Request to set pool heat mode to")) {
			state.poolStatus = json.status
			state.poolMode = json.value
            sendEvent(name: "poolHeatMode", value: state.heaterMap["${json.value}"])
		} else if (json.text.contains("User request to update pool heat set point")) {
			state.poolStatus = json.status
			state.poolSetPoint = json.value
            def poolSetPoint = state.poolSetPoint
			if (temperatureUnit == "auto") {
		    	unit = celsiusUnit
			    poolSetPoint = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(state.poolSetPoint) : state.poolSetPoint).toDouble().round(0)
			}
			sendEvent(name: "poolHeaterSetpoint", value: poolSetPoint, unit: unit)
		} else if (json.text.contains("Request to set pool heat setpoint")) {
			state.poolStatus = json.status
			state.poolSetPoint = json.value
            def poolSetPoint = state.poolSetPoint
			if (temperatureUnit == "auto") {
		    	unit = celsiusUnit
			    poolSetPoint = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(state.poolSetPoint) : state.poolSetPoint).toDouble().round(0)
			}
			sendEvent(name: "poolHeaterSetpoint", value: poolSetPoint, unit: unit)
       } else if (json.text.contains("SPA to")) {
			log.info "SPA toggle text: ${json.text}"
			def mode = json.value?.is(1) ? "on" : "off"
            if(mode == "on") {
            	state.operatingMode = "Spa"
				sendEvent(name: "operatingMode", value: "Spa")
            	log.info "SPA toggle, switch: ${mode}"
            	state.pumpStatus = mode
            	sendEvent(name: "switch", value: mode)
            }
       } else if (json.text.contains("POOL to")) {
			log.info "POOL toggle text: ${json.text}"
			def mode = json.value?.is(1) ? "on" : "off"
            state.operatingMode = "Pool"
            if(mode == "on") {
				sendEvent(name: "operatingMode", value: "Pool")
				log.info "POOL toggle, switch: ${mode}"
        	    state.pumpStatus = mode
				sendEvent(name: "switch", value: mode)
            }
       }
   }
}


def parseCircuit(physicalgraph.device.HubResponse hubResponse) {
    //log.debug "------------- parseCircuit -------------"  

	def json = hubResponse.json
    if (!json) {
       log.error "parsed lan message was nil"
       def evt = createEvent(name: "refresh", isStateChange: "true", value: "Idle")
       return evt
    } 

	def circuits = json.circuit
	def pumpStatus = "off"
	json.circuit.keySet().each {
		def c = json.circuit[it]        
		if (c.name == "SPA LIGHT") {
            sendEvent(name: "spaLight", value: state.onOff["${c.status}"]) 
		}
		if (c.name == "POOL LIGHT") {
            sendEvent(name: "poolLight", value: state.onOff["${c.status}"]) 
		}
		if (c.name == "POOL") {
        	//Check that SPA is not is already running. Double indication
            if(pumpStatus == "off") {
                if(c.status == 1) {
                    pumpStatus = "on"
                    state.operatingMode = "Pool"
                    sendEvent(name: "operatingMode", value: "Pool") 
                }
			}
		}
		if (c.name == "SPA") {
            if(pumpStatus == "off") {
                if(c.status == 1) {
                    pumpStatus = "on"
                    state.operatingMode = "Spa"
                    sendEvent(name: "operatingMode", value: "Spa")
                }
            }
		}
		if (c.name == "CLEANER") {
            sendEvent(name: "cleaningRobot", value: state.onOff["${c.status}"]) 
		}
        state.circuit[c.name] = c.number
	}
    if(state.pumpStatus != pumpStatus) {
        log.info "Circuit, switch: ${pumpStatus}"
        sendEvent(name: "switch", value: pumpStatus)
        state.pumpStatus = pumpStatus
    }
}


def parseUOM(physicalgraph.device.HubResponse hubResponse) {
	//log.debug "------------- parseUOM -------------"
    
	def json = hubResponse.json
    //log.debug json
	if (json.UOM != null) {
       	state.uom = json.UOM.UOMStr
        log.debug(state.uom) 
    }
}

def parseTemperature(physicalgraph.device.HubResponse hubResponse) {
	//log.debug "------------- parseTemperature -------------"
	def json = hubResponse.json
    state.temperature = json.temperature	
	def temperatures = json.temperature
    
    log.debug("Pool State: ${state.operatingMode}")
    if (state.operatingMode == "Pool") {
    	state.poolTempLK = temperatures.poolTemp
    }
	log.debug("Incoming Pool Temp: ${temperatures.poolTemp}")
	log.debug("Considered Pool Temp: ${state.poolTempLK}")
    
    def freeze = temperatures.freeze?.is(1) ? "Freeze Protection" : "Off"
    def heater = temperatures.heaterActive?.is(1) ? "Heating" : "Off"
//    def tempPoolTemp = state.SPAstate?.is(1) ? state.poolTempLK : temperatures.poolTemp

	def celsiusUnit = "C" //°
    def fahrenheitUnit = "F"
    def unit = temperatureUnit == "C" ? celsiusUnit : fahrenheitUnit
    
    def airTemp = temperatures.airTemp
//    def poolTemp = tempPoolTemp
    def poolTemp = state.poolTempLK
    def spaTemp = temperatures.spaTemp
    def poolSetPoint = temperatures.poolSetPoint
    def spaSetPoint = temperatures.spaSetPoint

	if (temperatureUnit == "auto") {
		unit = celsiusUnit
		airTemp = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(temperatures.airTemp) : temperatures.airTemp).toDouble().round(1)
//	    poolTemp = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(tempPoolTemp) : tempPoolTemp).toDouble().round(1)
	    poolTemp = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(state.poolTempLK) : state.poolTempLK).toDouble().round(1)
	    spaTemp = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(temperatures.spaTemp) : temperatures.spaTemp).toDouble().round(1)
	    poolSetPoint = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(temperatures.poolSetPoint) : temperatures.poolSetPoint).toDouble().round(0)
	    spaSetPoint = (state.uom == "Fahrenheit" ? fahrenheitToCelsius(temperatures.spaSetPoint) : temperatures.spaSetPoint).toDouble().round(0)
	}

	state.spaTemp = spaTemp
    state.poolTemp = poolTemp
    state.airTemp = airTemp
    
    sendEvent(name: "temperature", value: airTemp, unit: unit)
	sendEvent(name: "poolTemperature", value: poolTemp, unit: unit) 
	sendEvent(name: "spaTemperature", value: spaTemp, unit: unit)
    sendEvent(name: "poolHeaterSetpoint", value: poolSetPoint, unit: unit)
	sendEvent(name: "spaHeaterSetpoint", value: spaSetPoint, unit: unit)
    sendEvent(name: "freezeProtection", value: freeze)
	sendEvent(name: "heaterOperation", value: heater)
    sendEvent(name: "poolHeatMode", value: state.heaterMap["${temperatures.poolHeatMode}"])
    sendEvent(name: "spaHeatMode", value: state.heaterMap["${temperatures.spaHeatMode}"])
}


def parseSchedule(physicalgraph.device.HubResponse hubResponse) {
    //log.debug "------------- parseSchedule -------------"   
    def json = hubResponse.json
    if (!json) {
       log.error "parsed lan message was nil"
       def evt = createEvent(name: "refresh", isStateChange: "true", value: "Idle")
       return evt
    } 

	//log.debug "schedule = ${json.schedule}"
    def fullSchedule = "#      Circuit     StartTime     EndTime\n"  
    fullSchedule = fullSchedule + "________________________________________\n"
              
    def eggSchedule = "----- EGG TIMER -----\n"
    eggSchedule = eggSchedule + "   #         Circuit        Duration\n"
    eggSchedule =   eggSchedule + "________________________________________\n"  
            
    def int circuitSize = 0
    def space = ""
    def int i
    def active = 0
    def ison = ""
    def int ID
    def CIRCUIT
    def CN
    def MODE
    def bytes
    def DURATION
    def START_TIME
    def END_TIME
    def DAYS
    def schmap = [:]
    
    json.schedule.keySet().each {
        space = ""
        def event = json.schedule[it]
        //log.info "event = ${event}"
        if (event.circuit) {
           ID = event.id
           CIRCUIT = event.circuit
           CN = event.circuitNum
           MODE = event.mode
           bytes = event.bytes     
           DURATION = event.duration
           START_TIME = event.startTime.time24
           END_TIME = event.endTime.time24
           DAYS = event.days
        } else {
           ID = event.ID     
           CIRCUIT = event.CIRCUIT
           CN = event.CIRCUITNUM
           MODE = event.MODE
           bytes = event.BYTES  
           DURATION = event.DURATION
           START_TIME = event.START_TIME
           END_TIME = event.END_TIME
           DAYS = event.DAYS
       }
        //log.debug "V6 - it:${it}-ID:${ID} CIRCUIT:${CN}-${CIRCUIT} MODE:${MODE} BYTES:${bytes}\n ----- DURATION:${DURATION} DAYS:${DAYS} START_TIME:${START_TIME} END_TIME:${END_TIME}"
            
        if (MODE == "Egg Timer") {
           circuitSize = 16 - CIRCUIT.size()
           for (i = 0; i <circuitSize; i++) {
                space = space + " "
           }
           if (CIRCUIT == "SPA") 
              space = space + " "
           eggSchedule = eggSchedule + "${ID}${space}${CIRCUIT}${space}${DURATION}\n"
        } else if (MODE == "Schedule") {
           if (CIRCUIT != "NOT USED") {
              circuitSize = 16 - CIRCUIT.size()
              for (i = 0; i <circuitSize; i++) {
                  space = space + " "
              }
              //log.debug "START_TIME = ${START_TIME}"
              //log.debug "END_TIME = ${END_TIME}"
              
              ison = ""
              def between = timeOfDayIsBetween(START_TIME, END_TIME, new Date(), location.timeZone) 
              def circuit_status = device.currentState(CIRCUIT)?.value
              //log.debug "circuit_status = ${circuit_status}"
    		  if (between && circuit_status == "on") {
                 //log.debug "current time is between ${START_TIME} and ${END_TIME} for ID ${ID} circuit ${CIRCUIT}"
                 ison = "*"
                 active = active + 1
              }
         
              def day_list = DAYS.split(" ")
              def days = []
              day_list.each {
                  days << it.substring(0,3)
              }  
              //log.info "----------- ID ${ID} -------------\n ${event}"
              schmap.put(ID,"${ison}${ID}${space}${CIRCUIT}${space}${START_TIME}${space}${END_TIME}\nDAYS:${days}\n\n")
            }
         }
  	}	
    //log.warn "schmap = ${schmap}"
    
    for (i = 1; i < 13; i++) {
        if (schmap[i])
    	   fullSchedule = fullSchedule + schmap[i]   
    }
    
    fullSchedule = fullSchedule + "* ${active} active schedule(s)"
    //log.info "fullschedule = ${fullSchedule}"
    sendEvent(name: "scheduleList", value: "${fullSchedule}")
    //sendEvent(name: "eggTimerTile", value: "${eggSchedule}")
}


def parseDevice(physicalgraph.device.HubResponse hubResponse) {
    //log.debug "------------- parseDevice -------------"  

	def msg = hubResponse.xml
    log.debug "msg = ${msg}"
    if (msg) {
        log.info "processing xml"

        def body = msg
        loginfo "body = ${body}"
        if (!body) {
            log.error "body was nil, returning"
            return null
        }    
        def verMajor = body.specVersion.major
        def verMinor = body.specVersion.minor
        def verPatch = body.specVersion.patch
        def manufacturer = body.device.manufacturer
        def modelDescription = body.device.modelDescription
        def friendlyName = body.device.friendlyName

        state.version = "${friendlyName} Version\n${verMajor}.${verMinor}.${verPatch}\n Debug ${dbug}" 
        state.manufacturer = "By ${manufacturer}\n${modelDescription}"
        log.debug "version = ${state.version}"
        log.debug "manufacturer = ${state.manufacturer}"
    }  
}

// sendCommand : sends commands to controller
def sendCommandCallBack(command, callBack) {
    //log.warn "In sendCommandCallBack"
    def userpass = encodeCredentials(username, password)
    def headers = getHeader(userpass)
    def dni = setDeviceNetworkId("${controllerIP}","${controllerPort}")

    def params = [
        method: "GET",
		path: command,
        headers: headers,
        dni: [dni]       
    ]    
    
    def opts = [
        callback : callBack,
        type: 'LAN_TYPE_CLIENT'
    ]
    
    //log.warn "sendCommand command: ${command}\npoolCommand =\n${params}"
    try {
       sendHubCommand(new physicalgraph.device.HubAction(params, null, opts))
       //log.warn "SENT: $params $opts callback : ${callBack}"
    } catch (e) {
       log.error "something went wrong: $e"
    }
}

def sendCommand(command) {
    def userpass = encodeCredentials(username, password)
    def headers = getHeader(userpass)
    def dni = setDeviceNetworkId("${controllerIP}","${controllerPort}")

    def params = [
        method: "GET",
		path: command,
        headers: headers,
        dni: [dni]
    ]    
    
    //loginfo "sendCommand command: ${command}\npoolCommand =\n${params}"
    try {
       sendHubCommand(new physicalgraph.device.HubAction(params))
       //logdebug "SENT: $params"
    } catch (e) {
       log.error "something went wrong: $e"
    }
    
}

def initialize() {
	//log.debug("initialize")
	runEvery1Minute(refresh)
    
    state.dayValueMap = [Sunday:1,Monday:2,Tuesday:4,Wednesday:8,Thursday:16,Friday:32,Saturday:64,
                          Sun:1,Mon:2,Tue:4,Wed:8,Thu:16,Fri:32,Sat:64] 
                          
    state.Month = ["Jan":1,"Feb":2,"Mar":3,"Apr":4,"May":5,"Jun":6,"Jul":7,"Aug":8,"Sep":9,"Oct":10 ,"Nov":11,"Dec":12]                     
                          
    state.dayMap = ["Sunday":Sunday, 
                     "Monday":Monday, 
                     "Tuesday":Tuesday, 
                     "Wednesday":Wednesday, 
                     "Thursday":Thursday, 
                     "Friday":Friday, 
                     "Saturday":Saturday]   
                     
    state.heaterMap = [0:"Off",1:"Heater",2:"Solar Preferred",3:"Solar Only"]
    state.heaterMapI = ["off":0,"heater":1,"solarpref":2,"solaronly":3]
	state.onOff = [0:"off",1:"on"]
	state.onOffI = ["off":0,"on":1]

    state.operatingMode = ""
    state.airTemp = 0
    state.poolTemp = 0
    state.poolTempLK = 0
    state.pumpStatus = "off"

    sendEvent(name: "switch", value: "off")
    sendEvent(name: "temperature", value: 0, unit: "C")    
	sendEvent(name: "poolTemperature", value: "0", unit: "°C")
	sendEvent(name: "spaTemperature", value: "0", unit: "°C")
	sendEvent(name: "poolHeatMode", value: "off")
	sendEvent(name: "spaHeatMode", value: "off")
    sendEvent(name: "poolHeaterSetpoint", value: "0", unit: "°C")
    sendEvent(name: "spaHeaterSetpoint", value: "0", unit: "°C")
    sendEvent(name: "freezeProtection", value: "Off")
	sendEvent(name: "heaterOperation", value: "Off")
    sendEvent(name: "operatingMode", value: "Pool")        
    sendEvent(name: "poolLight", value: "Off")
    sendEvent(name: "spaLight", value: "Off")
    sendEvent(name: "cleaningRobot", value: "Off")
	sendEvent(name: "chlorinatorOperatingState", value: "Off", unit: "-")
	sendEvent(name: "chlorinatorSaltLevel", value: "n/a", unit: "ppm")
	sendEvent(name: "chlorinatorCurrentOutput", value: "n/a", unit: "%")
	sendEvent(name: "chlorinatorSetpoint", value: "0/0", unit: "%")
	sendEvent(name: "superChlorinate", value: "Off")
	sendEvent(name: "chlorinatorStatus", value: "Off")
	sendEvent(name: "updateDateAndTime", value: true)
    
    if(updatePoolTimeAndDate) {
    	log.debug("auto date update")
    	//runEvery24Hours(setdatetime)
        setdatetime()
    }
    
    sendCommandCallBack("/all",'parseUOM')
    
    state.circuit = [:]
    sendCommandCallBack("/circuit",'parseCircuit')
}

def updated() {
    log.info "######### UPDATED #########" 
    initialize()   
    refresh()
}
     
def installed() {
    log.info "########## installed ###########"
    initialize()
    setDeviceNetworkId("${controllerIP}","${controllerPort}")  
}


def refresh() {
    // this runs every minute
    log.debug "Requested a refresh"
    sendCommandCallBack("/temperature",'parseTemperature')
    sendCommandCallBack("/chlorinator",'parseChlor')
    sendCommandCallBack("/circuit",'parseCircuit')
    sendCommandCallBack("/schedule",'parseSchedule')
}


// Set Controller Date Time
def setdatetime() {
// datetime/set/time/{hour}/{min}/{dow}/{day}/{mon}/{year}/{dst}
// set the schedule on the controller for the particular schedule ID. 
// dow= day of week as expressed as [0=Sunday, 1=Monday, 2=Tuesday, 4=Wednesday, 8=Thursday, 16=Friday, 32=Saturday] 
// or a combination thereof [3=Monday+Tuesday]. To set a schedule set a valid start and end time (hh:mm). 
// To set an egg timer, set the start time to 25:00 and the endtime to the duration (hh:mm) you want the egg timer to run.
//"text": "FAIL: SOCKET API - hour (NaN) should be 0-23 and minute (NaN) should be 0-59. 
// Received: NaN:NaNDay (NaN) should be 0-31, month (NaN) should be 0-12 and year (NaN) should be 0-99.
// Day of week (NaN) should be one of: [1,2,4,8,16,32,64] [Sunday->Saturday]dst (0) should be 0 or 1" }
    log.info "------- In setdatetime -------"

    if(updatePoolTimeAndDate) {
        Date date = new Date()
        def df = new SimpleDateFormat("yy")
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"))
        String year = df.format(date)

        df = new SimpleDateFormat("MM")
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"))
        String month = df.format(date)

        df = new SimpleDateFormat("dd")
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"))
        String day = df.format(date)

        df = new SimpleDateFormat("HH")
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"))
        String hour = df.format(date)

        df = new SimpleDateFormat("mm")
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"))
        String min = df.format(date)

        df = new SimpleDateFormat("E")
        df.setTimeZone(TimeZone.getTimeZone("America/Chicago"))
        String dow = df.format(date)

        def downum = state.dayValueMap[dow]
        sendCommandCallBack("/datetime/set/time/${hour}/${min}/date/${downum}/${day}/${month}/${year}/0",'parseAction')
	}
}
// private functions

private delayAction(long time) {
    new physicalgraph.device.HubAction("delay $time")
}

private setDeviceNetworkId(ip,port){
      def iphex = convertIPtoHex(ip)
      def porthex = convertPortToHex(port)
      device.deviceNetworkId = "$iphex:$porthex"
      return (device.deviceNetworkId)
}

private getHostAddress() {
    return "${controllerIP}:${controllerPort}"
}

private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex
}

private String convertPortToHex(port) {
    String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}

private encodeCredentials(username, password){
    def userpassascii = "${username}:${password}"
    def userpass = "Basic " + userpassascii.encodeAsBase64().toString()
    return userpass
}

private getHeader(userpass){
    def headers = [:]
    headers.put("HOST", "${controllerIP}:${controllerPort}")
    headers.put("Accept","application/json")
    return headers
}