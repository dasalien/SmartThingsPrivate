/**
 *  My Virtual Zone
 *  v2.0
 *
 *  Copyright 2017-2020 Yves Racine
 *  LinkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *
 *  Developer retains all right, title, copyright, and interest, including all copyright, patent rights, trade secret 
 *  in the Background technology. May be subject to consulting fees under the Agreement between the Developer and the Customer. 
 *  Developer grants a non exclusive perpetual license to use the Background technology in the Software developed for and delivered 
 *  to Customer under this Agreement. However, the Customer shall make no commercial use of the Background technology without
 *  Developer's written consent.
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
  *  Software Distribution is restricted and shall be done only with Developer's written approval.
 *
 *
 */
preferences {

	//	Preferences are optional 
 
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces)\n or leave it empty (no tracing)")
	input("logFilter", "number",title: "(1=ERROR only,2=<1+WARNING>,3=<2+INFO>,4=<3+DEBUG>,5=<4+TRACE>)",  range: "1..5",
 		description: "optional" )  
}
metadata {
	// Automatically generated. Make future change here.
	definition (name: "My Virtual Zone V2", namespace: "acrosscable12814", author: "Yves Racine",mnmn: "SmartThingsCommunity", vid: "0d8b85d1-ccfd-3f72-a6c5-9372b5d98408", ocfDeviceType:"oic.d.thermostat") {
    //25256a45-ffa7-32b4-a161-06e4888cd643
    //
		capability "Temperature Measurement"
		//capability "fabricacross64399.setThermostatSetpoint"
        capability "acrosscable12814.thermostatSetpoint"
        capability "acrosscable12814.zoneTemperatureDelta"
        capability "acrosscable12814.zoneBaselineSetpoint"
        capability "acrosscable12814.zoneSchedule"
        capability "acrosscable12814.zoneAllVents"
		capability "Motion Sensor"
		capability "Switch"
		capability "Contact Sensor"
		capability "thermostatMode"
		capability "Refresh"
		capability "Sensor"
		capability "Actuator"
		capability "Health Check"
//		capability "Battery"  // not available, but need to be added for display in new ST App.
	}

	command "deltaLevelUp"
	command "deltaLevelDown"
	command "setZoneActive"
	command "setZoneInactive"
	command "openAllVentsInZone"    
	command "closeAllVentsInZone"    
	command "levelUp"
	command "levelDown"
	command "setTemperature", ["number"]
//	command "setThermostatSetpoint"

    
    
	attribute "tempDelta", "string"
	attribute "mode", "string"
	attribute "activeZone", "string"
	attribute "allVentsState", "string"
	attribute "activeInSchedule", "string"
	attribute "baselineSetpoint", "string"  
    
    

    
	// UI tile definitions
	tiles(scale: 2) {
		multiAttributeTile(name:"multigeneric", type: "generic", width: 2, height: 2,canChangeIcon: true){
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
				attributeState("default", label:'${currentValue}', unit:"dF", backgroundColor:"#269bd2") 
			}
			tileAttribute("device.thermostatSetpoint", key: "VALUE_CONTROL") {
				attributeState("default", action: "setTemperature")
				attributeState("VALUE_UP", action: "levelUp")
				attributeState("VALUE_DOWN", action: "levelDown")
			}

		}
		standardTile("allVentsState", "device.allVentsState", width: 2, height: 2, canChangeIcon: true) {
			state "off", action: "openAllVentsInZone", icon: "st.vents.vent-closed", backgroundColor: "#ffffff",nextState: "on"
			state "on", label: '${name}', action: "closeAllVentsInZone", icon: "st.vents.vent-open", backgroundColor: "#79b821",
                       	defaultState:true,nextState: "off"
		}
		valueTile("temperature", "device.temperature", width: 6, height: 4) {
			state("temperature", label:'${currentValue}', unit:"F",
				backgroundColors: getBackgroundColors())
		}
		standardTile("motion", "device.motion", width: 2, height: 2) {
			state("active", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#00a0dc")
			state("inactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff", defaultState:true)
		}
		standardTile("contact", "device.contact", width: 2, height: 2) {
			state("open", label:'${name}', icon:"st.contact.contact.open", backgroundColor:"#e86d13")
			state("closed", label:'${name}', icon:"st.contact.contact.closed", backgroundColor:"#00a0dc",defaultState:true)
		}
		standardTile("switch", "device.switch", width: 2, height: 2) {
			state("on", label:'Active', icon:"st.motion.motion.inactive",backgroundColor: "#44b621",
            		defaultState:true, action: "switch.off", nextState: "off"
				)
			state("off", label:'Inactive', icon:"st.motion.motion.active", backgroundColor:"#ffffff",
            		action: "switch.on", nextState: "on"
				)                    
		}
		standardTile("activeInSchedule", "device.activeInSchedule", width: 2, height: 2, canChangeIcon: true) {
			state "off", label: '${name}', icon: "st.Office.office7", backgroundColor: "#ffffff",defaultState:true
			state "on", label: '${name}', icon: "st.Office.office7", backgroundColor: "#79b821"
		}
 		standardTile("mode", "device.mode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2,) {
			state "heat", label: 'Mode ${name}',
				icon: "${getCustomImagePath()}heatMode.png", backgroundColor: "#ffffff"
			state "off", label: 'Mode ${name}', 
				icon: "st.Outdoor.outdoor19", backgroundColor: "#ffffff", defaultState:true
			state "cool", label: 'Mode ${name}', 
				icon: "${getCustomImagePath()}coolMode.png", backgroundColor: "#ffffff"
			state "auto", label: 'Mode ${name}', 
				icon: "${getCustomImagePath()}autoMode.png",
 			backgroundColor: "#ffffff"
		}
		valueTile("baselineSetpoint", "device.baselineSetpoint", width: 2, height: 2, inactiveLabel: false) {
			state ("default", label:'Baseline ${currentValue}', unit:"F", 
			backgroundColors:[
				[value: 0, color: "#153591"],
				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 35, color: "#d04e00"],
				[value: 37, color: "#bc2323"],
				// Fahrenheit Color Range
				[value: 31, color: "#153591"],
				[value: 44, color: "#1e9cbb"],
				[value: 59, color: "#90d2a7"],
				[value: 74, color: "#44b621"],
				[value: 84, color: "#f1d801"],
				[value: 95, color: "#d04e00"],
				[value: 96, color: "#bc2323"]
			])	            
		}
                     
		valueTile("name", "device.name", inactiveLabel: false, width: 6,height: 1) {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("tempDelta", "device.tempDelta", width: 2, height: 2, inactiveLabel: false) {
			state ("default", label:'TempDelta ${currentValue}', unit:"F", 
			backgroundColors:[
				[value: 0, color: "#153591"],
				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 35, color: "#d04e00"],
				[value: 37, color: "#bc2323"],
				// Fahrenheit Color Range
				[value: 31, color: "#153591"],
				[value: 44, color: "#1e9cbb"],
				[value: 59, color: "#90d2a7"],
				[value: 74, color: "#44b621"],
				[value: 84, color: "#f1d801"],
				[value: 95, color: "#d04e00"],
				[value: 96, color: "#bc2323"]
			])	            
		}

		standardTile("deltaLevelDown", "device.tempDelta", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "default", label: '', action:"deltaLevelDown", icon: "${getCustomImagePath()}coolDown.png", backgroundColor: "#ffffff"

		}
		standardTile("deltaLevelUp", "device.tempDelta", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "default", label: '', action:"deltaLevelUp", icon: "${getCustomImagePath()}heatUp.png", backgroundColor: "#ffffff"
		}

	main(["multigeneric"])
		details([
		"multigeneric"
		,"switch"        
		,"mode"        
		,"baselineSetpoint",
		,"tempDelta", "deltaLevelUp", "deltaLevelDown"
		,"contact"
		,"allVentsState"
		,"motion"
		,"activeInSchedule"        
		])
	}
}

void installed() {
	initialize()
	if (settings.trace) { 
			log.debug("installed>$device.displayName installed with settings: ${settings.inspect()}, state=${state.inspect()}")
	}
}

void initialize() {
	state?.scale=getTemperatureScale() 
	def virtual_info = device.deviceNetworkId.tokenize('.')
	state?.indiceZone = virtual_info.last()
	sendEvent(name:"tempDelta", value:0,isDisplayed:false)
    sendEvent(name:"zoneTemperatureDelta", value:0, unit: "C")
    
    sendEvent(name: "temperature", value: 0, unit: "C")
    sendEvent(name: "switch", value: "on")
    sendEvent(name: "zoneSetpoint", value: 1, unit: "C")
    sendEvent(name: "motion", value: "inactive")
    sendEvent(name: "contact", value: "closed")
    sendEvent(name: "zoneBaselineSetpoint", value: "n/a")
    sendEvent(name: "zoneSchedule", value: "n/a")
    sendEvent(name: "zoneAllVents", value: "Active")
    sendEvent(name: "supportedThermostatModes", value: "[off,heat,cool,auto]")
    sendEvent(name: "thermostatMode", value: "Auto")
}

/* Ping is used by Device-Watch in attempt to reach the device
sendEvent(name: "switch", value: "on")
*/
def ping() {
	poll()
}

def updated() {
	initialize()
	traceEvent(settings.logFilter,"updated>$device.displayName updated with settings: ${settings.inspect()},state=${state.inspect()}",
		settings.trace,get_LOG_TRACE())	        
}


def getBackgroundColors() {
	def results
	if (state?.scale =='C') {
				// Celsius Color Range
		results=
			[        
				[value: 0, color: "#153591"],
				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 35, color: "#d04e00"],
				[value: 37, color: "#bc2323"]
			]
	} else {
		results =
				// Fahrenheit Color Range
			[        
				[value: 31, color: "#153591"],
				[value: 44, color: "#1e9cbb"],
				[value: 59, color: "#90d2a7"],
				[value: 74, color: "#44b621"],
				[value: 84, color: "#f1d801"],
				[value: 95, color: "#d04e00"],
				[value: 96, color: "#bc2323"]
			]  
	}
	return results    
}

def setThermostatMode(value) {
	log.debug(" Thermostat Mode: ${value}")
    sendEvent(name: "thermostatMode", value: value)
}   

def setZoneTemperatureDelta(value) {
	log.debug("zoneTemperatureDelta: ${value}")
    sendEvent(name: "zoneTemperatureDelta", value: value)
}   

def setZoneAllVents(value) {
	log.debug("zoneAllVents: ${value}")
    sendEvent(name: "zoneAllVents", value: value)
    
    if(value == "Off") {
    	closeAllVentsInZone()
	} else {
    	openAllVentsInZone()
    }
        
    
}   

void levelUp() {
	setTemperature(1)
}

void levelDown() {
	setTemperature(-1)
}

void setThermostatSetpoint(value) {
	traceEvent(settings.logFilter,"setThermostatSetpoint>initial value= $value")
	def mode = device.currentValue("mode")
	float MAX_DELTA=20    
	def curTempDelta=(device.currentValue("tempDelta")) ?:0
	def mainSetpoint=(device.currentValue("baselineSetpoint"))?:(state?.scale== 'C')?21:70
	def curSetpoint = (device.currentValue("thermostatSetpoint")) ?: mainSetpoint
	def scale=state?.scale   
	String tempValueString    
 	if (scale== 'C') { 	
		double tempValue=value
		tempValueString = String.format('%2.1f', tempValue)                    
		if (tempValueString.matches(".*([.,][3456])")) {                
			tempValueString=String.format('%2d.5', tempValue.intValue())                
			traceEvent(settings.logFilter,"setTemperature>curSetpoint's value ($tempValueString) which ends with 3456=> rounded to .5", settings.trace,get_LOG_INFO())	
		} else if (tempValueString.matches(".*([.,][789])")) {  
			traceEvent(settings.logFilter,"setTemperature>curSetpoint's value ($tempValueString) which ends with 789=> rounded to next .0", settings.trace,get_LOG_INFO())	
			tempValue=tempValue.intValue() + 1                        
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		} else {
			traceEvent(settings.logFilter,"setTemperature>curSetpoint's value ($tempValueString) which ends with 012=> rounded to previous .0", settings.trace,get_LOG_INFO())	
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		}
    
		traceEvent(settings.logFilter,"setTemperature in Celsius>after temp correction= $temp",settings.trace)
		float newTempDelta= (tempValue  - mainSetpoint.toFloat()).round(1)      
		if ((newTempDelta.toDouble() < tempValue) && (curTempDelta.toFloat() != newTempDelta) && (newTempDelta < MAX_DELTA)) {        
			sendEvent(name:"tempDelta", value: newTempDelta, displayed: true)
            sendEvent(name:"zoneTemperatureDelta", value: newTempDelta, unit:scale)
            
			tempValueString = String.format('%2.1f', tempValue.round(1))
			sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)
            

		}  else {
			traceEvent(settings.logFilter,"setTemperature>max delta reached ($MAX_DELTA)..", settings.trace,get_LOG_INFO())	
			sendEvent(name:"tempDelta", value: MAX_DELTA, displayed: true, unit:scale)
            sendEvent(name:"zoneTemperatureDelta", value: MAX_DELTA, unit:scale)
			sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)
		}        
			        
	} else {
		double tempValue=value
		traceEvent(settings.logFilter,"setTemperature in Farenheit>after temp correction= $temp",settings.trace)
		double newTempDelta= (tempValue  - mainSetpoint.toDouble()) 
		if (newTempDelta.toDouble() < tempValue && curTempDelta != newTempDelta && (newTempDelta < MAX_DELTA)) {        
			sendEvent(name:"tempDelta", value: newTempDelta.intValue(), displayed: true)
            sendEvent(name:"zoneTemperatureDelta", value: newTempDelta.intValue(), unit:scale)
			tempValueString = String.format('%2d', tempValue.intValue())
			sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)
		}  else {
			traceEvent(settings.logFilter,"setTemperature>max delta reached ($MAX_DELTA)..", settings.trace,get_LOG_INFO())	
			sendEvent(name:"tempDelta", value: MAX_DELTA, displayed: true, unit:scale)
			sendEvent(name:"zoneTemperatureDelta", value: MAX_DELTA, unit:scale)
            sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)
		}        
			        
	}        
 
}


void setTemperature(value) {
	traceEvent(settings.logFilter,"setTemperature>initial value= $value")
	def mode = device.currentValue("mode")
	float MAX_DELTA=20    
	def mainSetpoint=(device.currentValue("baselineSetpoint"))?:(state?.scale== 'C')?21:70
	def curSetpoint = (device.currentValue("thermostatSetpoint")) ?: mainSetpoint
	String tempValueString    
    
	def curTempDelta=(device.currentValue("tempDelta")) ?:0
	if (state?.scale== 'C') { 	
		double tempValue=curSetpoint
		tempValueString = String.format('%2.1f', tempValue)                    
		if (tempValueString.matches(".*([.,][3456])")) {                
			tempValueString=String.format('%2d.5', tempValue.intValue())                
			traceEvent(settings.logFilter,"setTemperature>curSetpoint's value ($tempValueString) which ends with 3456=> rounded to .5", settings.trace,get_LOG_INFO())	
		} else if (tempValueString.matches(".*([.,][789])")) {  
			traceEvent(settings.logFilter,"setTemperature>curSetpoint's value ($tempValueString) which ends with 789=> rounded to next .0", settings.trace,get_LOG_INFO())	
			tempValue=tempValue.intValue() + 1                        
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		} else {
			traceEvent(settings.logFilter,"setTemperature>curSetpoint's value ($tempValueString) which ends with 012=> rounded to previous .0", settings.trace,get_LOG_INFO())	
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		}
    
		curSetpoint=tempValueString.toDouble()        
		double temp  
		if (value==-1 || value == 0) {
			temp = curSetpoint - 0.5           
		} else if (value==1) {
			temp = curSetpoint + 0.5            
		} else {        
			temp = (value <= curSetpoint)? (curSetpoint - 0.5) : ( curSetpoint + 0.5)   
		}        
		traceEvent(settings.logFilter,"setTemperature in Celsius>after temp correction= $temp",settings.trace)
		float newTempDelta= (temp  - mainSetpoint.toFloat()).round(1)      
		if ((curTempDelta.toDouble() < temp) && (curTempDelta.toFloat() != newTempDelta) && (newTempDelta < MAX_DELTA)) {        
			sendEvent(name:"tempDelta", value: newTempDelta, displayed: true)
            sendEvent(name:"zoneTemperatureDelta", value: newTempDelta, state?.scale)
			tempValueString = String.format('%2.1f', temp.round(1))
			sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit: state?.scale)
		}            
			        
	} else {
		double temp  
		if (value==-1 || value == 0) {
			temp = curSetpoint - 1           
		} else if (value==1) {
			temp = curSetpoint + 1            
		} else {        
			temp = (value <= curSetpoint)? (curSetpoint - 1) : ( curSetpoint + 1)   
		}        
		traceEvent(settings.logFilter,"setTemperature in Farenheit>after temp correction= $temp",settings.trace)
		double newTempDelta= (temp  - mainSetpoint.toDouble()) 
		if (curTempDelta.toDouble() < temp && curTempDelta != newTempDelta && (newTempDelta < MAX_DELTA)) {        
			sendEvent(name:"tempDelta", value: newTempDelta.intValue(), displayed: true)
            sendEvent(name:"zoneTemperatureDelta", value: newTempDelta.intValue(), state?.scale)
			tempValueString = String.format('%2d', temp.intValue())
			sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true,unit:state?.scale)
		}            
			        
	}        
        
}		


void deltaLevelUp() {
	def scale = (state?.scale) ?: getTemperatureScale()
	def currentSetpointDelta = device.currentValue("tempDelta")
	def mainSetpoint=(device.currentValue("baselineSetpoint"))?:(state?.scale== 'C')?21:70
	double nextLevel    
	if (scale == 'C') {
 		nextLevel=(currentSetpointDelta)? currentSetpointDelta.toDouble():0.0        
		nextLevel = (nextLevel + 0.5).round(1)        
		traceEvent(settings.logFilter,"levelUp>$device.displayName, indiceZone=${state?.indiceZone}, nextLevel=$nextLevel",
			settings.trace,get_LOG_TRACE())        
		if (nextLevel > 10) {
			nextLevel = 10.0
		}
		sendEvent(name:"tempDelta", value: nextLevel, displayed: true)
        sendEvent(name:"zoneTemperatureDelta", value: nextLevel, scale)
 		double newThermostatSetpoint=mainSetpoint.toDouble() + nextLevel     
		def tempValueString = String.format('%2.1f', newThermostatSetpoint.round(1))
		sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)     
	} else {
		nextLevel=(currentSetpointDelta)? currentSetpointDelta.toDouble().round():0        
		nextLevel = (nextLevel + 1)
		traceEvent(settings.logFilter,"levelUp>$device.displayName, indiceZone=${state?.indiceZone}, nextLevel=$nextLevel",
			settings.trace,get_LOG_TRACE())        
		if (nextLevel > 20) {
			nextLevel = 20
		}
		sendEvent(name:"tempDelta", value: nextLevel.intValue(), displayed: true)
        sendEvent(name:"zoneTemperatureDelta", value: nextLevel.intValue(), scale)
		double newThermostatSetpoint=mainSetpoint.toDouble() + nextLevel        
		def tempValueString = String.format('%2d', newThermostatSetpoint.intValue())
		sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)
	}
}
void deltaLevelDown() {
	def scale = (state?.scale) ?: getTemperatureScale()
	def currentSetpointDelta = device.currentValue("tempDelta")
	def mainSetpoint=(device.currentValue("baselineSetpoint"))?:(state?.scale== 'C')?21:70
	double nextLevel    
	if (scale == 'C') {
		nextLevel=(currentSetpointDelta)? currentSetpointDelta.toDouble():0.0        
		nextLevel = (nextLevel - 0.5).round(1)        
		traceEvent(settings.logFilter,"levelDown>$device.displayName, indiceZone=${state?.indiceZone}, nextLevel=$nextLevel",
			settings.trace,get_LOG_TRACE())        
		if (nextLevel < -10) {
			nextLevel = -10.0
		}
		sendEvent(name:"tempDelta", value: nextLevel, displayed: true)
        sendEvent(name:"zoneTemperatureDelta", value: nextLevel, scale)
        
		double newThermostatSetpoint=mainSetpoint.toDouble() + nextLevel     
		def tempValueString = String.format('%2.1f', newThermostatSetpoint.round(1))
		sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)     
        
	} else {
		nextLevel=(currentSetpointDelta)? currentSetpointDelta.toDouble().round():0        
		nextLevel = (nextLevel - 1)
		traceEvent(settings.logFilter,"levelDown>$device.displayName, indiceZone=${state?.indiceZone}, nextLevel=$nextLevel",
			settings.trace,get_LOG_TRACE())        
		if (nextLevel < -20) {
			nextLevel = -20
		}
		sendEvent(name:"tempDelta", value: nextLevel.intValue(), displayed: true)
        sendEvent(name:"zoneTemperatureDelta", value: nextLevel.intValue(), scale)
		double newThermostatSetpoint=mainSetpoint.toDouble() + nextLevel        
		def tempValueString = String.format('%2d', newThermostatSetpoint.intValue())
		sendEvent(name:"thermostatSetpoint", value: tempValueString, displayed: true, unit:scale)
	}
}

void setZoneActive() {
	def indiceZone=state?.indiceZone       
	traceEvent(settings.logFilter,"activeZone>about to make zone active, indiceZone=${indiceZone}",
		settings.trace,get_LOG_TRACE())
        
	sendEvent(name: "activeZone", value: "true", displayed: true,isStateChange:true)
	sendEvent(name: "switch", value: "on", displayed: true, isStateChange:true)
}
void setZoneInactive() {
	def indiceZone=state?.indiceZone       
	traceEvent(settings.logFilter,"inactiveZone>about to make zone inactive, indiceZone=${indiceZone}",
		settings.trace,get_LOG_TRACE())
        
	sendEvent(name: "activeZone", value: "false", displayed: true, isStateChange:true)
	sendEvent(name: "switch", value: "off", displayed: true, isStateChange:true)
}
void on() {	
	setZoneActive()
}

void off() {
	setZoneInactive()
}

void openAllVentsInZone() {
	def indiceZone=state?.indiceZone       
	traceEvent(settings.logFilter,"on>about to turn on all vents in zone, indiceZone=${indiceZone}",
		settings.trace,get_LOG_TRACE())
	parent.open_vents_in_zone(indiceZone)    	
        
	sendEvent(name: "allVentsState", value: "on",displayed: true, isStateChange:true)
    sendEvent(name: "allVentsinZone", value: "Active",displayed: true, isStateChange:true)
    sendEvent(name: "zoneAllVents", value: "Active")
    sendEvent(name: "switch", value: "on")
}

void closeAllVentsInZone() {
	def indiceZone=state?.indiceZone      
	traceEvent(settings.logFilter,"on>about to turn off all vents in zone, indiceZone=${indiceZone}",
		settings.trace,get_LOG_TRACE())        
	parent.close_vents_in_zone(indiceZone)    	
	sendEvent(name: "allVentsState", value: "off",displayed: true, isStateChange:true)
    sendEvent(name: "allVentsinZone", value: "Off",displayed: true, isStateChange:true)
    sendEvent(name: "zoneAllVents", value: "Off")
    sendEvent(name: "switch", value: "off")
}

private int get_LOG_ERROR() {return 1}
private int get_LOG_WARN()  {return 2}
private int get_LOG_INFO()  {return 3}
private int get_LOG_DEBUG() {return 4}
private int get_LOG_TRACE() {return 5}

def traceEvent(logFilter,message, displayEvent=false, traceLevel=4, sendMessage=false) {
	int LOG_ERROR= get_LOG_ERROR()
	int LOG_WARN=  get_LOG_WARN()
	int LOG_INFO=  get_LOG_INFO()
	int LOG_DEBUG= get_LOG_DEBUG()
	int LOG_TRACE= get_LOG_TRACE()
	int filterLevel=(logFilter)?logFilter.toInteger():get_LOG_WARN()

	if ((displayEvent) || (sendMessage)) {
		def results = [
			name: "verboseTrace",
			value: message,
			displayed: ((displayEvent)?: false)
		]	

		if ((displayEvent) && (filterLevel >= traceLevel)) {
			switch (traceLevel) {
				case LOG_ERROR:
					log.error "${message}"
				break
				case LOG_WARN:
					log.warn "${message}"
				break
				case LOG_INFO:
					log.info  "${message}"
				break
				case LOG_TRACE:
					log.trace "${message}"
				break
				case LOG_DEBUG:
				default:
					log.debug "${message}"
				break
			}  /* end switch*/              
		} /* end if displayEvent*/
		if (sendMessage) sendEvent (results)
	}
	      
}
private def getCustomImagePath() {
	return "https://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/"
}

private def getStandardImagePath() {
	return "http://cdn.device-icons.smartthings.com"
}