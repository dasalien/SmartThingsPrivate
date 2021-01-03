metadata {
	definition (name: "Virtual Thermostat Device",
    namespace: "acrosscable12814",
    author: "Dieter Rothhardt",
    mnmn: "SmartThingsCommunity", 
    vid: "f2415c71-667f-309c-af8b-5d63df3ead15",
    executeCommandsLocally: true,
    ocfDeviceType: "oic.d.thermostat") {
		capability "Temperature Measurement"
		//capability "Thermostat"
		capability "Thermostat Mode"
		capability "Thermostat Heating Setpoint"
		capability "Thermostat Cooling Setpoint"
        capability "Thermostat Operating State"
        capability "Thermostat Fan Mode"
		capability "Configuration"
		capability "Refresh"
		capability "Actuator"
		capability "Sensor"
		capability "Health Check"

		command "refresh"
		command "poll"
        
		command "offbtn"
		command "heatbtn"
		command "levelUpDown"
		command "levelUp"
		command "levelDown"
		command "heatingSetpointUp"
		command "heatingSetpointDown"
		command "coolingSetpointUp"
		command "coolingSetpointDown"
		command "changeMode"
		command "setVirtualTemperature", ["number"]
		command "setHeatingStatus", ["string"]
        command "setCoolingStatus", ["string"]
        command "setThermostatFanMode", ["string"]
		command "setTemperature", ["number"]
        
		attribute "temperatureUnit", "string"
		attribute "thermostatOperatingState", "string"        
        attribute "currentThermostatOperatingState", "string"        
        
	}

	simulator {
		// TODO: define status and reply messages here
	}
}

def shouldReportInCentigrade() {
	try {
    	def ts = getTemperatureScale();
    	return ts //== "C"
    } catch (e) {
    	log.error e
    }
    return true;
}

def installed() {
    log.trace "Executing 'installed'"
    initialize()
}

def configure() {
    log.trace "Executing 'configure'"
    initialize()
}

private initialize() {
    log.trace "Executing 'initialize'"
    
    setHeatingSetpoint(defaultTemp())
    setCoolingSetpoint(defaultTemp())
    setVirtualTemperature(defaultTemp())
    //setCoolingStatus("off")
    //setHeatingStatus("off")
    setThermostatMode("auto")
    setThermostatFanMode("auto")
    sendEvent(name:"supportedThermostatModes",    value: ['auto','cool','heat', 'off'], displayed: false)
    sendEvent(name:"supportedThermostatFanModes", values: ['on','auto','circulate'], displayed: false)
    
	state.tempScale = "C"
}

def getTempColors() {
	def colorMap
        //   wantMetric()
	if(getTemperatureScale() == "C"()) {
		colorMap = [
			// Celsius Color Range
			[value: 0, color: "#153591"],
			[value: 7, color: "#1e9cbb"],
			[value: 15, color: "#90d2a7"],
			[value: 23, color: "#44b621"],
			[value: 29, color: "#f1d801"],
			[value: 33, color: "#d04e00"],
			[value: 36, color: "#bc2323"]
			]
	} else {
		colorMap = [
			// Fahrenheit Color Range
			[value: 40, color: "#153591"],
			[value: 44, color: "#1e9cbb"],
			[value: 59, color: "#90d2a7"],
			[value: 74, color: "#44b621"],
			[value: 84, color: "#f1d801"],
			[value: 92, color: "#d04e00"],
			[value: 96, color: "#bc2323"]
		]
	}
}

def unitString() {  return shouldReportInCentigrade() ? "C": "F" }
def defaultTemp() { return shouldReportInCentigrade() ? 20 : 70 }
def lowRange() { return shouldReportInCentigrade() ? 9 : 45 }
def highRange() { return shouldReportInCentigrade() ? 45 : 113 }
def getRange() { return "${lowRange()}..${highRange()}" }

def getTemperature() {
	return device.currentValue("temperature")
}

def setThermostatFanMode(mode) {
	sendEvent(name:"thermostatFanMode", value: mode)
    
    log.debug("${mode}")
    if(mode == "circulate") {
    	sendEvent(name:"thermostatOperatingState", value: "fan only")
    }
    if(mode == "auto") {
    	sendEvent(name:"thermostatOperatingState", value: "idle")
    }
}

def setHeatingSetpoint(temp) {
    def chsp = device.currentValue("heatingSetpoint");

//    if(ctsp != temp || chsp != temp) {
//        sendEvent(name:"thermostatSetpoint", value: temp, unit: unitString(), displayed: false)
        sendEvent(name:"heatingSetpoint", value: temp, unit: unitString())
//    }
}

def setCoolingSetpoint(temp) {
//	def ctsp = device.currentValue("thermostatSetpoint");
    def chsp = device.currentValue("coolingSetpoint");

//    if(ctsp != temp || chsp != temp) {
//        sendEvent(name:"thermostatSetpoint", value: temp, unit: unitString(), displayed: false)
        sendEvent(name:"coolingSetpoint", value: temp, unit: unitString())
//    }
}

def heatingSetpointUp() {
	def hsp = device.currentValue("heatingSetpoint")
	if(hsp + 1.0 > highRange()) return;
	setHeatingSetpoint(hsp + 1.0)
}

def heatingSetpointDown() {
	def hsp = device.currentValue("heatingSetpoint")
	if(hsp - 1.0 < lowRange()) return;
	setHeatingSetpoint(hsp - 1.0)
}

def coolingSetpointUp() {
	def hsp = device.currentValue("coolingSetpoint")
	if(hsp + 1.0 > highRange()) return;
	setCoolingSetpoint(hsp + 1.0)
}

def coolingSetpointDown() {
	def hsp = device.currentValue("coolingSetpoint")
	if(hsp - 1.0 < lowRange()) return;
	setCoolingSetpoint(hsp - 1.0)
}

def levelUp() {
	if(getThermostatMode() == 'heat') {
    	heatingSetpointUp()
    } else {
    	coolingSetpointUp()
    }
	//eeddir ... check on mode, use above
	//def hsp = device.currentValue("thermostatSetpoint")
	//if(hsp + 1.0 > highRange()) return;
    //setHeatingSetpoint(hsp + 1.0)
}

def levelDown() {
	if(getThermostatMode() == 'heat') {
    	heatingSetpointDown()
    } else {
    	coolingSetpointDown()
    }
	//eeddir ... check on mode, use above. missing, off/auto
    //def hsp = device.currentValue("thermostatSetpoint")
	//if(hsp - 1.0 < lowRange()) return;
    //setHeatingSetpoint(hsp - 1.0)
}

def poll() {
	refresh()
}

def parse(data) {
    log.debug "parse data: $data"
}

def refresh() {
    log.trace "Executing refresh"
    sendEvent(name: "supportedThermostatModes",    value: ['auto','cool','heat','off'], displayed: false)
    sendEvent(name: "supportedThermostatFanModes", values: ['on','auto','circulate'], displayed: false)
}

def getThermostatMode() {
	log.debug("getThermostatMode")
	return device.currentValue("thermostatMode")
}

def getOperatingState() {
	log.debug("getOperatingState")
	return device.currentValue("thermostatOperatingState")
}

def currentThermostatOperatingState() {
	log.debug("currentThermostatOperatingState: ${device.currentValue("thermostatOperatingState")}")
	return device.currentValue("thermostatOperatingState")
}

def getThermostatSetpoint() {
	return device.currentValue("thermostatSetpoint")
}

def getHeatingSetpoint() {
	return device.currentValue("heatingSetpoint")
}

def offbtn() {
	setThermostatMode("off")
}

def heatbtn() {
	setThermostatMode("heat")
}

def setThermostatMode(mode) {
	//eeddir needs change
	if(device.currentValue("thermostatMode") != mode) {
    	sendEvent(name: "thermostatMode", value: mode)
        
        if(mode == "auto") {
    		sendEvent(name:"thermostatOperatingState", value: "idle")
            thermostatOperatingState = "idle"
            currentThermostatOperatingState = "idle"
            sendEvent(name:"thermostatFanMode", value: "auto")
    	}
        if(mode == "heat") {
    		sendEvent(name:"thermostatOperatingState", value: "heating")
            thermostatOperatingState = "heating"
            currentThermostatOperatingState = "heating"
            sendEvent(name:"thermostatFanMode", value: "on")
    	}
        if(mode == "cool") {
    		sendEvent(name:"thermostatOperatingState", value: "cooling")
            thermostatOperatingState = "cooling"
            currentThermostatOperatingState = "cooling"
            sendEvent(name:"thermostatFanMode", value: "on")
    	}
        if(mode == "off") {
    		sendEvent(name:"thermostatOperatingState", value: "off")
            thermostatOperatingState = "off"
            currentThermostatOperatingState = "off"
            sendEvent(name:"thermostatFanMode", value: "auto")
    	}
    }
}

def levelUpDown() {
}

def changeMode() {
	//eeddir needs change
	def val = device.currentValue("thermostatMode") == "off" ? "heat" : "off"
	setThermostatMode(val)
    return val
}

def setVirtualTemperature(temp) {
	sendEvent(name:"temperature", value: temp, unit: unitString(), displayed: true)
}

def setHeatingStatus(string) {
	if(device.currentValue("thermostatOperatingState") != string) {
		sendEvent(name:"thermostatOperatingState", value: string)
    }
}

def setCoolingStatus(string) {
	if(device.currentValue("thermostatOperatingState") != string) {
		sendEvent(name:"thermostatOperatingState", value: string)
    }
}