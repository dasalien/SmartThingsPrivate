/***
 *  My Flair Vent
 *  Copyright 2017-2020 Yves Racine
 *  LinkedIn profile: www.linkedin.com/in/yracine
 *  Version 1.3
 *  Refer to readme file for installation instructions.
 *  UI inspired by jscgs350
 *
 *  Developer retains all right, title, copyright, and interest, including all copyright, patent rights,
 *  trade secret in the Background technology. May be subject to consulting fees under an Agreement 
 *  between the Developer and the Customer. Developer grants a non exclusive perpetual license to use
 *  the Background technology in the Software developed for and delivered to Customer under this
 *  Agreement. However, the Customer shall make no commercial use of the Background technology without
 *  Developer's written consent.
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  
 *  Software Distribution is restricted and shall be done only with Developer's written approval.
 */
import java.text.SimpleDateFormat

include 'asynchttp_v1'
preferences {

	//	Preferences are optional as the Service Manager (MyFlairServiceMgr) will populate them

	input("structureId", "text", title: "StructureId", description:
		"The structureId of your location\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("ventId", "text", title: "Serial #", description:
		"The id of your vent\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("appKey", "text", title: "App Key", description:
		"The application key given by Flair\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("privateKey", "text", title: "Private Key", description:
		"The private key given by Flair\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces)\n or leave it empty (no tracing)")
	input("logFilter", "number",title: "(1=ERROR only,2=<1+WARNING>,3=<2+INFO>,4=<3+DEBUG>,5=<4+TRACE>)",  range: "1..5",
 		description: "optional" )  
}
metadata {
	definition(name: "Flair Vent V2", namespace: "acrosscable12814", mnmn: "SmartThingsCommunity", vid: "3230b888-d21e-399d-b3f5-1da897df04f3", ocfDeviceType: "x.com.st.d.vent", author: "Yves Racine") {//oic.d.switch
		capability "Switch Level"
		capability "Fan Speed"
        capability "Switch"
		capability "Refresh"
		capability "Sensor"
		capability "Temperature Measurement"
		capability "Battery"	// to be added later
		capability "Actuator"
		capability "Health Check"
		capability "acrosscable12814.flairPressure"
		capability "acrosscable12814.flairRoom"
		capability "acrosscable12814.flairZone"
		capability "acrosscable12814.flairRssi"
		capability "acrosscable12814.flairStatus"

		command "getVentInfo"
		command "getVentStates"
		command "getVentSensorReadings"
		command "getLevel"
		command "getOnOff"
		command "getPressure"
		command "getBattery"
		command "getTemperature"
		command "clearObstruction"
		command "ventFifty"
		command "ventHundred"
		command "ventLevelUp"
		command "ventLevelDown"
		command "setVent"
//		command "setVentState"	// No Longer supported by Flair APIs
		command "setLightPattern"
		command "getRoom"        
		command "setRoom"
		command "setRoomType"
		command "setRoomLevel"
//		command "setRoomWindows"	// Not currently supported by the Flair APIs
		command "setRoomUserDesiredTemp"
		command "setRoomSetpoint"
		command "setRoomTempAwayMin"
		command "setRoomTempAwayMax"
		command "setRoomHumidityAwayMin"
		command "setRoomHumidityAwayMax"
		command "setRoomPreHeatPrecool"
		command "setRoomFrozenPipePetProtect"
		command "setRoomAirReturn"        
		command "setRoomActive"        
		command "setRoomAwayMode"
//		command "setRoomOccupancyMode"  Not supported via the Flair APIs
		command "setRoomPucksInactive"        
		command "getZone"        
		command "getZonesList"        
		command "produceSummaryReport"

		attribute "structureId", "string"
		attribute "ventId", "string"
		attribute "ventName", "string"
		attribute "roomId", "string"
 		attribute "zoneList","string"
		attribute "roomName", "string"
		attribute "zoneName", "string"
		attribute "lightPattern", "string"
		attribute "updateInterval", "number"
		attribute "temperatureDisplay", "number"
		attribute "pressure", "number"
		attribute "rssi", "number"
		attribute "systemVoltage", "number"
		attribute "motorRunTime", "string"
		attribute "firmwareS", "string"
		attribute "ventList", "string"
		attribute "motorOpenDutyCyclePercent", "string" 
		attribute "motorCloseDutyCyclePercent","string"
		attribute "createdAt","string"
		attribute "updatedAt","string"
		attribute "read","string"
		attribute "setBy","string"
		attribute "changeSet","string"
		attribute "subGhzRadioTxPowerMw","string"
		attribute "motorMaxRotateTimeMs","string"
		attribute "ventData", "string"
		attribute "ventStateData", "string"
		attribute "sensorReadingsData", "string"

		attribute "roomId", "string"
		attribute "roomName", "string"
 		attribute "zoneName","string"
		attribute "zoneList", "string"
 		attribute "rmSetpoint","number"
 		attribute "rmCurrentTemperature","number"
 		attribute "rmUserDesiredTemperature","number"
 		attribute "rmPreheatPrecool","string"
 		attribute "rmOccupancyMode","string"
 		attribute "rmTempAwayMin","number"
 		attribute "rmTempAwayMax","number"
 		attribute "rmHumidityAwayMin","number"
 		attribute "rmHumidityAwayMax","number"
		attribute "rmHoldReason","string"        
		attribute "rmHoldUntil","string"        
		attribute "rmActive","string"        
		attribute "rmPucksInactive","string"        
		attribute "rmWindows","string"        
		attribute "rmAwayMode","string"        
		attribute "rmType","string"        
		attribute "rmLevl","string"        
		attribute "rmFrozenPipePetProtect","string"
		attribute "rmAirReturn","string"
		attribute "roomData","string"
		attribute "zoneData", "string"

		attribute "verboseTrace", "string"
		attribute "summaryReport","string"
	
	}

	// UI tile definitions
	tiles(scale: 2) {
		multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeIcon: true, decoration: "flat" ) {
			tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "on", action: "switch.off", label: "ON", icon: "st.vents.vent-open", backgroundColor: "#44b621"
				attributeState "off", action: "switch.on", label: "OFF", icon: "st.vents.vent", backgroundColor: "#ffffff"
				attributeState "obstructed", action: "clearObstruction", label: "OBSTRUCTION", icon: "st.vents.vent-open", backgroundColor: "#ff0000"
				attributeState "clearing", action: "", label: "CLEARING", icon: "st.vents.vent-open", backgroundColor: "#ffff33"
			}
			tileAttribute("device.level", key: "SLIDER_CONTROL") {
				attributeState "level", action: "setLevel"
			}
		}
		valueTile("name", "device.ventName", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("ventFifty", "device.level", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
			state "ventFifty", label: '50%', action: "ventFifty"
		}
		valueTile("ventHundred", "device.level", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
			state "ventHundred", label: '100%', action: "ventHundred"
		}
		standardTile("ventLevelUp", "device.level", width: 1, height: 1, inactiveLabel: false, decoration: "flat") {
			state "ventLevelUp", label:'25%', action:"ventLevelUp", icon:"st.thermostat.thermostat-up"
		}
		standardTile("ventLevelDown", "device.level", width: 1, height: 1, inactiveLabel: false, decoration: "flat") {
			state "ventLevelDown", label:'25%', action:"ventLevelDown", icon:"st.thermostat.thermostat-down"
		}
		valueTile("temperature", "device.temperatureDisplay",  width: 2, height: 2) {
			state "temperature", label: 'Vent   ${currentValue}',unit:"dF",
			backgroundColors:[
				[value: 0, color: "#153591"],
				[value: 7, color: "#1e9cbb"],
				[value: 15, color: "#90d2a7"],
				[value: 23, color: "#44b621"],
				[value: 29, color: "#f1d801"],
				[value: 33, color: "#d04e00"],
				[value: 36, color: "#bc2323"],
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
		valueTile("roomName", "device.roomName", width: 4, height: 1, decoration: "flat") {
			state "default", label: 'Room ${currentValue}', backgroundColor: "#ffffff"
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/room.png"
		}
		valueTile("zoneName", "device.zoneName", inactiveLabel: false, width: 4, height: 2) {
			state "default", label: 'Zone(s) ${currentValue}', backgroundColor: "#ffffff" 
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/zoning.jpg"
		}
		valueTile("rssi", "device.rssi", width: 2, height: 2, decoration: "flat") {
			state "default", label: 'rssi ${currentValue}', backgroundColor: "#ffffff"
		}
		valueTile("pressure", "device.pressure", width: 4, height: 1,  decoration: "flat") {
			state "pressure", label: 'Pressure ${currentValue}Pa', backgroundColor: "#ffffff", unit: "Pa"
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/pressure.png"
		}
		valueTile("battery", "device.battery", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "battery", label: 'Battery ${currentValue}%', backgroundColor: "#ffffff"
		}
		valueTile("updatedAt", "device.updatedAt", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Updated ${currentValue}', backgroundColor: "#ffffff"
		}
		standardTile("refresh", "device.power", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
				state "default", label: 'Refresh', action: "refresh.refresh", icon: "st.secondary.refresh"
		}
		htmlTile(name:"graphHTML", action: "getGraphHTML", width: 6, height: 8,  whitelist: ["www.gstatic.com"])

		main "switch"
		details([
 			"switch", 
			"ventLevelDown",
 			"ventFifty", "ventHundred", 
			"ventLevelUp", 
			"temperature", 
			"rssi", 
			"battery", 
			"updatedAt",            
 			"pressure",
			"roomName",
			"refresh",
			"zoneName",
//			"updatedAt",
			"graphHTML"             
		])
	}
}


def parse(String description) {}


def getTempBackgroundColors() {
	def results
	if (state?.scale == 'C') {
		// Celsius Color Range
		results = [
			[value: 0, color: "#153591"],
			[value: 7, color: "#1e9cbb"],
			[value: 15, color: "#90d2a7"],
			[value: 23, color: "#44b621"],
			[value: 29, color: "#f1d801"],
			[value: 33, color: "#d04e00"],
			[value: 36, color: "#bc2323"]
		]
	} else {
		results = [

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

mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}


def setFanSpeed(level) {
	if (level == 0) {
    	setLevel(0)
    } else if (level == 1) {
    	setLevel(25)
    } else if (level == 2) {
    	setLevel(50)
    } else if (level == 3) {
    	setLevel(75)
    } else {
    	setLevel(100)
    }
}

void installed() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	state?.scale=getTemperatureScale() 
	if (settings.trace) { 
		traceEvent(settings.logFilter, "installed>$device.displayName updated with settings: ${settings.inspect()}", settings.trace, get_LOG_TRACE())	
	}
}
/* @ping() is used by Device-Watch in attempt to reach the device
*/
def ping() {
	poll()
}

def updated() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	state?.scale = getTemperatureScale()
	retrieveDataForGraph()  
	data?.vents=[]
	data?.ventSensorReadings=[]
    
	traceEvent(settings.logFilter, "updated>$device.displayName updated with settings: ${settings.inspect()}", settings.trace)
}

//remove from the selected devices list in Service Manager
void uninstalled() {
	traceEvent(settings.logFilter, "executing uninstalled for ${this.device.displayName}", settings.trace)
	parent.purgeChildDevice(this)    
}


// ventId is single ventId (not a list)
private def refresh_vent(ventId="", asyncValues=null) {
	def sensorReadingsId
	def todayDay = new Date().format("dd",location.timeZone)
	def room
	String zonesList
    
	ventId=determine_vent_id(ventId)
	def structureId=determine_structure_id()    
	if (!data?.vents) {
		data?.vents=[]
	}        
    
	if (!asyncValues) {
		traceEvent(settings.logFilter, "refresh_vent>manual refresh", settings.trace, get_LOG_INFO())
		getVentInfo(ventId)
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
			// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter, "refresh_vent>$exceptionCheck for ventId=$ventId", settings.trace, get_LOG_ERROR())
			return
		}
		getVentSensorReadings(ventId,"current-reading")
		
	} else {
		traceEvent(settings.logFilter, "refresh_vent>poll refresh with values=$asyncValues", settings.trace, get_LOG_INFO())
		if (asyncValues.data instanceof Collection) {
 			data?.vents=asyncValues.data
		} else {
			data?.vents[0]=asyncValues.data            
		}            
		    
	}   
	// split the events into 2 for better performances.
	def dataEvents = [
		ventName: data?.vents[0]?.attributes?.name,	
		lightPattern: data?.vents[0]?.attributes?."setup-lightstrip",
		createdAt: formatDateInLocalTime(data?.vents[0]?.attributes?."created-at"),
		updatedAt: formatDateInLocalTime(data?.vents[0]?.attributes?."updated-at"),
		level: data?.vents[0]?.attributes?."percent-open",
		updateInterval: data?.vents[0]?.attributes?."reporting-interval-ms"
	]
	if (dataEvents.level && dataEvents.level>5) {
		dataEvents?.switch="on"		    
	} else {
		dataEvents?.switch="off"		    
	}    

    if(dataEvents.level == 0) {
    	dataEvents?.fanSpeed=0
    } else if(dataEvents.level < 26) {
    	dataEvents?.fanSpeed=1
    } else if(dataEvents.level < 51) {
    	dataEvents?.fanSpeed=2
    } else if(dataEvents.level < 76) {
    	dataEvents?.fanSpeed=3
    } else {
    	dataEvents?.fanSpeed=4
    }
	traceEvent(settings.logFilter,"refresh_vent>dataEvents= ${dataEvents}", settings.trace)    
    
	generateEvent(dataEvents)

	//getVentSensorReadings(ventId,"current-reading")
//	getVentStates(ventId,"current-state")	// do not get the state anymore		       
        
	def roomId    
	try {    
		roomId=data.vents[0]?.relationships?.room?.data?.id 
	} catch (any) {        
		traceEvent(settings.logFilter, "refresh_vent>roomId not found- vent $ventId not associated to a room", settings.trace, get_LOG_WARN())
	}      
	roomId= (roomId)?: device.currentValue("roomId")   // get the previous (saved) roomId value if any 
	if (roomId) {    
		if ((!asyncValues)  || ((!state?.today) || (state?.today != todayDay))) { // every day refresh the room and zone info
			room=getRoom(roomId,true)    
			parent.getObject("rooms",roomId, "zones")
			parent.updateZones(this)    
			state?.today=todayDay        
		} else {   
			room=getRoom(roomId)
		}            
	}    
	zonesList = getZonesList()		            
    
	traceEvent(settings.logFilter,"refresh_vent>data?.vents=${data?.vents[0]}", settings.trace)
//	traceEvent(settings.logFilter,"refresh_vent>data?.ventStates=${data?.ventStates[0]}", settings.trace)
	dataEvents = [
		motorOpenDutyCyclePercent: data?.vents[0]?.attributes?."motor-open-duty-cycle-percent", 
		motorCloseDutyCyclePercent: data?.vents[0]?.attributes?."motor-close-duty-cycle-percent",
//		setBy:data?.ventStates[0]?.attributes?."set-by",
//		read:data?.ventStates[0]?.attributes?.read,
//		changeSet:data?.ventStates[0]?.attributes?."changeset",
		demoMode: data?.vents[0]?.attributes?."demo-mode",
		subGhzRadioTxPowerMw:data?.vents[0]?.attributes?."sub-ghz-radio-tx-power-mw",
		motorMaxRotateTimeMs:data?.vents[0]?.attributes?."motor-max-rotate-time-ms",
		battery: getBatteryUsage(),
		systemVoltage: data?.ventSensorReadings[0]?.attributes?."system-voltage",       
		temperature: data?.ventSensorReadings[0]?.attributes?."duct-temperature-c",
		temperatureDisplay: data?.ventSensorReadings[0]?.attributes?."duct-temperature-c",
		pressure: data?.ventSensorReadings[0]?.attributes?."duct-pressure", 
		rssi: data?.ventSensorReadings[0]?.attributes?.rssi,
		motorRunTime: data?.ventSensorReadings[0]?.attributes?."motor-run-time",                    
		firmwareS: data?.ventSensorReadings[0]?.attributes?."firmware-version-s",                    
		roomId:roomId,           
		roomName:room?.attributes?.name,           
		rmSetpoint:room?.attributes?."set-point-c",        
		rmCurrentTemperature:room?.attributes?."current-temperature-c",        
		rmUserDesiredTemperature:room?.attributes?."user-temperature-preference-c",        
		rmOccupancyMode:room?.attributes?."occupancy-mode",        
		rmPreheatPrecool:room?.attributes?."preheat-precool",        
		rmTempAwayMin:room?.attributes?."temp-away-min-c",        
		rmTempAwayMax:room?.attributes?."temp-away-max-c",        
		rmHumidityAwayMin:room?.attributes?."humidity-away-min",        
		rmHumidityAwayMax:room?.attributes?."humidity-away-max",        
		rmHoldReason:room?.attributes?."hold-reason",        
		rmHoldUntil:formatDateInLocalTime(room?.attributes?."hold-until"),        
		rmActive:room?.attributes?.active,        
		rmPucksInactive:room?.attributes?."pucks-inactive"?.toLowerCase(),        
		rmWindows:room?.attributes?.windows,        
		rmAwayMode:room?.attributes?."room-away-mode",        
		rmAirReturn:room?.attributes?."air-return",  
		rmType:room?.attributes?."room-type",        
		rmLevl: room?.attributes?."level",       
		rmFrozenPipePetProtect:room?.attributes?."frozen-pipe-pet-protect",
		zoneName:zonesList
	]
	traceEvent(settings.logFilter,"refresh_vent>dataEvents= ${dataEvents}", settings.trace)    
    
	generateEvent(dataEvents)
    
	traceEvent(settings.logFilter, "refresh_vent>done for ventId=${ventId}", settings.trace)
}
private def getBatteryUsage() {
	def TOTAL_NOMINAL_VOLTAGE=3 
	Double results=0.0

	if (!data?.ventSensorReadings) {
		return    
	}
	def systemVoltage=data?.ventSensorReadings[0]?.attributes?."system-voltage"
	if (systemVoltage) {
		results= (systemVoltage/TOTAL_NOMINAL_VOLTAGE)*100
		results=(results >100)?100:results 
	}
	return results as Double
}

private void generateEvent(Map results) {
	traceEvent(settings.logFilter, "generateEvent>parsing data $results", settings.trace)

	state?.scale = getTemperatureScale() // make sure to display in the right scale
	def scale = state?.scale

	if (results) {
		results.each {name,value->
        
			String upperFieldName=name.toUpperCase()
			def isDisplayed = true
            
            //log.debug("Generate Events: ${upperFieldName} : ${value}")

			// 			Temperature variable names for display contain 'display'            

			if (upperFieldName.contains("DISPLAY")) {

				String tempValueString
				double tempValue
				if (scale == "C") {
					tempValue = getTemperatureValue(value).toDouble().round(1)
					tempValueString = String.format('%2.1f', tempValue)
				} else {
					tempValue = getTemperatureValue(value).toDouble().round()
					tempValueString = String.format('%2d', tempValue.intValue())
				}
				def isChange = isStateChange(device, name, tempValueString)

				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed)

				// 			Temperature variable names contain 'temp' or 'setpoint' (not for display)           

			} else if ((upperFieldName.contains("TEMP")) || (upperFieldName.contains("SETPOINT"))) {  

				double tempValue = getTemperatureValue(value).round(1)
				String tempValueString = String.format('%2.1f', tempValue)
				if (tempValueString != '0.0') {    // don't post value if all zeros                
					def isChange = isStateChange(device, name, tempValueString)
					isDisplayed = isChange
					sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed, isStateChange: isChange)
				}
			} else if ((upperFieldName.contains("HUMIDITY")) || (upperFieldName.contains("LEVEL")) || (upperFieldName.contains("BATTERY"))) {
				value = (value?:0)
				double humValue = value?.toDouble().round(0)
				String humValueString = String.format('%2d', humValue.intValue())
				def isChange = isStateChange(device, name, humValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: humValueString, unit: "%", displayed: isDisplayed, isStateChange: isChange)
			} else if (upperFieldName.contains("PRESSURE")) {
				value = (value?:0)
				double pressureValue = (value?.toDouble() * 1000).round(0) // Convert Kpa to Pa
				String pressureValueString = String.format('%2d', pressureValue.intValue())
				def isChange = isStateChange(device, name, pressureValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: pressureValueString, , unit: "Pa", displayed: isDisplayed, isStateChange: isChange)
                sendEvent(name: "flairPressure", value: "${pressureValueString} Pa")
			} else if (upperFieldName.contains("DATA")) { // data variable names contain 'data'

				sendEvent(name: name, value: value, displayed: (settings.trace?: false))

			} else if (upperFieldName.contains("RSSI")) {
				sendEvent(name: "flairRSSI", value: "${value} dB")

			} else if (upperFieldName.contains("ROOMNAME")) { 
				sendEvent(name: "flairRoom", value: value)

			} else if (upperFieldName.contains("ZONENAME")) { 
				sendEvent(name: "flairZone", value: value)

			} else if (upperFieldName.contains("UPDATEDAT")) { 
				sendEvent(name: "flairStatus", value: "${value} ")

			} else {
            
				if (value != null && value != 'null' && value != '[:]' && value != '[]') {           
					def isChange = isStateChange(device, name, value.toString())
					isDisplayed = isChange

					sendEvent(name: name, value: value.toString(), isStateChange: isChange, displayed: isDisplayed)
				}
			}
		}
	}
}




private def getTemperatureValue(value) {
	if (!value) return 0.0 as Double
	double celsius = value.toDouble()
	if (state?.scale == "C") {
		return celsius 
	} else {
		return cToF(celsius) 
	}
}



private void api(method, id, args, success = {}) {
	def MAX_EXCEPTION_COUNT = 5
	String URI_ROOT = "${get_URI_ROOT()}"

	if (!isLoggedIn()) {
		login()
	}

	if (isTokenExpired()) {
		traceEvent(settings.logFilter,"api>need to refresh tokens", settings.trace)

		if (!refresh_tokens()) {
			if ((exceptionCheck) && (state.exceptionCount >= MAX_EXCEPTION_COUNT) && (exceptionCheck.contains("Unauthorized"))) {
				traceEvent(settings.logFilter,"api>$exceptionCheck, not able to renew the refresh token; need to re-login to flair via MyFlairServiceMgr...", true)
			}
		} else {

			// Reset Exceptions counter as the refresh_tokens() call has been successful 
			state.exceptionCount = 0
		}

	}

	def methods = [
		'getVent': [uri: "${URI_ROOT}/api/vents/${id}",
			type: 'get'],
		'getVentSensorReadings': [uri: "${URI_ROOT}/api/vents/${id}/sensor-readings",
			type: 'get'],
		'getVentStates': [uri: "${URI_ROOT}/api/vents/${id}/vent-states",
			type: 'get'],
		'current-state': [uri: "${URI_ROOT}/api/vents/${id}/current-state",
			type: 'get'],
		'previous-state': [uri: "${URI_ROOT}/api/vents/${id}/vent-states",
			type: 'get'],
		'setVentState': [uri: "${URI_ROOT}/api/vent-states", 
			type: 'post'],
		'setVent': [uri: "${URI_ROOT}/api/vents", 
			type: 'put']
	]
	def request = methods.getAt(method)
	if (request.type=="get" && args) {
		def args_encoded = java.net.URLEncoder.encode(args.toString(), "UTF-8")
		request.uri=request.uri + "?${args_encoded}"    
//		request.uri=request.uri + "?${args}"    
	}    
    
    
	doRequest(request.uri, args, request.type, success)
	if (state.exceptionCount >= MAX_EXCEPTION_COUNT) {
		def exceptionCheck = device.currentValue("verboseTrace")
		traceEvent(settings.logFilter, "api>error: found a high number of exceptions (${state.exceptionCount}), last exceptionCheck=${exceptionCheck}, about to reset counter", true)
		if (!exceptionCheck.contains("Unauthorized")) {
			state.exceptionCount = 0 // reset the counter as long it's not unauthorized exception
			sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		}
	}

}

// @doRequest: need to be authenticated in before this is called. So don't call this. Call api.
private void doRequest(uri, args, type, success) {
	def params = [
		uri: uri,
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//			'Content-Type': "application/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"
		]
	]
	try {
		traceEvent(settings.logFilter, "doRequest>about to ${type} with uri ${params.uri}, (unencoded)args= ${args}", settings.trace)
		traceEvent(settings.logFilter,"doRequest>about to ${type} with params= ${params}", settings.trace)
		if (type == 'post') {
			params?.body=args        
			httpPostJson(params, success)
		} else if (type == 'get') {
			httpGet(params, success)
		}
		/* when success, reset the exception counter */
		state.exceptionCount = 0
		traceEvent(settings.logFilter, "doRequest>done with ${type}", settings.trace)

	} catch (java.net.UnknownHostException e) {
		traceEvent(settings.logFilter, "doRequest> Unknown host ${params.uri}", true, get_LOG_ERROR())
	} catch (java.net.NoRouteToHostException e) {
		traceEvent(settings.logFilter, "doRequest> No route to host ${params.uri}", true, get_LOG_ERROR())
	} catch (e) {
		traceEvent(settings.logFilter,"doRequest>exception $e for ${params.uri}", settings.trace, get_LOG_ERROR())
		state?.exceptionCount = (state?.exceptionCount?:0) +1
		throw e        
	} 
    
}

void setRoomType(value) {
	traceEvent(settings.logFilter,"setting roomType: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")
    
	setRoom(roomId, ['room-type':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setLevel>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmType", value: value)

	traceEvent(settings.logFilter, "setRoomType>done for roomId=${roomId}", settings.trace)
}

// @value must be an integer

void setRoomLevel(value) {
	traceEvent(settings.logFilter,"setting roomLevel: ${value}", settings.trace)

	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['level':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomLevel>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmLevl", value: value)

	traceEvent(settings.logFilter, "setRoomLevel>done for roomId=${roomId}", settings.trace)
}
/*
void setRoomWindows(value) {
	traceEvent(settings.logFilter,"setting roomWindows: ${value}", settings.trace)

	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['windows':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomWindows>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmWindows", value: value)

	traceEvent(settings.logFilter, "setRoomWindows>done for roomId=${roomId}", settings.trace)
}

*/

// @value must be a real

void setRoomTempAwayMin(value) {
	traceEvent(settings.logFilter,"setting roomTempAwayMin: ${value}", settings.trace)
	def tempValue = value
    
	if (state?.scale !='C') {
		tempValue = fToC(value)    
	} 
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['temp-away-min-c':tempValue])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomTempAwayMin>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmTempAwayMin", value: value)

	traceEvent(settings.logFilter, "setRoomTempAwayMin>done for roomId=${roomId}", settings.trace)
}


// @value must be a real

void setRoomTempAwayMax(value) {
	traceEvent(settings.logFilter,"setting roomTempAwayMax: ${value}", settings.trace)
	def tempValue = value
    
	if (state?.scale !='C') {
		tempValue = fToC(value)    
	} 
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['temp-away-max-c':tempValue])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomTempAwayMax>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmTempAwayMax", value: value)

	traceEvent(settings.logFilter, "setRoomTempAwayMax>done for roomId=${roomId}", settings.trace)
}

// @value must be an integer
void setRoomHumidityAwayMin(value) {
	traceEvent(settings.logFilter,"setting roomHumidityAwayMin: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['humidity-away-min':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomHumidityAwayMin>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmHumidityAwayMin", value: value)

	traceEvent(settings.logFilter, "setRoomHumidityAwayMin>done for roomId=${roomId}", settings.trace)
}

// @value must be a real
void setRoomHumidityAwayMax(value) {
	traceEvent(settings.logFilter,"setting roomHumidityAwayMax: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['humidity-away-max':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomHumidityAwayMax>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmHumidityAwayMax", value: value)

	traceEvent(settings.logFilter, "setRoomHumidityAwayMax>done for roomId=${roomId}", settings.trace)
}

// @value must be a boolean 
void setRoomPreHeatPrecool(value) {
	traceEvent(settings.logFilter,"setting roomPreheatPrecool: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")
	boolean valueBoolean =(value==true)?:false    
	setRoom(roomId, ['preheat-precool':valueBoolean])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomPreHeatPrecool>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmPreheatPrecool", value: valueBoolean)

	traceEvent(settings.logFilter, "setRoomPreHeatPrecool>done for roomId=${roomId}", settings.trace)
}

// @value must be a boolean 
void setRoomFrozenPipePetProtect(value) {
	traceEvent(settings.logFilter,"setting roomFrozenPipePetProtect: ${value}", settings.trace)
	boolean valueBoolean =(value==true)?:false    
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['frozen-pipe-pet-protect':valueBoolean])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomFrozenPipePetProtect>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmFrozenPipePetProtect", value: valueBoolean)

	traceEvent(settings.logFilter, "setRoomFrozenPipePetProtect>done for roomId=${roomId}", settings.trace)
}

// @value must be a boolean 
void setRoomActive(value) {
	traceEvent(settings.logFilter,"setting roomActive: ${value}", settings.trace)
	boolean valueBoolean =(value==true)?:false    
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['active':valueBoolean])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomFrozenPipePetProtect>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmActive", value: valueBoolean)

	traceEvent(settings.logFilter, "setRoomActive>done for roomId=${roomId}", settings.trace)
}

// @value must be a boolean 

void setRoomAirReturn(value) {
	traceEvent(settings.logFilter,"setting roomAirReturn: ${value}", settings.trace)
	boolean valueBoolean =(value==true)?:false    
	def roomId=device.currentValue("roomId")

	setRoom(roomId, ['air-return':valueBoolean])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomAirReturn>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmAirReturn", value: valueBoolean)

	traceEvent(settings.logFilter, "setRoomAirReturn>done for roomId=${roomId}", settings.trace)
}

// @value must be a 'smart away' or 'off' (default)

void setRoomAwayMode(value) {
	def FLAIR_SMART_AWAY ='Smart Away'
	def FLAIR_OFF_MODE ='Off Only'
    
	traceEvent(settings.logFilter,"setting roomAwayMode: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")

	value=( (value.toLowerCase().contains('smart')) ? FLAIR_SMART_AWAY : FLAIR_OFF_MODE)
	setRoom(roomId, ['room-away-mode':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomAwayMode>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmAwayMode", value: value)

	traceEvent(settings.logFilter, "setRoomAwayMode>done for roomId=${roomId}", settings.trace)
}

// @value must be 'active' or 'inactive'

void setRoomPucksInactive(value) {
	def FLAIR_PUCK_ACTIVE ='Active'
	def FLAIR_PUCK_INACTIVE ='Inactive'
	traceEvent(settings.logFilter,"setting roomPucksInactive: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")

	value=(value.toLowerCase().contains.('inactive')? FLAIR_PUCK_INACTIVE:FLAIR_PUCK_ACTIVE)
	setRoom(roomId, ['pucks-inactive':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomPucksInactive>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmPucksInactive", value: value.toLowerCase())  // In lower case, to be consistent with other ST devices

	traceEvent(settings.logFilter, "setRoomPucksInactive>done for roomId=${roomId}", settings.trace)
}

// @value must be a real

void setRoomUserDesiredTemp(value) {
	traceEvent(settings.logFilter,"setting rmUserDesiredTemperature: ${value}", settings.trace)
	def tempValue =tempValue=(state?.scale !='C') ? fToC(value) : value
	def roomId=device.currentValue("roomId")
	setRoom(roomId, ['user-desired-temperature-c':tempValue])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomUserDesiredTemp>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmUserDesiredTemperature", value: value)

	traceEvent(settings.logFilter, "setRoomUserDesiredTemp>done for roomId=${roomId}", settings.trace)
}


// @value must be a real

void setRoomSetpoint(value) {
	traceEvent(settings.logFilter,"setting rmSetpoint: ${value}", settings.trace)
	def roomId=device.currentValue("roomId")
	def tempValue=(state?.scale !='C') ? fToC(value) : value    
	setRoom(roomId, ['set-point-c':tempValue])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setRoomSetpoint>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "rmSetpoint", value: value)

	traceEvent(settings.logFilter, "setRoomSetpoint>done for roomId=${roomId}", settings.trace)
}


// @roomId	Id of the Room, by default the current one
// @attribute	Room Attribute(s) set to be changed in Map
void setRoom(roomId, attributes=[]) {
	String URI_ROOT = "${get_URI_ROOT()}"

	if (!roomId) {
		roomId=device.currentValue("roomId")
    
	}    
	if (attributes == null || attributes == "" || attributes == [] ) {
		traceEvent(settings.logFilter, "setRoom>attributes set is empty, exiting", settings.trace)
		return        
	}
    
	def currentAttributes =new groovy.json.JsonBuilder(attributes) 
	def bodyReq = '{"data":{"id":"' + roomId + '","type":"rooms","attributes":' + currentAttributes +'}}'
	def args = [
			attributes: bodyReq
		]
    
	def params = [
			uri: "${URI_ROOT}/api/rooms/${roomId}",
			body: bodyReq,            
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//				'Content-Type': "text/json",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	traceEvent(settings.logFilter, "setRoom>about to call patchObjectAsync with bodyReq=${bodyReq}", settings.trace)
	asynchttp_v1.patch('patchObjectAsync', params, args)
	runIn(30,"refresh_room_async")    // refresh the room values in 30 sec.
	traceEvent(settings.logFilter, "setRoom>done for roomId=${roomId}", settings.trace)
}

void refresh_room_async() {
	getRoom("",true) 	// force update of the local cache            
}


def patchObjectAsync(response, data) {	
	def FLAIR_SUCCESS =200
	def FLAIR_CREATED =201    
	def TOKEN_EXPIRED=401
	def attributes =  data?.attributes    
	  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter, "patchObjectAsync>Flair's Access token has expired for patch api call with attributes $attributes, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()
		} else {
			traceEvent(settings.logFilter,"patchObjectAsync> - patch api call with attributes $attributes failed, Error: $response.status",settings.trace,get_LOG_ERROR())
			state?.exceptionCount=state?.exceptionCount +1        
		}
        
	} else {
		if (response.status == FLAIR_SUCCESS) {
			traceEvent(settings.logFilter,"patchObjectAsync> - patch api call with attributes $attributes sent successfully",settings.trace)
			state?.exceptionCount=0
			state.lastPollTimestamp = now()
		}
	}
}


// @ventId		Id of the Vent, by default the current one
// @attribute		Vent Attribute(s) set to be changed in Map
void setVent(ventId, attributes=[]) {
	String URI_ROOT = "${get_URI_ROOT()}"

	if (!ventId) {
		ventId=device.currentValue("ventId")
    
	}    
	if (attributes == null || attributes == "" || attributes == [] ) {
		traceEvent(settings.logFilter, "setVent>attributes set is empty, exiting", settings.trace)
		return        
	}
    
	def currentAttributes =new groovy.json.JsonBuilder(attributes) 
	def bodyReq = '{"data":{"id":"' + ventId + '","type":"vents","attributes":' + currentAttributes +'}}'
	def args = [
			attributes: bodyReq
		]
  
	def params = [
			uri: "${URI_ROOT}/api/vents/${ventId}",
			body: bodyReq,            
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	traceEvent(settings.logFilter, "setVent>about to call patchObjectAsync with bodyReq=${bodyReq}", settings.trace)
	asynchttp_v1.patch('patchObjectAsync', params, args)
	traceEvent(settings.logFilter, "setVent>done for ventId=${ventId}", settings.trace)

}




// @id			Id of the vent
// @stateAttribute	Vent Attribute(s) to be changed in Map
void setVentState(ventId, stateAttributes=[]) {
	def FLAIR_SUCCESS =200
	def FLAIR_CREATED =201    
	def TOKEN_EXPIRED=401


	ventId = determine_vent_id(ventId)
	if (stateAttributes == null || stateAttributes == "" || stateAttributes == [] ) {
		traceEvent(settings.logFilter, "setVentState>currentState object is empty, exiting", settings.trace)
		return        
	}
    
	def currentStateAttributes =new groovy.json.JsonBuilder(stateAttributes) 
	def bodyReq = '{"data":{"type":"vent-states","attributes":' + currentStateAttributes +',"relationships":{"vent":{"data":{"type":"vents","id":"' + ventId + '"}}}}}'
    
	traceEvent(settings.logFilter, "setVentState>about to call setVentState api with bodyReq=${bodyReq}", settings.trace)
	int statusCode = 1
	int j = 0
	while ((statusCode != FLAIR_SUCCESS && statusCode != FLAIR_CREATED) && (j++ < 2)) { // retries once if api call fails
		api('setVentState', ventId, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "setVentState>ventId=${ventId}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()           
			}
			if (statusCode in [FLAIR_SUCCESS, FLAIR_CREATED]) {
				/* when success, reset the exception counter */
				state.exceptionCount = 0
				traceEvent(settings.logFilter, "setVentState>done for ${ventId}", settings.trace)
				state.lastPollTimestamp = now()  // To avoid polling right after setting the vent level
			} else {
				state.exceptionCount = state.exceptionCount + 1
				traceEvent(settings.logFilter, "setVentState>error=${statusCode.toString()} for ${ventId}", settings.trace)
			} /* end if statusCode */
		} /* end api call */
	} /* end while */
}


// @id		Id of the vent, by default, retrieve all vents under a user 
// @postData	indicates whether the data should be posted for further processing [optional]
void getVentInfo(id, postData = false) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	def ventData = []
	def ventList = ""
	def bodyReq = ""
	def statusCode = true
	int j = 0

	traceEvent(settings.logFilter, "getVentInfo>bodyReq=${bodyReq},id=$id", settings.trace)

	if (!data?.vents) {    
		data?.vents=[]
	}    
	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getVent', id, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode == TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getVentInfo>ventId=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getVentInfo>resp.data=${resp.data}", settings.trace)

				if (resp.data instanceof Collection) {
					data?.vents = resp.data.data
				} else {
					data?.vents[0] = resp.data.data
				}
				                
				data?.vents?.each {
					def ventId = it.id
					def name = it?.attributes?.name
					def lightPattern = it.attributes?."setup-lightstrip"
					def room = it.relationships?.room                         
                        
					traceEvent(settings.logFilter, "getVentInfo>ventId=${ventId}, name= $name, " +
						"room=${room}", settings.trace)
					if (postData) {
						traceEvent(settings.logFilter, "getVentInfo>adding ${it} to ventData", settings.trace)
						ventData << it // to be transformed into Json later
					}
					ventList = ventList + id + ','
				} /* end for each vent */
				traceEvent(settings.logFilter, "getVentInfo>done for ventId=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getVentInfo>error=${statusCode.toString()} for ventId=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	def ventDataJson = ""

	if (ventData != []) {
		ventDataJson = new groovy.json.JsonBuilder(ventData)
	}
	/*	
	traceEvent(settings.logFilter,"getVentInfo>ventDataJson=${ventDataJson}", settings.trace)
	*/
	def ventEvents = [
			ventData: "${ventDataJson.toString()}",
			ventList: "${ventList.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"getVentInfo>ventListEvents to be sent= ${ventListEvents}", settings.trace)
	*/
	generateEvent(ventEvents)

}


// @id			Id of the vent, by default the current vent
// @method		possible values: current-state, previous-state, by default=all states
// @postData		indicates whether the data should be posted for further processing [optional]
// @postTimestamp	timestamp (from) threshold to post states [optional]

void getVentStates(id, method="",postData=false,postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	String FLAIR_CURRENT_STATE="current-state"    
	String FLAIR_PREVIOUS_STATE="previous-state"   
	String FLAIR_ALL_STATES="getVentStates"    
	def MAX_INDICE=5    
	def ventData = []
	def bodyReq = ""
	def statusCode = true
	int j = 0

	if (!id) {
		id = device.currentValue("ventId")    
		if (!id) {
			traceEvent(settings.logFilter, "getVentStates>id is null, exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_STATE)?FLAIR_CURRENT_STATE:((method==FLAIR_PREVIOUS_STATE)?FLAIR_PREVIOUS_STATE:FLAIR_ALL_STATES)   
/*
	if (method in [FLAIR_CURRENT_STATE, FLAIR_PREVIOUS_STATE] ) {
		bodyReq=bodyReq + "page[size]=1"    
	}
*/    
	traceEvent(settings.logFilter, "getVentStates>method=$method,bodyReq=${bodyReq},id=$id", settings.trace)
//	log.debug "getVentStates>method=$method,bodyReq=${bodyReq},id=$id"

	if (!data?.ventStates) {
		data?.ventStates=[]    
	}    
	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api(method, id, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode == TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getVentStates>ventId=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getVentStates>resp.data=${resp.data}", settings.trace)

				if (resp.data.data instanceof Collection) {
					data?.ventStates = resp.data.data
				} else {
					data?.ventStates[0] = resp.data.data
				}
				int max_ind = (data?.ventStates) ? (data?.ventStates.size()-1):-1	
				max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
				for (i in 0..max_ind) {
					def lightstrip= data?.ventStates[i]?.attributes?."lightstrip"                        
					def updateInterval= data?.ventStates[i]?.attributes?."reporting-interval-ms"                     
					def level = data?.ventStates[i]?.attributes?."percent-open"
					traceEvent(settings.logFilter, "getVentStates>ventId=${id},state no ${i},level=$level" +
						"lightstrip=$lightstrip,updateInterval=$updateInterval", settings.trace)
					if (method == FLAIR_CURRENT_STATE) {
						if (postData) { 
							ventData <<data?.ventStates[i]
						}
						break                   
					} else if ((method == FLAIR_PREVIOUS_STATE) && (((max_ind > 0) && (i==1)) || (max_ind==0))) {
						if (postData) { 
							ventData =[] // just generate the json for the last row
							ventData << data?.ventStates[i]
						}                            
						break                   
					} else if (postData) {
						if (postTimestamp) {
							// Save states greater than timestamp 
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getVentStates>adding ${data?.ventStates[i]} to ventData, createdDate (${createdDate.getTime()}) is greater than timestamp ($postTimestamp)", settings.trace)
								ventData << data?.ventStates[i] // to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getVentStates>adding ${data?.ventStates[i]} to ventData", settings.trace)
							ventData << data?.ventStates[i]// to be transformed into Json later
						}
					}
			                    
				} /* end for each state */
				traceEvent(settings.logFilter, "getVentStates>done for ventId=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getVentStates>error=${statusCode.toString()} for ventId=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */

	generate_vent_state_json(ventData)
}

private void generate_vent_state_json(ventData) {
	def ventDataJson = ""

	if (ventData != []) {
		ventDataJson = new groovy.json.JsonBuilder(ventData)
	}
	/*	
	traceEvent(settings.logFilter,"getVentStates>ventDataJson=${ventDataJson}", settings.trace)
	*/
	def ventEvents = [
			ventStateData: "${ventDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"getVentStates>ventListEvents to be sent= ${ventListEvents}", settings.trace)
	*/
	generateEvent(ventEvents)
}

// @id			Id of the vent, by default the current vent
// @postData		indicates whether the data should be posted for further processing
// @method		possible values: current-reading, previous-reading, by default=all readings
//@postTimestamp	timestamp (from) threshold to post readings [optional]
void getVentSensorReadings(id, method="",postData=false,postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	String FLAIR_CURRENT_READING="current-reading"   
	def MAX_INDICE=5    
	String FLAIR_PREVIOUS_READING="previous-reading"    
	String FLAIR_ALL_READINGS="getVentSensorReadings"    
	def ventData = []
	def statusCode = true
	int j = 0
	def bodyReq=""
    
	if (!id) {
		id = device.currentValue("ventId")    
		if (!id) {
			traceEvent(settings.logFilter, "getVentSensorReadings>id=$id exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_READING)?FLAIR_CURRENT_READING:((method==FLAIR_PREVIOUS_READING)?FLAIR_PREVIOUS_READING:FLAIR_ALL_READINGS)    
/*
	if (method in [FLAIR_CURRENT_READING, FLAIR_PREVIOUS_READING] ) {
		bodyReq=bodyReq + "page[size]=1"    
	}
*/
	traceEvent(settings.logFilter, "getVentSensorReadings>method=$method,bodyReq=${bodyReq}, id=$id", settings.trace)
//	log.debug "getVentSensorReadings>method=$method,bodyReq=${bodyReq},id=$id"
    
	if (!data?.ventSensorReadings) {    
		data?.ventSensorReadings=[]
	}    

	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getVentSensorReadings', id, bodyReq) {resp->
			statusCode = resp.status
			if (statusCode == TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getVentSensorsReadings>id=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getVentSensorsReadings>resp.data=${resp.data}", settings.trace)
/*                
				if (resp.data.data instanceof Collection) {
					data?.ventSensorReadings = resp.data.data
				} else {
					data?.ventSensorReadings[0] = resp.data.data
				}
*/                
				int max_ind= (data?.ventSensorReadings) ? (data?.ventSensorReadings.size()-1) :-1               
				max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
				for (i in 0..max_ind) {
					data?.ventSensorReadings[i] =resp.data.data[i]				                
					def temperature = resp.data.data[i]?.attributes?."duct-temperature-c"
					def pressure = resp.data.data[i]?.attributes?."duct-pressure"
					def rssi = resp.data.data[i]?.attributes?.rssi
					def level = resp.data.data[i]?.attributes?."percent-open"
					def systemVoltage = resp.data.data[i]?.attributes?."systemVoltage"						                    

					traceEvent(settings.logFilter, "getVentSensorsReadings>readings no ${i}, temperature= $temperature, pressure=$pressure," +
						"rssi=$rssi, openLevel=$level, systemVoltage=$systemVoltage", settings.trace)
					if (method == FLAIR_CURRENT_READING) {
						if (postData) { 
							ventData << data?.ventSensorReadings[i] 
						}                        
						break                    
					} else if ((method == FLAIR_PREVIOUS_READING) && (((max_ind > 0) && (i==1)) || (max_ind ==0))) {
						if (postData) { 
							ventData =[] // just generate the json for the last row
							ventData << data?.ventSensorReadings[i]                         
						}                            
						break                  
					} else if (postData) {
						if (postTimestamp) {
							// Save readings greater than timestamp (
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getVentSensorsReadings>adding ${data?.ventSensorReadings[i]} to ventData, createdDate ($createdDate.getTime()) is greater than timestamp ($postTimestamp)", settings.trace)
								ventData << data?.ventSensorReadings[i] // to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getVentSensorsReadings>adding ${data?.ventSensorReadings[i]} to ventData", settings.trace)
							ventData << data?.ventSensorReadings[i] // to be transformed into Json later
						}                    
					}                    
				} /* end for each sensor-readings  */
				traceEvent(settings.logFilter, "getVentSensorsReadings>done for id=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getVentSensorsReadings>error=${statusCode.toString()} for id=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */

	generate_vent_readings_json(ventData) 
}

private void generate_vent_readings_json(ventData) {
	def ventDataJson = ""

	if (ventData != []) {
		ventDataJson = new groovy.json.JsonBuilder(ventData)
	}
	/*	
	traceEvent(settings.logFilter,"getVent>ventDataJson=${ventDataJson}", settings.trace)
	*/
	def ventEvents = [
			sensorReadingsData: "${ventDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"getVentInfo>ventListEvents to be sent= ${ventListEvents}", settings.trace)
	*/
	generateEvent(ventEvents)
}

void updateZones(zones) {
	traceEvent(settings.logFilter,"updateZones>zones from parent=$zones",settings.trace,get_LOG_TRACE())        
	if (!data?.zones) {
		data?.zones=[]    
	}    
	data?.zones=zones
	traceEvent(settings.logFilter,"updateZones>data.zones=${data?.zones}",settings.trace,get_LOG_TRACE())        
}

void updateStructures(structures) {
	traceEvent(settings.logFilter,"updateStructures>structures from parent=$structures",settings.trace,get_LOG_TRACE())        
	if (!data?.structures) {
		data?.structures=[]    
	}    
	data?.structures=structures
	traceEvent(settings.logFilter,"updateStructures>data.structures=${data.structures}",settings.trace,get_LOG_TRACE())        
}

void updateRooms(rooms) {
	traceEvent(settings.logFilter,"updateRooms>rooms from parent=$rooms",settings.trace,get_LOG_TRACE())        
	if (!data?.rooms) {
		data?.rooms=[]    
	}    
	data?.rooms=rooms
	traceEvent(settings.logFilter,"updateRooms>data.rooms=${data.rooms}",settings.trace,get_LOG_TRACE())        
}

// @getZonesList will post the list of Zone ids in the "zoneList" attribute

def getZonesList() {
	traceEvent(settings.logFilter,"getZonesList>data.zones=${data?.zones}",settings.trace,get_LOG_TRACE())        
	def results=""    
	def zoneList=""    
	if (!data?.zones) {
		sendEvent(name: "zoneList", value: "") // no values
		return results
	} 
	int max_ind= (data?.zones) ? (data.zones.size()-1): -1    
	for (i in 0..max_ind) {
		results=  results + ((i!=0)?',':' ') + data.zones[i].attributes.name     
		zoneList = zoneList + data.zones[i].id + ","         
	}    
	traceEvent(settings.logFilter,"getZonesList>zonesList=${results}, list of ids=$zoneList",settings.trace,get_LOG_TRACE())        
	sendEvent(name: "zoneList", value: zoneList) // save the zone id list
	return results    
}

// @id		Id of the room, by default the current one
//@forceUpdate	forceUpdate of the local cache, by default false
//@postData	flag used to post the corresponding room data as json, by default false

def getRoom(id,forceUpdate=false,postData=false) {
	if (!id) {
		id= device.currentValue("roomId")    
		if (!id) {
			traceEvent(settings.logFilter,"getRoom>id is null,exiting", settings.trace)
			return null        
		}        
	}    
	def results = data?.rooms.find{it.id==id}
	if ((!results) || (forceUpdate)) { // get the object from parent

		parent.getObject("rooms", id)    	
		parent.updateRooms(this)  
		results = data?.rooms.find{it.id==id}
	}
	if ((results) && (postData)) {

		def roomDataJson = new groovy.json.JsonBuilder(results)
		/*	
		traceEvent(settings.logFilter,"getRoom>roomDataJson=${roomDataJson}", settings.trace)
		*/
		def roomEvents = [
				roomData: "${roomDataJson.toString()}"
			]
		/*    
		traceEvent(settings.logFilter,"getRoom>roomEvents to be sent= ${roomEvents}", settings.trace)
		*/
		generateEvent(roomEvents)
    
	}    
	traceEvent(settings.logFilter,"getRoom>results =${results} for id=$id", settings.trace)
	return results    
}

// @id			Id of the zone [required]
// @forceUpdate		forceUpdate of the local cache, by default false
// @postData		flag used to post the corresponding zone data as json, by default false
def getZone(id,forceUpdate=false,postData=false) {

	if (!id) {
		traceEvent(settings.logFilter,"getZone>id is null,exiting", settings.trace)
		return null        
	}    
	def results = data?.zones.find{it.id==id}
	if ((!results) || (forceUpdate)) { // get the object from parent

		parent.getObject("zones", id)    	
		parent.updateZones(this)  
		results = data?.zones.find{it.id==id}
	}
	if ((results) && (postData)) {

		def zoneDataJson = new groovy.json.JsonBuilder(results)
		/*	
		traceEvent(settings.logFilter,"getZone>zoneDataJson=${zoneDataJson}", settings.trace)
		*/
		def zoneEvents = [
				zoneData: "${zoneDataJson.toString()}"
			]
		/*    
		traceEvent(settings.logFilter,"getZone>zoneEvents to be sent= ${zoneEvents}", settings.trace)
		*/
		generateEvent(zoneEvents)
    
	}    
	traceEvent(settings.logFilter,"getZone>zone =${results} for id=$id", settings.trace)
	return results    
}


private def getStructure(id) {
	def results = data?.structures.find{it.id==id}
	return results    
}


/**** COMMAND METHODS ****/

void on() {
	setLevel(100)
//	sendEvent(name: "switch", value: "on")
//	sendEvent(name: "level", value: 100)    
	traceEvent(settings.logFilter, "on>done for ventId=${ventId}", settings.trace)
}

void off() {
	setLevel(0)
//	sendEvent(name: "switch", value: "off")
//	sendEvent(name: "level", value: 0)    
	traceEvent(settings.logFilter, "off>done for ventId=${ventId}", settings.trace)
}

void clearObstruction() {
	on()
	off()
	on()
	if (getOnOff() != "on") {
		traceEvent(settings.logFilter, "clearObstruction>still obstructed", settings.trace, get_LOG_ERROR())
	}
}

void setLevel(value, rate=null) {
	traceEvent(settings.logFilter,"setting level: ${value}", settings.trace)
	def ventId = determine_vent_id("")
	setVent(ventId, ['percent-open':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setLevel>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
    sendEvent(name: "level", value: value)
    log.debug("level: ${level}")
    if(value == 0) {
    	sendEvent(name: "fanSpeed", value: "0")
    } else if(value < 26) {
    	sendEvent(name: "fanSpeed", value: "1")
    } else if(value < 51) {
    	sendEvent(name: "fanSpeed", value: "2")
    } else if(value < 76) {
    	sendEvent(name: "fanSpeed", value: "3")
    } else {
    	sendEvent(name: "fanSpeed", value: "4")
    }
        

	if (value > 0) {
		sendEvent(name: "switch", value: "on", descriptionText: "on by setting a level")
	} else {
		sendEvent(name: "switch", value: "off", descriptionText: "off by setting level to 0")
	}
	runIn((1*60), "refresh_vent")    
	traceEvent(settings.logFilter, "setLevel>done for ventId=${ventId}", settings.trace)

}

void setLightPattern(value) {
	traceEvent(settings.logFilter,"setting lightPattern: ${value}", settings.trace)
	def ventId = determine_vent_id("")
	setVent(ventId, ['lightstrip':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setLightPattern>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "lightPattern", value: value)
}

void ventTwentyFive() {
	setLevel(25)
}
void ventFifty() {
	setLevel(50)
}
void ventSeventyFive() {
	setLevel(75)
}
void ventHundred() {
	setLevel(100)
}
void ventLevelUp() {
	def currentLevel=device.latestValue("level")
	currentLevel= ((currentLevel!=null) && (currentLevel.isNumber()))? currentLevel.toInteger():0    
	int nextLevel =  currentLevel + 25 
	traceEvent(settings.logFilter, "Setting vent opening to: ${nextLevel}")
	if (nextLevel > 100) {
		nextLevel = 100
	}
	setLevel(nextLevel)
}

void ventLevelDown() {
	def currentLevel=device.latestValue("level")
	currentLevel= ((currentLevel!=null) && (currentLevel.isNumber()))? currentLevel.toInteger():100    
	int nextLevel =  currentLevel - 25 
	if (nextLevel < 0) {
		nextLevel = 0
	}
	traceEvent(settings.logFilter, "Setting vent opening to: ${nextLevel}", settings.trace)
	setLevel(nextLevel)
}

def getOnOff() {
	traceEvent(settings.logFilter, "getOnOff()", settings.trace, get_LOG_TRACE())

	return device.latestValue("switch")
}

def getPressure() {
	traceEvent(settings.logFilter, "getPressure()", settings.trace, get_LOG_TRACE())
	return device.latestValue("pressure")

}

def getLevel() {
	traceEvent(settings.logFilter, "getLevel()", settings.trace, get_LOG_TRACE())

	// disallow level updates while vent is obstructed
	if (device.currentValue("switch") == "obstructed") {
		traceEvent(settings.logFilter, "cannot get level status because ${ventId} is obstructed", settings.trace, get_LOG_ERROR())
		return null
	}
	return device.latestValue("level")
}

def getTemperature() {
	traceEvent(settings.logFilter, "getTemperature()", settings.trace, get_LOG_TRACE())
	return device.latestValue("temperature")
}

def getBattery() {
	traceEvent(settings.logFilter, "getBattery()", settings.trace, get_LOG_TRACE())

	return device.latestValue("battery")
}

// refresh() has a different polling interval as it is called by the UI (contrary to poll).
    
void refresh() {
	def ventId = determine_vent_id("")
	def poll_interval = 0.5 // set a 30 sec. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now()-(poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter, "refresh>ventId = ${ventId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
			"not refreshing data...", settings.trace)
		return
	}
	state.lastPollTimestamp = now()
	refresh_vent(ventId)

}

void poll() {
	String URI_ROOT = "${get_URI_ROOT()}"
	def ventId = determine_vent_id("")

	def poll_interval = 1 // set a minimum of 1 min. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now()-(poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter, "poll>ventId = ${ventId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
			"not refreshing data...", settings.trace)
		return
	}

	if (!isLoggedIn()) {
		login()    
	}    

	if (isTokenExpired()) {
		traceEvent(settings.logFilter, "poll>need to refresh tokens", settings.trace)

		if (!refresh_tokens()) {
			traceEvent(settings.logFilter, "poll>$exceptionCheck, not able to renew the refresh token", settings.trace, get_LOG_ERROR())
		} else {

			// Reset Exceptions counter as the refresh_tokens() call has been successful 
			state?.exceptionCount = 0
		}

	}

	traceEvent(settings.logFilter, "poll>about to call pollAsyncResponse for ventId = ${ventId}...", settings.trace)

	def params = [
		uri: "${URI_ROOT}/api/vents/${ventId}/sensor-readings?page[size]=1",
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//			'Content-Type': "text/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	asynchttp_v1.get('pollAsyncResponseVentReadings', params)
	params = [
		uri: "${URI_ROOT}/api/vents/${ventId}",
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//			'Content-Type': "text/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"

		]
	]

	asynchttp_v1.get('pollAsyncResponseVentInfo', params)
    
/*	Do not get the vent state anymore
	params = [
		uri: "${URI_ROOT}/api/vents/${ventId}/vent-states?page[size]=1",
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
			'Content-Type': "text/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"

		]
	]

	asynchttp_v1.get('pollAsyncResponseVentCurrentState', params)
*/    
	traceEvent(settings.logFilter, "poll>done for ventId = ${ventId}", settings.trace)
}


def pollAsyncResponseVentInfo(response, data) {
	def TOKEN_EXPIRED = 401
	String URI_ROOT = "${get_URI_ROOT()}"
    

	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter, "pollAsyncResponseVentInfo>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseVentInfo>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseVentInfo>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues = null    
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json
		} catch (e) {
			traceEvent(settings.logFilter, "pollAsyncResponseVentInfo>Flair-error parsing json from response: $e")
		}
		if (responseValues) {
			traceEvent(settings.logFilter,"pollAsyncResponseVentInfo>responseValues=$responseValues", settings.trace, get_LOG_TRACE())
			def ventId = responseValues.data?.id
			if (settings.trace) {
				def name = responseValues.data?.attributes?.name
				def level = responseValues.data?.attributes?."percent-open"
				traceEvent(settings.logFilter, "pollAsyncResponseVentInfo>ventId=${ventId},name=${name}, level=$level, poll done",settings.trace)
			}
			refresh_vent(ventId, responseValues)            
			state.lastPollTimestamp = now()
			retrieveDataForGraph()            
			state?.exceptionCount = 0
		}
	}

}

def pollAsyncResponseVentCurrentState(response, data) {	
	def TOKEN_EXPIRED=401
  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponseVentCurrentState>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()      
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseVentCurrentState>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseVentCurrentState>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues = null
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponseVentCurrentState>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
			traceEvent(settings.logFilter,"pollAsyncResponseVentCurrentState>responseValues=$responseValues", settings.trace, get_LOG_TRACE())
             
			def ventId = responseValues.data?.relationships?.vent?.data?.id       
			if (settings.trace) {
				def lightstrip = responseValues.data?.attributes?."lightstrip"
				def demoMode = responseValues.data?.attributes?."demo-mode"
				def level= responseValues.data?.attributes."percent-open"
				traceEvent(settings.logFilter,"pollAsyncResponseVentCurrentState>ventId=${ventId}," +
					"lightstrip=$lightstrip, demoMode=$demoMode, level=$level",settings.trace)     
			}
			state?.exceptionCount = 0
			refresh_ventStates(ventId,responseValues)            
			state.lastPollTimestamp = now()
		}                
	}        
}

// @ventId is single ventId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private void refresh_ventStates(ventId, asyncValues=null) {

	ventId=determine_vent_id(ventId)
	traceEvent(settings.logFilter,"refresh_ventStates>about to refresh $ventId based on $asyncValues", settings.trace)

	if (!data?.ventStates) {    
		data?.ventStates=[]
	}    

	if (!asyncValues) {
		getVentStates(ventId,"current-state")		       
    } else {
		if (asyncValues.data instanceof Collection) {                
			data?.ventStates=asyncValues.data
		} else {
			data?.ventStates[0]=asyncValues.data
		}                
	}
	traceEvent(settings.logFilter,"refresh_vent>data?.ventStates=${data?.ventStates[0]}", settings.trace)
	def dataEvents = [
		setBy:data?.ventStates[0]?.attributes?."set-by",
		read:data?.ventStates[0]?.attributes?.read,
		changeSet:data?.ventStates[0]?.attributes?."changeset",
		demoMode: data?.ventStates[0]?.attributes?."demo-mode",
		subGhzRadioTxPowerMw:data?.ventStates[0]?.attributes?."sub-ghz-radio-tx-power-mw",
		motorMaxRotateTimeMs:data?.ventStates[0]?.attributes?."motor-max-rotate-time-ms",
		updateInterval: data?.ventStates[0]?.attributes?."reporting-interval-ms"
	]
	traceEvent(settings.logFilter,"refresh_ventStates>dataEvents= ${dataEvents}", settings.trace)    
    
	generateEvent(dataEvents)
    
	traceEvent(settings.logFilter, "refresh_ventStates>done for ventId=${ventId}", settings.trace)
}

def pollAsyncResponseVentReadings(response, data) {
	def TOKEN_EXPIRED = 401
	String URI_ROOT = "${get_URI_ROOT()}"


	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter, "pollAsyncResponseVentReadings>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseVentReadings>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseVentReadings>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
		        
	} else {
		def responseValues = null
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json
		} catch (e) {
			traceEvent(settings.logFilter, "pollAsyncResponseVentReadings>Flair-error parsing json from response: $e")
		}
		if (responseValues) {
			traceEvent(settings.logFilter,"pollAsyncResponseVentReadings>responseValues=$responseValues", settings.trace, get_LOG_TRACE())
			def ventId = responseValues.data[0]?.relationships?.vent?.data?.id
			if (settings.trace) {
				def temperature = responseValues.data[0]?.attributes?."duct-temperature-c"
				def pressure =  responseValues.data[0]?.attributes?."duct-pressure"
				def rssi =  responseValues.data[0]?.attributes?.rssi
				def systemVoltage = responseValues.data[0]?.attributes?."system-voltage"
				def level=  responseValues.data[0]?.attributes?."percent-open"              
				traceEvent(settings.logFilter, "pollAsyncResponseVentReadings>ventId=${ventId}, temperature= $temperature, pressure=$pressure," +
					"rssi=$rssi, systemVoltage=$systemVoltage, level=$level", settings.trace)
			}
			refresh_ventReadings(ventId, responseValues) 
			state?.exceptionCount = 0
		}
	}
}

// @ventId is single ventId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private void refresh_ventReadings(ventId, asyncValues=null) {
	def MAX_INDICE=5
	ventId=determine_vent_id(ventId)
	traceEvent(settings.logFilter,"refresh_ventReadings>about to refresh $ventId based on $asyncValues", settings.trace)

	if (!data?.ventSensorReadings) {    
		data?.ventSensorReadings=[]
	}    

	if (!asyncValues) {
		getVentSensorReadings(ventId,"current-reading")		       
	} else {
/*    
		if (asyncValues.data instanceof Collection) {                
			data?.ventSensorReadings=asyncValues.data
		} else {
			data?.ventSensorReadings[0]=asyncValues.data
		}                
*/
		int max_ind= (asyncValues.data) ? (asyncValues.data.size()-1) :-1               
		max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
		for (i in 0..max_ind) {
			data?.ventSensorReadings[i] =asyncValues.data[i]                    
		}            

	}
	traceEvent(settings.logFilter,"refresh_ventReadings>data?.ventSensorReadings=${data?.ventSensorReadings[0]}", settings.trace)
	def dataEvents = [
		temperature: data?.ventSensorReadings[0]?.attributes?."duct-temperature-c",
		temperatureDisplay: data?.ventSensorReadings[0]?.attributes?."duct-temperature-c",
		level: data?.ventSensorReadings[0]?.attributes?."percent-open",
		pressure: data?.ventSensorReadings[0]?.attributes?."duct-pressure", 
		rssi: data?.ventSensorReadings[0]?.attributes.rssi,
		battery:getBatteryUsage(),        
		systemVoltage: data?.ventSensorReadings[0]?.attributes?."system-voltage",       
		motorRunTime: data?.ventSensorReadings[0]?.attributes?."motor-run-time",                    
		firmwareS: data?.ventSensorReadings[0]?.attributes?."firmware-version-s"                    
	]
	if (dataEvents.level && dataEvents.level>5 ) { // to correct some state issues (open/close) with level in readings
		dataEvents.switch="on"		    
	} else {
		dataEvents.switch="off"		    
	}    
    
	traceEvent(settings.logFilter,"refresh_ventReadings>dataEvents= ${dataEvents}", settings.trace)    
    
	generateEvent(dataEvents)
    
	traceEvent(settings.logFilter, "refresh_ventReadings>done for ventId=${ventId}", settings.trace)
}



// MISC METHODS

private def getCustomImagePath() {
	return "https://raw.githubusercontent.com/yracine/flairDevices/master/images/"
}

private def getStandardImagePath() {
	return "http://cdn.device-icons.smartthings.com"
}
private def get_APPLICATION_VERSION() {
	return "application/vnd.api+json; co.flair.api.version=1"
}

private def cToF(temp) {
	return (temp * 1.8 + 32)
}
private def fToC(temp) {
	return (temp-32).toFloat() / 1.8
}


private def refreshLocalAuthToken() {
	boolean refresh_success = false
	def REFRESH_SUCCESS_CODE = 200

	traceEvent(settings.logFilter,"refreshLocalAuthToken>about to refresh auth token", settings.trace)

	def stcid = get_appKey()
	def privateKey=get_privateKey()    

	def refreshParams = [
			method: 'POST',
			uri   : "${get_URI_ROOT()}",
			path  : "/oauth/token",
			query : [grant_type: 'refresh_token', refresh_token: "${data?.auth?.refresh_token}", client_id: stcid, client_secret: privateKey ]
	]


	traceEvent(settings.logFilter, "refreshLocalAuthToken>refreshParams=$refreshParams", settings.trace)
	def jsonMap
	httpPost(refreshParams) {resp->
			if (resp.status == REFRESH_SUCCESS_CODE) {
				traceEvent(settings.logFilter,"refreshLocalAuthToken>token refresh done resp = ${resp.data}", settings.trace)
				jsonMap = resp.data

				if (resp.data) {

					data.auth.access_token = resp.data.access_token
					data.auth.refresh_token = resp.data.refresh_token
					data.auth.expires_in = resp.data.expires_in
					data.auth.token_type = resp.data.token_type
					data.auth.scope = resp?.data?.scope
					def authexptime = new Date((now() + (resp?.data?.expires_in * 1000))).getTime()
					data.auth.authexptime = authexptime
					traceEvent(settings.logFilter, "refreshLocalAuthToken>new authexptime = ${authexptime}", settings.trace)
					traceEvent(settings.logFilter, "refreshLocalAuthToken>new access token = ${data.auth.access_token}", settings.trace)
					traceEvent(settings.logFilter, "refreshLocalAuthToken>new refresh token = ${data.auth.refresh_token}", settings.trace)
					refresh_success = true
				} /* end if resp.data */
			} else {
				traceEvent(settings.logFilter, "refreshLocalAuthToken>refresh failed ${resp.status} : ${resp.status.code}", true, get_LOG_ERROR())
			} /* end if http status == 200 */
	} /* end if http post */

	return refresh_success
}

private def refresh_tokens() {
	def buffer_time_refresh=2  // set a 2 min. buffer time before re-attempting refresh 
	def time_check_for_refresh = (now() - (buffer_time_refresh * 60 * 1000))
    
	if (isTokenExpired()) {    
 		traceEvent(settings.logFilter,"refresh_tokens>trying to get tokens from parent first to avoid repetitive calls to parent.refreshParentTokens()")
 		parent.refreshThisChildAuthTokens(this) 
		if (!isTokenExpired()) {    
			state?.lastRefreshTimestamp= now()
			return true
		}    
	}    
	if ((state?.lastRefreshTimestamp) && (state?.lastRefreshTimestamp > time_check_for_refresh)) {
		traceEvent(settings.logFilter,"refresh_tokens>time_check_for_refresh (${time_check_for_refresh} < state.lastRefreshTimestamp (${state?.lastRefreshTimestamp}), not refreshing tokens...",
			settings.trace)
		return false
	}
//	if device created by initialSetup (Service Manager)

	if (data?.auth?.ventId) {
		if (!parent.refreshParentTokens()) {
			refreshLocalAuthToken() // If not successful, refresh the device tokens locally
			if (!isTokenExpired()) {    
				parent.setParentAuthTokens(data.auth)
			}                
			traceEvent(settings.logFilter,"refresh_tokens>warning: called refreshLocalAuthToken() after parent call failure", settings.trace, get_LOG_WARN())
		}            
	}  else {  /* need to login */
		login()
	}    
	if (isTokenExpired()) {
		traceEvent(settings.logFilter,"refresh_tokens>local tokens still expired after call to refreshParentTokens,trying to get tokens from parent again",settings.trace, get_LOG_WARN())
		parent.refreshThisChildAuthTokens(this) 
		state?.lastRefreshTimestamp= now()
	}  	
	if (!isTokenExpired()) {    
		state?.lastRefreshTimestamp= now()
		return true
	} 
	traceEvent(settings.logFilter,"refresh_tokens>warning: local tokens still expired after refresh_tokens() call", true, get_LOG_WARN())
	return false
}

private def refreshChildTokens(auth) {
	traceEvent(settings.logFilter,"begin new token auth= $auth.access_token, authexptime=$auth.authexptime", settings.trace)
	traceEvent(settings.logFilter,"refreshChildTokens>old data.auth= ${data.auth}", settings.trace)
	if ((!data?.auth?.authexptime) || ((data?.auth?.authexptime) && (auth.authexptime > data?.auth?.authexptime))) {    
		if (!data?.auth?.authexptime) { // if info lost
			def varSettings=[:]
			varSettings=settings        
			data?.auth=varSettings    
			data?.auth?.appKey=auth?.clientId                
			if (!settings.appKey) { // if appKey is lost
				settings.appKey=auth?.clientId
			}            
			data?.auth?.privateKey=auth?.privateKey                
			if (!settings.privateKey) { // if appKey is lost
				settings.privateKey=auth?.privateKey
			}            
		}            
		save_data_auth(auth)        
		traceEvent(settings.logFilter, "refreshChildTokens>end newly refreshed data.auth=$data.auth", settings.trace, get_LOG_INFO())
	}
	if (!isTokenExpired()) {
		return true   
	}       
	traceEvent(settings.logFilter,"refreshChildTokens>warning:tokens still expired",settings.trace)
	return false    
}
void save_data_auth(auth) {
	data?.auth?.access_token = auth.access_token
	data?.auth?.refresh_token = auth.refresh_token
	data?.auth?.token_type=auth.token_type
	data?.auth?.authexptime=auth.authexptime
	traceEvent(settings.logFilter,"save_data_auth>new data.auth=${data.auth}", settings.trace,get_LOG_INFO())
}

private void login() {
	traceEvent(settings.logFilter,"login>begin", settings.trace,get_LOG_TRACE())
	if (!isLoggedIn()) {
		traceEvent(settings.logFilter,"login> no auth_token...,about to get it from the parent", true, get_LOG_ERROR())
 		parent.refreshThisChildAuthTokens(this) 
		if (!isTokenExpired()) {    
			state?.lastRefreshTimestamp= now()
			return 
		}    
	}
}



private def isLoggedIn() {
	if (!data?.auth?.access_token) {
		traceEvent(settings.logFilter,"isLoggedIn> no data auth", settings.trace,get_LOG_TRACE())
		return false
	} 
	return true
}

private def isTokenExpired() {
	def buffer_time_expiration = 5 // set a 5 min. buffer time 
	def time_check_for_exp = now() + (buffer_time_expiration * 60 * 1000)
	double authExpTimeInMin = ((data.auth.authexptime-time_check_for_exp) / 60000).toDouble().round(0)
	traceEvent(settings.logFilter,"isTokenExpired>expiresIn timestamp: ${data?.auth?.authexptime} > timestamp check for exp: ${time_check_for_exp}?", settings.trace)
	traceEvent(settings.logFilter, "isTokenExpired>expires in ${authExpTimeInMin.intValue()} minutes", settings.trace, get_LOG_INFO())
	if (authExpTimeInMin < 0) {
		traceEvent(settings.logFilter, "isTokenExpired>auth token buffer time  expired (${buffer_time_expiration} min.), countdown is ${authExpTimeInMin.intValue()} minutes, need to refresh tokens now!",
			settings.trace, get_LOG_WARN())
	}
	if (authExpTimeInMin < (0-buffer_time_expiration)) {
		traceEvent(settings.logFilter, "isTokenExpired>refreshing tokens is more at risk (${authExpTimeInMin} min.),exception count may increase if tokens not refreshed!",
			settings.trace, get_LOG_WARN())
	}
	if (data.auth.authexptime > time_check_for_exp) {
		traceEvent(settings.logFilter,"isTokenExpired> not expired", settings.trace, get_LOG_INFO())
		return false
	}
	traceEvent(settings.logFilter,"isTokenExpired>expired", settings.trace, get_LOG_INFO())
	return true
}

// Determine id from settings or initalSetup
private def determine_vent_id(vent_id) {
	def ventId=device.currentValue("ventId")

	if ((vent_id != null) && (vent_id != "")) {
		ventId = vent_id
	} else if ((settings.ventId != null) && (settings.ventId != "")) {
		ventId = settings.ventId.trim()
		traceEvent(settings.logFilter,"determine_vent_id> ventId from settings = ${settings.ventId}", settings.trace)
	} else if (data?.auth?.ventId) {
		settings?.ventId = data?.auth?.ventId
		ventId=data?.auth?.ventId
		traceEvent(settings.logFilter,"determine_vent_id> ventId from data.auth = ${data?.auth?.ventId}", settings.trace)
	} else if ((ventId !=null) && (ventId != "")) {
		settings.ventId = ventId
		traceEvent(settings.logFilter,"determine_vent_id> ventId from device = $ventId", settings.trace)
	}
        
	if ((vent_id != "") && (vent_id != ventId) && (ventId)) {
		sendEvent(name: "ventId", displayed: (settings.trace?: false), value: ventId)
	}
	return ventId
}

// Determine id from settings or initalSetup
private def determine_structure_id(structure_id) {
	def structureId = device.currentValue("structureId") 

	if ((structure_id != null) && (structure_id != "")) {
		structureId = structure_id
	} else if ((settings.structureId != null) && (settings.structureId != "")) {
		structureId = settings.structureId.trim()
		traceEvent(settings.logFilter,"determine_structure_id>structureId from settings= ${settings.structureId}", settings.trace)
	} else if ((settings.structureId == null) || (settings.structureId == "")) {
		settings?.structureId = structureId
		traceEvent(settings.logFilter,"determine_structure_id>structureId from device= ${structureId}", settings.trace)
	}
	if ((structure_id != "") && (structure_id != structureId)) {
		sendEvent(name: "structureId", displayed: (settings.trace?: false), value: structureId)
	}
	return structureId
}

// @Get the appKey for authentication
private def get_appKey() {

	if (settings.appKey) {	
		return settings.appKey
	} else {
		return data?.auth?.appKey
	}    
}    

// @Get the privateKey for authentication
private def get_privateKey() {
	
	if (settings.privateKey) {	
		return settings.privateKey
	} else {
		return data?.auth?.privateKey
	}    
}    
      



private def get_URI_ROOT() {
	return "https://api.flair.co"
}

private def get_API_VERSION() {
	return "1"
}


// @Called by My FlairServiceMgr for initial creation of a child Device
void initialSetup(device_client_id,device_private_key,access_token,refresh_token,token_type,token_authexptime,device_structure_id,device_vent_id) {
	def varSettings=[:]
	settings?.trace=true
	settings?.logFilter=5
	if (settings.trace) {
		log.debug "initialSetup>begin"
		log.debug "initialSetup> device_vent_Id = ${device_vent_id}"
		log.debug "initialSetup> device_client_id = ${device_client_id}"
		log.debug "initialSetup> device_private_key = ${device_private_key}"
		log.debug "initialSetup> token_type = ${token_type}"
		log.debug "initialSetup> token_authexptime = ${token_authexptime}"
		log.debug "initialSetup> device_structure_Id = ${device_structure_id}"
	}	

	settings?.appKey= device_client_id
	settings?.privateKey= device_private_key
	settings?.structureId = device_structure_id
	settings?.ventId = device_vent_id
	
	varSettings=settings	
	data?.auth=[:]
	data?.auth=varSettings
    
	data?.auth?.access_token= access_token
	data?.auth?.refresh_token = refresh_token
	data?.auth?.token_type = token_type
	data?.auth?.authexptime= token_authexptime
	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()
    
	sendEvent(name: "ventId", displayed: (settings.trace?:false),value: device_vent_id)    
	sendEvent(name: "structureId", displayed: (settings.trace?: false), value: device_structure_id)
    
    //eeddir
	sendEvent(name: "flairPressure", value: "n/a")    
    sendEvent(name: "flairRoom", value: "n/a")    
    sendEvent(name: "flairZone", value: "n/a")    
    sendEvent(name: "flairStatus", value: "n/a")
    sendEvent(name: "flairRSSI", value: "n/a")

	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()
	runIn(1*60, "refresh")
	//	refresh_vent(device_vent_id)
	if (settings.trace) {    
		log.debug "initialSetup> settings = $settings"
//		log.debug "initialSetup> state = ${state}"
//		log.debug "initialSetup> data.auth after refresh = ${data.auth}"
		log.debug "initialSetup> end"
	}	
}

def toJson(Map m) {
	return new org.codehaus.groovy.grails.web.json.JSONObject(m).toString()
}

private int get_LOG_ERROR() {return 1}
private int get_LOG_WARN()  {return 2}
private int get_LOG_INFO()  {return 3}
private int get_LOG_DEBUG() {return 4}
private int get_LOG_TRACE() {return 5}


def traceEvent(logFilter,message, displayEvent=false, traceLevel=4, sendMessage=true) {
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


void produceSummaryReport(pastDaysCount) {
	traceEvent(settings.logFilter,"produceSummaryReport>begin",settings.trace, get_LOG_TRACE())
	def avg_room_temp,avg_room_desired_temp, avg_room_setpoint, min_room_setpoint=200, max_room_setpoint=0, avg_vent_temp, max_vent_temp
	def vent_level_count, min_open_level=100, max_open_level=0    
	double avg_vent_level    
	boolean found_values=false
	Date todayDate = new Date()
	Date startOfPeriod = todayDate - pastDaysCount
	long min_room_timestamp,max_room_timestamp, min_level_timestamp, max_level_timestamp

	def rmSetpointData = device.statesSince("rmSetpoint", startOfPeriod, [max:200])
	def rmTemperatureData = device.statesSince("rmCurrentTemperature", startOfPeriod, [max:200])
	def rmUserDesiredTempData = device.statesSince("rmUserDesiredTemperature", startOfPeriod, [max:200])
	def temperatureData = device.statesSince("temperature", startOfPeriod, [max:200])
	def openLevelData = device.statesSince("level", startOfPeriod, [max:200])

	if (rmTemperatureData) {
		avg_room_temp= (rmTemperatureData.sum{it.floatValue.toFloat()}/(rmTemperatureData.size())).toFloat().round(1)
		found_values=true        
	}        
	if (rmUserDesiredTempData) {
		avg_room_desired_temp= (rmUserDesiredTempData.sum{it.floatValue.toFloat()}/ (rmUserDesiredTempData.size())).toFloat().round(1)
		found_values=true        
	}
	if (rmSetpointData) {    
		avg_room_setpoint =  (rmSetpointData.sum{it.floatValue.toFloat()}/ (rmSetpointData.size())).toFloat().round(1)
		        
		int maxInd=rmSetpointData?.size()-1    
		for (int i=maxInd; (i>=0);i--) {
			if (rmSetpointData[i]?.floatValue.toFloat() < min_room_setpoint.toFloat()) {
				min_room_setpoint=rmSetpointData[i]?.floatValue 
				min_room_timestamp=rmSetpointData[i]?.date.getTime()                
			}
			if (rmSetpointData[i]?.floatValue.toFloat() > max_room_setpoint.toFloat()) {
				max_room_setpoint=rmSetpointData[i]?.floatValue  
				max_room_timestamp=rmSetpointData[i]?.date.getTime()                
			}
		}            
		found_values=true        
	} 
    
	if (openLevelData) {    
		avg_vent_level = (openLevelData.sum{((it.value)?it.value:0).toInteger()}/ (openLevelData.size())).toDouble().round()
		vent_level_count = openLevelData.size()
		found_values=true  
		int maxInd=openLevelData?.size()-1    
		for (int i=maxInd; (i>=0);i--) {
			if ((openLevelData[i]?.value) && (openLevelData[i]?.value < min_open_level)) {
				min_open_level=openLevelData[i]?.value  
				min_level_timestamp=openLevelData[i]?.date.getTime()                
			}
			if ((openLevelData[i]?.value) && (openLevelData[i]?.value > max_open_level)) {
				max_open_level=openLevelData[i]?.value  
				max_level_timestamp=openLevelData[i]?.date.getTime()                
			}
		}            
        
	} 
    
	if (temperatureData) {    
		avg_vent_temp= (temperatureData.sum{it.floatValue.toFloat()}/ (temperatureData.size())).toFloat().round(1)
		found_values=true        
	}        
	if (!found_values) {
		traceEvent(settings.logFilter,"produceSummaryReport>found no values for report,exiting",settings.trace)
		sendEvent(name: "summaryReport", value: "")
		return        
	}    
	String scale=getTemperatureScale(), unitScale='Farenheit', timePeriod="In the past ${pastDaysCount} days"
    
	if (scale=='C') { 
		unitScale='Celsius'    
	}   
	if (pastDaysCount <2) {
		timePeriod="In the past day"    
	}    

	String roomName =device.currentValue("roomName")
	String summary_report = "${timePeriod}" 
	if (roomName) {
		summary_report= summary_report + ", in the room ${roomName} where the vent ${device.displayName} is located"
	}
    
	if (avg_room_desired_temp) {
		summary_report= summary_report + ",the room's average desired temperature was ${avg_room_desired_temp} degrees ${unitScale}"
    
	}
	    
	if (avg_room_temp) {
		summary_report= summary_report + ",the room's average temperature was ${avg_room_temp.toString()} degrees ${unitScale}"
	}
    
	if (avg_room_setpoint) {
		summary_report= summary_report + ",the room's setpoint was ${avg_room_setpoint.toString()} degrees in average." 
	}
	if (min_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(min_room_timestamp)					    
		summary_report= summary_report + "The room's minimum setpoint was ${min_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
	if (max_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(max_room_timestamp)					    
		summary_report= summary_report + ",and the room's maximum setpoint was ${max_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
    
	if (avg_vent_temp) {
		summary_report= summary_report + ".The vent's average temperature collected was ${avg_vent_temp.toString()} degrees ${unitScale}"
	}
    
	if (avg_vent_level) {
		summary_report= summary_report + ", the vent's average level was ${avg_vent_level.intValue()}% and there were ${vent_level_count} level changes"      
	}
	if ((min_open_level!=null) && (min_level_timestamp != max_level_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(min_level_timestamp)					    
		summary_report= summary_report + ".The vent's minimum level was ${min_open_level.toString()}% on ${timeInLocalTime.substring(0,16)}" 
	}
	if ((max_open_level) && (min_level_timestamp != max_level_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(max_level_timestamp)					    
		summary_report= summary_report + ".The vent's maximum level was ${max_open_level.toString()}% on ${timeInLocalTime.substring(0,16)}" 
	}

	sendEvent(name: "summaryReport", value: summary_report, isStateChange: true)
    
	traceEvent(settings.logFilter,"produceSummaryReport>end",settings.trace, get_LOG_TRACE())

}


def retrieveDataForGraph() {
	def scale = state?.scale
	Date todayDate = new Date()
	def todayDay = new Date().format("dd",location.timeZone)
	String todayInLocalTime = todayDate.format("yyyy-MM-dd", location.timeZone)
	String timezone = new Date().format("zzz", location.timeZone)
	String todayAtMidnight = todayInLocalTime + " 00:00 " + timezone
	Date startOfToday = formatDate(todayAtMidnight)
	Date yesterday = startOfToday -1    
	Date startOfWeek = startOfToday -7
	def MIN_DEVIATION_TEMP=(scale=='C'?5:10)   
	def MIN_DEVIATION_LEVEL=50    
	def currentOpenLevel    
    
	traceEvent(settings.logFilter,"retrieveDataForGraph>today at Midnight in local time= ${todayAtMidnight}",settings.trace)
	def rmTemperatureTable = []
	def rmSetpointTable = []
	def temperatureTable = []
	def openLevelTable=[]    
	int maxInd    
	def previousValue=null
    
	def rmSetpointData = device.statesSince("rmSetpoint", startOfWeek, [max:20])
	def rmTemperatureData = device.statesSince("rmCurrentTemperature", startOfWeek, [max:50])
	def temperatureData = device.statesSince("temperature", startOfWeek, [max:200])
	def openLevelData = device.statesSince("level", startOfWeek, [max:300])
    
	if (rmTemperatureData) {    
		maxInd=rmTemperatureData?.size()-1 
		for (int i=maxInd; (i>=0);i--) {
			// filter some values        
			if (i !=maxInd) previousValue = rmTemperatureData[i+1]?.floatValue
			if ((i==0) || (i==maxInd) || ((rmTemperatureData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (rmTemperatureData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
				rmTemperatureTable.add([rmTemperatureData[i].date.getTime(),rmTemperatureData[i].floatValue])
			}
		} /* end for */            
	}
	if (temperatureData) {    
		previousValue=null
		maxInd=temperatureData?.size()-1    
		for (int i=maxInd; (i>=0);i--) {
			// filter some values        
			if (i !=maxInd) previousValue = temperatureData[i+1]?.floatValue
			if ((i==0) || (i==maxInd) || ((temperatureData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (temperatureData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
				temperatureTable.add([temperatureData[i].date.getTime(),temperatureData[i].floatValue])
			}
		} /* end for */            
	}
	if (openLevelData) {    
		previousValue=null
		maxInd=openLevelData?.size()-1    
		for (int i=maxInd; (i>=0);i--) {
			// filter some values        
			if ((i !=maxInd) && (openLevelData[i+1])) previousValue = openLevelData[i+1]?.value.toInteger()
			if ((i==0) || (i==maxInd) || ((openLevelData[i]?.value) && (openLevelData[i]?.value.toInteger() <= (previousValue - MIN_DEVIATION_LEVEL)) || (openLevelData[i]?.value.toInteger() >= (previousValue + MIN_DEVIATION_LEVEL)))) {
				openLevelTable.add([openLevelData[i].date.getTime(),openLevelData[i].value])
			}
		} /* end for */            
        
	}
    
	if (rmSetpointData) {    
		previousValue=null
		maxInd=rmSetpointData?.size()-1    
		for (int i=maxInd; (i>=0);i--) {
			// filter some values        
			if (i !=maxInd) previousValue = rmSetpointData[i+1]?.floatValue
			if ((i==0) || (i==maxInd) || ((rmSetpointData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (rmSetpointData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
				rmSetpointTable.add([rmSetpointData[i].date.getTime(),rmSetpointData[i].floatValue])
			}
		}            
	}
    
	if (rmTemperatureTable == []) { // if temperature has not changed for a week
		def currentRmTemperature=device.currentValue("rmCurrentTemperature")
		if (currentRmTemperature) {
			rmTemperatureTable.add([startOfWeek.getTime(),currentRmTemperature])		        
			rmTemperatureTable.add([todayDate.getTime(),currentRmTemperature])		        
		}    
	} else {
		def currentRmTemperature=device.currentValue("rmCurrentTemperature")
		if (currentRmTemperature) {
			rmTemperatureTable.add([todayDate.getTime(),currentRmTemperature])		        
		}    
	}    

	if (rmSetpointTable == []) { // if temperature has not changed for a week
		def currentRmSetpoint=device.currentValue("rmSetpoint")
		if (currentRmSetpoint) {
			rmSetpointTable.add([startOfWeek.getTime(),currentRmSetpoint])		        
			rmSetpointTable.add([todayDate.getTime(),currentRmSetpoint])		        
		}    
	} else {
		def currentRmSetpoint=device.currentValue("rmSetpoint")
		if (currentRmSetpoint) {
			rmSetpointTable.add([todayDate.getTime(),currentRmSetpoint])		        
		}    
	}    

	if (temperatureTable == []) { // if Temperature has not changed for a week
		def currentTemperature=device.currentValue("temperature")
		if (currentTemperature) {
			temperatureTable.add([startOfWeek.getTime(),currentTemperature])		        
			temperatureTable.add([todayDate.getTime(),currentTemperature])		        
		}    
	} else {
		def currentTemperature=device.currentValue("temperature")
		if (currentTemperature) {
			temperatureTable.add([todayDate.getTime(),currentTemperature])		        
		}    
	}    
	if (openLevelTable == []) { // if level has not changed for a week
		try {    
			currentOpenLevel=device.latestValue("level")
		} catch (any) {        
			traceEvent(settings.logFilter,"retrieveDataForGraph>not able to get current level",settings.trace, get_LOG_TRACE())  
		}        
		if (currentOpenLevel !=null) {        
			if (currentOpenLevel.isNumber()) {        
				openLevelTable.add([startOfWeek.getTime(),currentOpenLevel])		        
				openLevelTable.add([todayDate.getTime(),currentOpenLevel])
			}            
		}            
	} else {
		try {    
			currentOpenLevel=device.latestValue("level")
		} catch (any) {        
			traceEvent(settings.logFilter,"retrieveDataForGraph>not able to get current level",settings.trace, get_LOG_TRACE())  
		}        
		if (currentOpenLevel !=null) { 
			if (currentOpenLevel.isNumber()) {        
				openLevelTable.add([todayDate.getTime(),currentOpenLevel])		        
			}            
		}            
	}    
	state?.rmTemperatureTable = rmTemperatureTable
	state?.temperatureTable = temperatureTable
	state?.openLevelTable = openLevelTable
	state?.rmSetpointTable = rmSetpointTable
	traceEvent(settings.logFilter,"retrieveDataForGraph>rmSetpointTable (size=${rmSetpointTable.size()}) =${rmSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>rmTemperatureTable (size=${state?.rmTemperatureTable.size()}) =${state?.rmTemperatureTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>temperatureTable (size=${state?.temperatureTable.size()}) =${state?.temperatureTable}",settings.trace,get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>openLevelTable (size=${state?.openLevelTable.size()}) =${state?.openLevelTable}",settings.trace, get_LOG_TRACE())  
}

def getStartTime() {
	long startTime = new Date().getTime().toLong()
	if ((state?.temperatureTable) && (state?.temperatureTable.size() > 0)) {
		startTime = state?.temperatureTable.min{it[0]}[0].toLong()
	}
	if ((state?.openLevelTable) && (state?.openLevelTable.size() > 0)) {
		startTime = Math.min(startTime, state.openLevelTable.min{it[0]}[0].toLong())
	}
	return startTime
}



String getDataString(Integer seriesIndex) {
	def dataString = ""
	def dataTable = []
	def dataArray    
	switch (seriesIndex) {
		case 1:
			dataTable = state?.temperatureTable
			break
		case 2:
			dataTable = state?.rmSetpointTable
			break
		case 3:
			dataTable = state?.rmTemperatureTable
			break
		case 4:
			dataTable = state?.openLevelTable
			break
	}
	dataTable.each() {
		dataString += "[new Date(${it[0]}),"
		if (seriesIndex==1) {
			dataString += "${it[1]},null,null,null],"
		}
		if (seriesIndex==2) {
			dataString += "null,${it[1]},null,null],"
		}
		if (seriesIndex==3) {
			dataString += "null,null,${it[1]},null],"
		}
		if (seriesIndex==4) {
			dataString += "null,null,null,${it[1]}],"
		}
        
	}
	        
	if (dataString == "") {
		def todayDate = new Date()
		if (seriesIndex==1) {
			dataString = "[new Date(todayDate.getTime()),0,null,null,null],"
		}
		if (seriesIndex==2) {
			dataString = "[new Date(todayDate.getTime()),null,0,null,null],"
		}
		if (seriesIndex==3) {
			dataString = "[new Date(todayDate.getTime()),null,null,0,null],"
		}
		if (seriesIndex==4) {
			dataString = "[new Date(todayDate.getTime()),null,null,null,0],"
		}
	}
//	traceEvent(settings.logFilter,"seriesIndex= $seriesIndex, dataString=$dataString",settings.trace)
    
	return dataString
}

private def formatTimeInTimezone(dateTime, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	String dateInLocalTime =new Date(dateTime).format("yyyy-MM-dd HH:mm:ss zzz", myTimezone)
//	log.debug("formatDateInLocalTime>dateInLocalTime=$dateInLocalTime, timezone=$timezone")    
	return dateInLocalTime
}

private String formatDateInLocalTime(dateInString, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	if ((dateInString==null) || (dateInString.trim()=="")) {
		return (new Date().format("yyyy-MM-dd HH:mm:ss", myTimezone))
	}    
	SimpleDateFormat ISODateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date ISODate = ISODateFormat.parse(dateInString.substring(0,19) + 'Z')
	String dateInLocalTime =new Date(ISODate.getTime()).format("yyyy-MM-dd HH:mm:ss zzz", myTimezone)
//	log.debug("formatDateInLocalTime>dateInString=$dateInString, dateInLocalTime=$dateInLocalTime")    
	return dateInLocalTime
}


private def ISODateFormat(dateString) {
 	SimpleDateFormat ISO8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date aDate = ISO8601format.parse(dateString.substring(0,19) + 'Z')
	return aDate
}

private def formatDate(dateString) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm zzz")
	Date aDate = sdf.parse(dateString)
	return aDate
}

def getGraphHTML() {
  
	String dataRows = "${getDataString(1)}" + "${getDataString(2)}" + "${getDataString(3)}" + "${getDataString(4)}"
	Date maxDateTime= new Date()
	Date minDateTime= new Date(getStartTime())
	def minDateStr= "new Date(" +  minDateTime.getTime() + ")"
	def maxDateStr= "new Date(" +  maxDateTime.getTime() + ")"
//	traceEvent(settings.logFilter,"getGraphHTML>dataRows=$dataRows",settings.trace)

	Date yesterday=maxDateTime -1
	def yesterdayStr= "new Date(" +  yesterday.getTime() + ")"
//	log.debug("minDateStr= $minDateStr")
//	log.debug("maxDateStr= $maxDateStr")
//	log.debug ("yesterdayStr=$yesterdayStr")   
	def html = """
		<!DOCTYPE html>
			<html>
				<head>
					<meta http-equiv="cache-control" content="max-age=0"/>
					<meta http-equiv="cache-control" content="no-cache"/>
					<meta http-equiv="expires" content="0"/>
					<meta http-equiv="pragma" content="no-cache"/>
					<meta name="viewport" content="width = device-width, user-scalable=no, initial-scale=1.0">
					<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
					<script type="text/javascript">
   					google.charts.load('current', {'packages':['corechart']});
					google.charts.setOnLoadCallback(drawChart);
                    
					function drawChart() {
						var data = new google.visualization.DataTable();
						data.addColumn('datetime', 'Time of Day')
						data.addColumn('number', 'VentTmp');
						data.addColumn('number', 'RmSetPnt');
						data.addColumn('number', 'Ambient');
						data.addColumn('number', 'Level');
						data.addRows([
							${dataRows}
						]);
						var options = {
							hAxis: {
								viewWindow: {
									min: ${minDateStr},
									max: ${maxDateStr}
								},
  								gridlines: {
									count: -1,
									units: {
										days: {format: ['MMM dd']},
										hours: {format: ['HH:mm', 'ha']}
										}
								},
								minorGridlines: {
									units: {
										hours: {format: ['hh:mm:ss a','ha']},
										minutes: {format: ['HH:mm a Z',':mm']}
									}
								}
							},
							series: {
								0: {targetAxisIndex: 0, color: '#FF0000'},
								1: {targetAxisIndex: 0, color: '#d04e00'},
								2: {targetAxisIndex: 0, color: '#f1d801'},
								3: {targetAxisIndex: 1, color: '#44b621',lineWidth: 1}
							},
							vAxes: {
								0: {
									title: 'Temperature',
									format: 'decimal',
									textStyle: {color: '#FF0000'},
									titleTextStyle: {color: '#FF0000'}
								},
								1: {
									title: 'Level(%)',
									format: 'decimal',
									minValue: 0,                                        
									maxValue: 100,                                        
									textStyle: {color: '#44b621'},
									titleTextStyle: {color: '#44b621'}
								}
							},
							legend: {
								position: 'bottom',
								textStyle: {color: '#000000'}
							},
							chartArea: {
								left: '12%',
								right: '15%',
								top: '3%',
								bottom: '20%',
								height: '85%',
								width: '100%'
							}
						};
						var chart = new google.visualization.LineChart(document.getElementById('chart_div'));

  						chart.draw(data, options);
						var button = document.getElementById('change');
						var isChanged = false;

						button.onclick = function () {
							if (!isChanged) {
								options.hAxis.viewWindow.min = ${minDateStr};
								options.hAxis.viewWindow.max = ${maxDateStr};
								isChanged = true;
							} else {
								options.hAxis.viewWindow.min = ${yesterdayStr};
								options.hAxis.viewWindow.max =  ${maxDateStr};
								isChanged = false;
							}
							chart.draw(data, options);
						};
					}                        
 			</script>
			</head>
	  		<h3 style="font-size: 20px; font-weight: bold; text-align: center; background: #ffffff; color: #44b621;">TempVsOpenLevel</h3>
			<body>
				<button id="change">Change View Window</button>
				<div id="chart_div"></div>
			</body>
		</html>
	"""
	render contentType: "text/html", data: html, status: 200
}