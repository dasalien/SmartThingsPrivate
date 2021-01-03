/***
 *  My Next Sensor
 *  Copyright 2020 Yves Racine
 *  LinkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *  Version 2.1.7
 *  Refer to readme file for installation instructions.
 *
 *  Developer retains all right, title, copyright, and interest, including all copyright, patent rights,
 *  trade secret in the Background technology. May be subject to consulting fees under an Agreement 
 *  between the Developer and three Customer. Developer grants a non exclusive perpetual license to use
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
import groovy.json.*
import groovy.transform.Field

//include 'asynchttp_v1'

// for the UI

preferences {

//	Preferences are no longer required when created with the Service Manager (MyNextServiceMgr).

	input("sensorId", "text", title: "internal Id", description:
		"internal sensorId\n(not needed when using MyNextServiceMgr, leave it blank)")
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces) or leave it empty (no tracing)")
	input("logFilter", "number",title: "(1=ERROR only,2=<1+WARNING>,3=<2+INFO>,4=<3+DEBUG>,5=<4+TRACE>)",  range: "1..5",
 		description: "optional" )        
}
metadata {
	// Automatically generated. Make future change here.
	definition(name: "My Next SensorV2", author: "Yves Racine", 
					ocfDeviceType: "x.com.st.d.sensor.temperature", namespace: "yracine") {
		capability "Sensor"
		capability "Temperature Measurement"
		capability "Polling"
		capability "Refresh"
		capability "Health Check"

/* Not supported
		command "getStructure"        
		command "setStructure"        
		command "setStructureHome"
		command "setStructureAway"
		command "setETA"		// call sample: hvac.setETA("","sample-trip-id","2018-01-30 23:00:00","2018-01-31 23:59:59")   
		command "cancelETA"		// call sample: hvac.cancelETA("","sample-trip-id")         
		command "getTips"  
		command "resetTips"     
*/        

        
		command "setSensorSettings"
		command "produceSummaryReport"
		command "save_data_auth"        
 
//		command "generateRTEvents" // Not supported yet

/*
		strcuture attributes 

		attribute "st_away","string"
		attribute "st_name","string"
		attribute "st_country_code","string"
		attribute "st_postal_code","string"
		attribute "st_peak_period_start_time","string"
		attribute "st_peak_period_end_time","string"
		attribute "st_time_zone","string"
		attribute "st_eta_trip_id","string"
		attribute "st_estimated_arrival_window_begin","string"
		attribute "st_estimated_arrival_window_end","string"
		attribute "st_eta_begin","string"
		attribute "st_wwn_security_state","string"        

		attribute "structureId", "string"        
		attribute "structureData", "string"        
*/        

		attribute "sensorId", "string"
		attribute "sensorName", "string"
		attribute "locale", "string"
        
//		attribute "structure_id","string"


//		new attributes in V2

		attribute "where_name","string"
//		attribute "label","string"
		attribute "battery_health","string"
		attribute "name_long","string"
		attribute "last_api_check","string"
		attribute "last_updated_at","string"
		attribute "tstat_scale", "string"
		attribute "verboseTrace", "string"
		attribute "last_connection","string"
        
        
		attribute "current_temperature", "string"	
		attribute "target_temperature", "string" 
/*        
		attribute "hvac_ac_state", "string"
		attribute "hvac_heater_state", "string"
		attribute "target_temperature_high", "string"
		attribute "target_temperature_low", "string"
 		attribute "auto_away", "string"
 		attribute "auto_away_learning", "string"
		attribute "hvac_cool_X2_state", "string"
		attribute "hvac_cool_X3_state", "string"
		attribute "hvac_heat_x2_state", "string"
		attribute "hvac_heat_x3_state", "string"
		attribute "hvac_aux_heater_state", "string"
		attribute "hvac_aux_heat_state", "string"
		attribute "hvac_emer_heat_state", "string"
 */
 		
		attribute "battery_level", "string"
		attribute "summaryReport", "string"


	}        
	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
    
		multiAttributeTile(name:"sensorMulti", type:"generic", width:6, height:4,canChangeIcon: true,backgroundColor:"#44b621") {
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
				attributeState("default", label:'${currentValue}', unit:"dF", backgroundColor: "#44b621") 
			}
		}
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperatureDisplay", label:'${currentValue}', unit:"dF",
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
			])
		}
		valueTile("name", "device.sensorName", inactiveLabel: false, width: 2,
			height: 2) {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
      
/*
		standardTile("mode", "device.thermostatMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "off", label: ' ',action: "switchMode",
				icon: "st.sensor.heating-cooling-off", backgroundColor: "#ffffff"
			state "eco", label: '${name}', action: "switchMode", 
				icon: "st.nest.nest-leaf", backgroundColor: "#ffffff"
			state "cool", label: '${name}', action: "switchMode",
				icon: "${getCustomImagePath()}coolMode.png", backgroundColor: "#ffffff"
			state "heat", label: '${name}', action: "switchMode",
				icon: "${getCustomImagePath()}heatMode.png", backgroundColor: "#ffffff"
			state "auto", label: '${name}', action: "switchMode",
				icon: "${getCustomImagePath()}autoMode.png", backgroundColor: "#ffffff"
		}
             
      
		valueTile("isOnline", "device.is_online", width: 2, height: 2,  decoration: "flat", inactiveLabel: false) {
			state "default", label:'Online ${currentValue}',
			backgroundColor: "#ffffff"
		}
*/        
		standardTile("refresh", "device.temperature", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {
			state "default", label: 'Refresh',action: "refresh", icon:"st.secondary.refresh", 			
			backgroundColor: "#ffffff"
		}
   		valueTile(	"lastConnection", "device.last_connection",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
			state("default",
				label:'LastConnect ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
        
		valueTile(	"swVersion", "device.software_version",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
			state("default",
				label:'swVersion ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
  	
		valueTile(	"lastAPICheck", "device.last_api_check",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
 			state("default",
				label:'LastAPICheck ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
//		htmlTile(name:"graphHTML", action: "getGraphHTML", width: 6, height: 8,  whitelist: ["www.gstatic.com"])
       
       
		main("sensorMulti")
		details(["sensorMulti",
			"name",	
//            "mode","fanMode",
// 			"swVersion",
			"lastConnection",
			"lastAPICheck",            
			"refresh",
//			"present",            
//			"graphHTML"            
		])

	}
    
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

mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}

void installed() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "cloud", scheme:"untracked"]), displayed: false)

	state?.scale=getTemperatureScale() 
//	setTemperatureScale("$state?.scale")    
	if (settings.trace) { 
		log.debug("installed>$device.displayName installed with settings: ${settings.inspect()} and state variables= ${state.inspect()}")
	}
	state?.redirectURL=null
	state?.retriesCounter=0        
	state?.retriesSettingsCounter=0        
	state?.redirectURLcount=0            
    
}  

/* Ping is used by Device-Watch in attempt to reach the device
*/
def ping() {
	poll()
}

def updated() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "cloud", scheme:"untracked"]), displayed: false)
    
	state?.scale=getTemperatureScale() 
//	setTemperatureScale(state?.scale)    
	state?.retriesCounter=0       
	state?.retriesSettingsCounter=0       
	state?.retriesHoldCounter=0       
	state?.scale=getTemperatureScale() 
	traceEvent(settings.logFilter,"updated>$device.displayName updated with settings: ${settings.inspect()} and state variables= ${state.inspect()}", settings.trace)
//	retrieveDataForGraph()        
}

//remove from the selected devices list in Service Manager
void uninstalled() {
	traceEvent(settings.logFilter, "executing uninstalled for ${this.device.displayName}", settings.trace)
	parent.purgeChildDevice(this)    
}



private def isOnline() {
	def result=(device.currentValue("is_online")=='true'? true :false)        
	return result
}




void updateChildData(objects) {
	traceEvent(settings.logFilter,"updateChildData>objects from parent=$objects",settings.trace,GLOBAL_LOG_TRACE)        
	if (!data?.sensors) {
		data?.sensors=[]    
	}    
	data?.sensors=objects
	traceEvent(settings.logFilter,"updateChildData>data.sensors=${data?.sensors}",settings.trace,GLOBAL_LOG_TRACE)        
}


// parse events into attributes
def parse(String description) {

}

// sensorId		single sensorId 
private def refresh_sensor(sensorId="") {
	def structures
	sensorId=determine_sensor_id(sensorId) 
    
	def scale = getTemperatureScale()
	state?.scale= scale
	def todayDay = new Date().format("dd",location.timeZone)
	def timeToday= new Date().format("HH:mm",location.timeZone)
    
  	if ((!state?.today) || (state?.today != todayDay))  {
		state?.today=todayDay        
		traceEvent(settings.logFilter,"refresh_sensor>about to call parent.getStructures(), type=buckets", detailedNotif)
		parent.getStructures(true, cache_timeout,'["buckets"]')
        
	}  
	if (!data?.sensors) {
		data?.sensors=[]
	}        

	double temperature
    
	if (scale == "C") {
//		coolingSetpointString = String.format('%2.1f',coolingSetpoint)
//		heatingSetpointString = String.format('%2.1f',heatingSetpoint)
		temperature=data?.sensors[0]?.temperature        
	} else {
//		coolingSetpointString= String.format('%2d', coolingSetpoint.intValue())            
//		heatingSetpointString= String.format('%2d', heatingSetpoint.intValue())            
		temperature= cToF_Rounded(data?.sensors[0]?.temperature)       
	}
	def dataEvents = [
		sensorId:  data?.sensors[0]?.id,
		temperature: temperature,
 		sensorName:data?.sensors[0]?.name,
//		"structure_id": data?.sensors[0]?.structure_id,
//		humidity:data?.sensors[0]?.humidity,
		"locale": data?.sensors[0]?.locale,
		"tstat_scale": scale,
		"software_version": data?.sensors[0]?.software_version,
		"where_id": data?.sensors[0]?.where_id,
		"battery_level": data?.sensors[0]?.battery_level,      
//		"can_heat": data?.sensors[0]?.can_heat.toString(),
//		"can_cool":data?.sensors[0]?.can_cool.toString(),
//		"target_temperature_c":data?.sensors[0]?.target_temperature,
//		"target_temperature_f": cToF_Rounded(data?.sensors[0]?.target_temperature),
//		"target_temperature_high_c": data?.sensors[0]?.target_temperature_high,
//		"target_temperature_high_f": cToF_Rounded(data?.sensors[0]?.target_temperature_high),
//		"target_temperature_low_c": data?.sensors[0]?.target_temperature_low,
//		"target_temperature_low_f": cToF_Rounded(data?.sensors[0]?.target_temperature_low),
//		"ambient_temperature_c": data?.sensors[0]?.ambient_temperature_,
//		"ambient_temperature_f":  cToF_Rounded(data?.sensors[0]?.ambient_temperature),
//		"eco_temperature_high_c": data?.sensors[0]?.eco_temperature_high,
//		"eco_temperature_high_f": cToF_Rounded(data?.sensors[0]?.eco_temperature_high),
//		"eco_temperature_low_c": data?.sensors[0]?.eco_temperature_low,
//		"eco_temperature_low_f": cToF_Rounded(data?.sensors[0]?.eco_temperature_low),
//		"is_locked": data?.sensors[0]?.is_locked.toString(),
//		"locked_temp_min_c": data?.sensors[0]?.locked_temp_min_c,
//		"locked_temp_min_f": data?.sensors[0]?.locked_temp_min_f,
//		"locked_temp_max_c": data?.sensors[0]?.locked_temp_max_c,
//		"locked_temp_max_f": data?.sensors[0]?.locked_temp_max_f,
		"where_name": data?.sensors[0]?.where_name,
//		"label": data?.sensors[0]?.label,
		"name_long":data?.sensors[0]?.long_name,
//		"is_online": data?.sensors[0]?.is_online.toString(),
		"last_updated_at": data?.sensors[0]?.last_updated_at,
		"last_connection": (data?.sensors[0]?.last_updated_at)? formatTimeInTimezone((data?.sensors[0]?.last_updated_at*1000))?.substring(0,16):"",
		"last_api_check": formatTimeInTimezone(now())?.substring(0,16),
        
 	]
	
     
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_sensor>done for sensorId =${sensorId}", settings.trace)
}


// refresh() has a different polling interval as it is called by the UI (contrary to poll).
void refresh() {
	Date endDate= new Date()
	Date startDate = endDate -1    
	def sensorId= determine_sensor_id("") 	    
	def poll_interval=0.5  // set a 30 sec. poll interval to avoid unecessary load on Nest servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"refresh>sensorId = ${sensorId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), not refreshing data...",
			settings.trace)	            
		return
	}
	state.lastPollTimestamp = now()
	getSensorInfo(sensorId, false)    
	refresh_sensor(sensorId)
  
}


void poll() {
    
	def sensorId= determine_sensor_id("") 	    

	def poll_interval=1  // set a minimum of 1min. poll interval to avoid unecessary load on Nest servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"poll>sensorId = ${sensorId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), not refreshing data...",
			settings.trace, GLOBAL_LOG_INFO)            
		return
	}
	getSensorInfo(sensorId, true)
	refresh_sensor(sensorId)    
	traceEvent(settings.logFilter,"poll>done for sensorId =${sensorId}", settings.trace)

}


private void generateEvent(Map results) {
	def scale =state?.scale
	traceEvent(settings.logFilter,"generateEvent>parsing data $results, scale=$scale", settings.trace)
    
	if (results) {
		results.each { name, value ->
			def isDisplayed = true
			String upperFieldName=name.toUpperCase()    

// 			Temperature variable names for display contain 'display'            

			if (upperFieldName?.contains("DISPLAY")) {  

				String tempValueString 
				double tempValue 
				if (scale == "C") {
					tempValue = value.toDouble()
					tempValueString = String.format('%2.1f', tempValue)
				                    
				} else {
					tempValue = value.toDouble().round()
					tempValueString = String.format('%2d', tempValue.intValue())            
				}
                
				def isChange = isStateChange(device, name, tempValue.toString())
                
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, displayed: isDisplayed, unit: scale)                                     									 

			} else if (upperFieldName == "TARGET_TEMPERATURE_TYPE") { 
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), isStateChange: isChange, displayed: isDisplayed)       
			} else if (upperFieldName?.contains("THERMOSTATSETPOINT")) {    // make sure that the thermostat setpoint is ending with .0 or .5
				String tempValueString 
				double tempValue=value.toDouble().round(1)                
				if (state?.scale =='C') {    
					tempValueString = String.format('%2.1f', tempValue )                    
					if (tempValueString.matches(".*([.,][3456])")) {                
						tempValueString=String.format('%2d.5',tempValue.intValue())                
						traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
					} else if (tempValueString.matches(".*([.,][789])")) {  
						traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
						tempValue=tempValue.intValue() + 1                        
						tempValueString=String.format('%2d.0',tempValue.intValue())               
					} else {
						traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
						tempValueString=String.format('%2d.0', tempValue.intValue())               
					}
				} else {
					tempValueString = String.format('%2d', tempValue.intValue())
				}    			
				def isChange = isStateChange(device, name, tempValueString)		// take value as is -don't transform it as it's been calculated already
				isDisplayed = isChange

				sendEvent(name: name, value: value, isStateChange: isChange, displayed: isDisplayed, unit: scale)       

			} else if ((upperFieldName?.contains("_STATE")) || (upperFieldName?.contains("_ACTIVE")) || 
				(upperFieldName?.contains("_ENABLED")) || (upperFieldName?.contains("HAS_"))) { 
				String valueString=value.toString()                
				value= (value && valueString != 'null') ? value : false            
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), displayed: isDisplayed)                                     									 
                
// 			Temperature variable names contain 'temp' or 'setpoint' (not for display)           

			} else if (((upperFieldName?.contains("TEMP")) || (upperFieldName?.contains("SETPOINT"))) &&
      			((!upperFieldName?.endsWith("_C") && (!upperFieldName?.endsWith("_F"))))) {  
                                
				String tempValueString 
				double tempValue 
				if (scale == "C") {
					tempValue = value.toDouble().round(1)
					tempValueString = String.format('%2.1f', tempValue)
				                    
				} else {
					tempValue = value.toDouble().round()
					tempValueString = String.format('%2d', tempValue.intValue())            
				}
				def isChange = isStateChange(device, name,  tempValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, displayed: isDisplayed, unit: scale)                                     									 
			} else if (upperFieldName?.contains("SPEED")) {

// 			Speed variable names contain 'speed'

 				def speedValue = getSpeed(value).toFloat().round(1)
				def isChange = isStateChange(device, name, speedValue.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: speedValue.toString(), unit: getDistanceScale(), displayed: isDisplayed)                                     									 
			} else if (upperFieldName?.contains("HUMIDITY")) {
 				double humValue = value.toDouble().round(0)
				String humValueString = String.format('%2d', humValue.intValue())
				def isChange = isStateChange(device, name, humValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: humValueString, unit: "%", displayed: isDisplayed)                                     									 
			} else if (upperFieldName?.contains("DATA")) { // data variable names contain 'data'

				sendEvent(name: name, value: value, displayed: (settings.trace?:false))                                     									 

			} else if (value != null && value.toString() != 'null' && value.toString() != '[:]' && value.toString() != '[]') {           
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), isStateChange: isChange, displayed: isDisplayed)       
			}
		}
	}
}

private def getSpeed(value) {
	def miles = value
	if (state?.scale == "C"){
		return milesToKm(miles)
	} else {
		return miles
	}
}

private def getTemperatureValue(value) {
	value = (value!=null)? value:0
	if (state?.scale == "C") {
		return fToC(value)
	} else {
		return value
	}
}


private def getDistanceScale() {
	def scale= state?.scale
	if (scale == 'C') {
		return "kmh"
	}
	return "mph"
}


// Get the basic sensor status (heating,cooling,fan only)
// To be called after a poll() or refresh() to have the latest status

def getThermostatOperatingState() {

	def sensorId= determine_sensor_id("")
	def operatingState = data?.sensors[0]?.operating_state?.toUpperCase()
//	def fanRunning=fanActive()     
	def currentOpState = operatingState?.contains('HEAT')? 'heating' : (operatingState?.contains('COOL')? 'cooling' : 
		(fanRunning)? 'fan only': 'idle')
	return currentOpState
}

// sensorId may only be a specific sensorId or "" (for current sensor)
// To be called after a poll() or refresh() to have the latest status


private void api( method, id, args=null, success = {}) {
	def MAX_EXCEPTION_COUNT=20
	String URI_ROOT = "${get_API_URI_ROOT()}"
    
   
	if (isLoggedIn() && isTokenExpired()) {
//		traceEvent(settings.logFilter,"api>need to refresh tokens",settings.trace)
       
		if (!refresh_tokens()) {
			if ((exceptionCheck) && (state.exceptionCount >= MAX_EXCEPTION_COUNT) && (exceptionCheck?.contains("Unauthorized"))) {
//				traceEvent(settings.logFilter,"api>$exceptionCheck, not able to renew the refresh token;need to re-login to Nest via MyNestInit....", true, GLOBAL_LOG_ERROR)         
			}
		} else {
        
			// Reset Exceptions counter as the refresh_tokens() call has been successful 
			state.exceptionCount=0
		}            
        
	}


	def methods = [
		'setSensorSettings':
			[uri: "${URI_ROOT}/${get_API_VERSION()}/put", type: 'put'],
		]
	def request = methods.getAt(method)
	traceEvent(settings.logFilter,"api> about to call doRequest with (unencoded) args = ${args}", settings.trace)
	if (request.type=="get" && args) {
		def args_encoded = java.net.URLEncoder.encode(args.toString(), "UTF-8")
		request.uri=request.uri + "?${args_encoded}"    
//		request.uri=request.uri + "?${args}"    
	}    
	traceEvent(settings.logFilter,"api> about to call doRequest with (unencoded) args = ${args}", settings.trace)
	doRequest(request.uri, args, request.type, success)
     
	if (state.exceptionCount >= MAX_EXCEPTION_COUNT) {
		def exceptionCheck=device.currentValue("verboseTrace")
		traceEvent(settings.logFilter,"api>error: found a high number of exceptions (${state.exceptionCount}), last exceptionCheck=${exceptionCheck}, about to reset counter",
			settings.trace, GLOBAL_LOG_ERROR)  
		if (!exceptionCheck?.contains("Unauthorized")) {          
			state.exceptionCount = 0  // reset the counter as long it's not unauthorized exception
			sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		}            
	}        

}

// Need to be authenticated in before this is called.
private void doRequest(uri, args, type, success) {
 	def TOKEN_EXPIRED=401
	def REFERER= get_REFERER()
	def USER_AGENT=get_USER_AGENT()    
	def params = [
		uri: uri,
		headers: [
			"Authorization": "${data.auth.token_type} ${data.auth.access_token}",
			query: [format: 'json'],
			'charset': "UTF-8",
			'Content-Type': "application/json",
			'Referer': REFERER,
			'User-Agent': USER_AGENT            
//			'Accept': "application/json"
		],
		body:[]
	]
	try {
		traceEvent(settings.logFilter,"doRequest>about to ${type} with uri ${params.uri}, args= ${args}",settings.trace, GLOBAL_LOG_INFO)
		if (type == 'put') {
			def objects=[objects: [args]]          	
			def argsInJson=new groovy.json.JsonBuilder(objects)        
			traceEvent(settings.logFilter,"doRequest>objects= ${objects}",settings.trace, GLOBAL_LOG_INFO)
			traceEvent(settings.logFilter,"doRequest>argsInJson= ${argsInJson}",settings.trace, GLOBAL_LOG_INFO)
			params?.body = argsInJson.toString()
			traceEvent(settings.logFilter,"doRequest>about to ${type} with params= ${params},", settings.trace, GLOBAL_LOG_INFO)
			httpPostJson(params, success)

		} else if (type == 'get') {
			httpGet(params, success)
		}
		/* when success, reset the exception counter */
		state.exceptionCount=0
		traceEvent(settings.logFilter,"doRequest>done with ${type}", settings.trace)

	} catch (java.net.UnknownHostException e) {
		traceEvent(settings.logFilter,"doRequest> Unknown host ${params.uri}", settings.trace, GLOBAL_LOG_ERROR)
	} catch (java.net.NoRouteToHostException e) {
		traceEvent(settings.logFilter,"doRequest>No route to host - check the URL ${params.uri} ", settings.trace, GLOBAL_LOG_ERROR)       
	} catch (e) {
		traceEvent(settings.logFilter,"doRequest>exception $e,error response=${e?.response?.status} for ${params}", settings.trace, GLOBAL_LOG_ERROR)
		state?.exceptionCount=state?.exceptionCount+1
		if (e?.response?.status== TOKEN_EXPIRED) {
			traceEvent(settings.logFilter,"doRequest>token expired ($e?.response?.status), trying to refresh tokens", settings.trace, GLOBAL_LOG_ERROR)       
			refresh_tokens()
		}    	
	}
   
}


void produceSummaryReport(pastDaysCount) {
	traceEvent(settings.logFilter,"produceSummaryReport>begin",settings.trace, GLOBAL_LOG_TRACE)
	def avg_tstat_temp,avg_room_setpoint, min_room_setpoint=200, max_room_setpoint=0, avg_tstat_humidity    
	boolean found_values=false
	Date todayDate = new Date()
	Date startOfPeriod = todayDate - pastDaysCount
	long min_room_timestamp,max_room_timestamp
	int holdCount, scheduleCount
	def rmSetpointData = device.statesSince("sensorSetpoint", startOfPeriod, [max:200])
	def temperatureData = device.statesSince("temperature", startOfPeriod, [max:200])
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
    
	if (temperatureData) {    
		avg_tstat_temp= (temperatureData.sum{it.floatValue.toFloat()}/ (temperatureData.size())).toFloat().round(1)
		found_values=true        
	}        
	if (!found_values) {
		traceEvent(settings.logFilter,"produceSummaryReport>found no values for report,exiting",settings.trace)
		sendEvent(name: "summaryReport", value: "")
		return        
	}    
	String scale=getTemperatureScale(), unitScale='Fahrenheit',timePeriod="In the past ${pastDaysCount} days"
	if (scale=='C') { 
		unitScale='Celsius'    
	}    
	if (pastDaysCount <2) {
		timePeriod="In the past day"    
	}    
	String roomName =device.currentValue("where_name")
	String summary_report = "At your home," 
	summary_report=summary_report + "${timePeriod}"    
	if (roomName) {	
		summary_report= summary_report + ",in the room ${roomName} where the sensor ${device.displayName} is located"
	} else {
    
		summary_report= summary_report + ",at the Nest ${device.displayName}"
	}    
    
	if (avg_room_temp) {
		summary_report= summary_report + ",the average room temperature was ${avg_room_temp.toString()} degrees ${unitScale}"
	}
/*    
	if (avg_room_setpoint) {
 		summary_report= summary_report + ",your Nest sensor's setpoint was ${avg_room_setpoint.toString()} degrees in average" 
 	}
	if (min_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(min_room_timestamp)					    
		summary_report= summary_report + ".The Nest's minimum setpoint was ${min_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
	if (max_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(max_room_timestamp)					    
		summary_report= summary_report + ",and the Nest's maximum setpoint was ${max_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
*/  
	if (avg_tstat_temp) {
		summary_report= summary_report + ".The sensor average ambient temp collected was ${avg_tstat_temp.toString()} degrees ${unitScale}."
	}
/*    
	if (avg_tstat_humidity) {
		summary_report= summary_report + "And finally, the sensor's average relative humidity observed was ${avg_tstat_humidity.toString()}%."      
	}
*/
	sendEvent(name: "summaryReport", value: summary_report, isStateChange: true)
    
	traceEvent(settings.logFilter,"produceSummaryReport>end",settings.trace, GLOBAL_LOG_TRACE)

}


// sensorId is single serial id 
//	if no sensorId is provided, it is defaulted to the current sensorId 
// settings can be anything supported by Nest at https://developers.nest.com/documentation/cloud/api-sensor
void setSensorSettings(sensorId,sensorSettings = []) {
	def TOKEN_EXPIRED=401    
	def REDIRECT_ERROR=307  
	def NEST_SUCCESS=200    
	def BLOCKED=429
	def interval=1*60
	
    
   	sensorId= determine_sensor_id(sensorId) 	    
	if (state?.lastStatusCodeForSettings==BLOCKED) {    
		def poll_interval=1  // set a minimum of 1 min interval to avoid unecessary load on Nest servers
		def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
		if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
			traceEvent(settings.logFilter,"setSensorSettings>sensorId = ${sensorId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), throttling in progress, command not being processed",
				settings.trace, GLOBAL_LOG_ERROR)            
			return
		}
	}
	traceEvent(settings.logFilter,"setSensorSettings>called with values ${sensorSettings} for ${sensorId}",settings.trace)
	if (sensorSettings == null || sensorSettings == "" || sensorSettings == [] ) {
		traceEvent(settings.logFilter, "setSensorSettings>sensorSettings set is empty, exiting", settings.trace)
		return        
	}
	def bodyReq = [object_key:"kryptonite.${sensorId}",op:"MERGE",value:sensorSettings]    
	int statusCode
	def exceptionCheck
	api('setSensorSettings', sensorId, bodyReq) {resp ->
		statusCode = resp?.status
		state?.lastStatusCodeForSettings=statusCode				                
		if (statusCode== REDIRECT_ERROR) {
			if (!process_redirectURL( resp?.headers.Location)) {
				traceEvent(settings.logFilter,"setSensorSettings>Nest redirect: too many redirects, count =${state?.redirectURLcount}", true, GLOBAL_LOG_ERROR)
				return                
			}
			        
			traceEvent(settings.logFilter,"setSensorSettings>Nest redirect: about to call setSensorSettings again, count =${state?.redirectURLcount}", true)
			doRequest( resp?.headers.Location, bodyReq, 'put') {redirectResp->
				statusCode=redirectResp?.status            
			}            
		}		    
		if (statusCode==BLOCKED) {
			traceEvent(settings.logFilter,"setSensorSettings>sensorId=${sensorId},Nest throttling in progress, error $statusCode, retries={state?.retriesSettingsCounter}", settings.trace, GLOBAL_LOG_ERROR)
			interval=1*60 			   // set a minimum of 5min. interval to avoid unecessary load on Nest servers
		}			
		exceptionCheck=device.currentValue("verboseTrace")
		if (statusCode == NEST_SUCCESS) {
			/* when success, reset the exception counter */
			state.exceptionCount=0
			if ((data?."replaySettingsId${state?.retriesSettingsCounter}" == null) ||
				(state?.retriesSettingsCounter > get_MAX_SETTER_RETRIES())) {          // reset the counter if last entry is null
				reset_replay_data('Settings')                
				state?.retriesSettingsCounter=0
			}            
			state?.redirectURLcount=0   
			traceEvent(settings.logFilter,"setSensorSettings>done for ${sensorId}", settings.trace)
		} else {
			traceEvent(settings.logFilter,"setSensorSettings> error=${statusCode.toString()} for ${sensorId}", true, GLOBAL_LOG_ERROR)
		} /* end if statusCode */
	} /* end api call */                
	if (exceptionCheck?.contains("exception")) {
		sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		traceEvent(settings.logFilter,"setSensorSettings>exception=${exceptionCheck}", true, GLOBAL_LOG_ERROR)
	}                
	if ((statusCode == BLOCKED) ||
		(exceptionCheck?.contains("Nest response error")) || 
		(exceptionCheck?.contains("ConnectTimeoutException"))) {
		state?.retriesSettingsCounter=(state?.retriesSettingsCounter?:0)+1            
		if (!(interval= get_exception_interval_delay(state?.retriesSettingsCounter))) {   
			traceEvent(settings.logFilter,"SetsensorSettings>too many retries", true, GLOBAL_LOG_ERROR)
			state?.retriesSettingsCounter=0 
			reset_replay_data('Settings')                
			return        
		}        
		state.lastPollTimestamp = (statusCode==BLOCKED) ? (now() + (interval * 1000)):(now() + (1 * 60 * 1000)) 
		data?."replaySettingsId${state?.retriesSettingsCounter}"=sensorId
		data?."replaySettings${state?.retriesSettingsCounter}"=sensorSettings  
		traceEvent(settings.logFilter,"setSensorSettings>about to call setSensorSettingsReplay,retries counter=${state?.retriesSettingsCounter}", true, GLOBAL_LOG_INFO)
		runIn(interval, "setSensorSettingsReplay",[overwrite:true])          
	}    
}

void setSensorSettingsReplay() {
	def exceptionCheck=""

	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) {
		def sensorId = data?."replaySettingsId$i"
		if (sensorId == null) continue  // already processed        
		def sensorSettings = data?."replaySettings$i"
		def poll_interval=1 
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		traceEvent(settings.logFilter,"setSensorSettingsReplay>about to recall setSensorSettings for sensorId=$sensorId,retries counter=$i" +
			",sensorSettings=$sensorSettings", true, GLOBAL_LOG_INFO)
		setSensorSettings(sensorId,sensorSettings) 
		exceptionCheck=device.currentValue("verboseTrace")
		if (exceptionCheck?.contains("done")) {
			data?."replaySettingsId$i"=null        
		} /* end if */
	} /* end for */
	if (exceptionCheck?.contains("done")) { // if last command executed OK, then reset the counter
		reset_replay_data('Settings')                
		state?.retriesSettingsCounter=0
	}     
}    



//	if no sensorId is provided, it is defaulted to the current sensorId 
void getSensorInfo(sensorId,useCache=true) {
	def NEST_SUCCESS=200
	def TOKEN_EXPIRED=401 
	def REDIRECT_ERROR=307    
	def BLOCKED=429
	def interval=1*60    

	sensorId=determine_sensor_id(sensorId)    

	parent.getObject(sensorId,"sensor",useCache)
	parent.updateObjects(this, "sensor", sensorId)   
    
}




void getsensorInfoReplay() {
	def id = data?.replayId
	def poll_interval=1 
	state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
	traceEvent(settings.logFilter,"getsensorInfoReplay>about to call getsensorInfo for ${id}",settings.trace, GLOBAL_LOG_INFO)
	getsensorInfo(id) 
}    
 


private void reset_replay_data(replayBuffer) { 
	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) 
		data?."replay${replayBuffer}Id${i}"= null
}


private int get_exception_interval_delay(counter,method="SETTER") {

	int max_retries=(method=="SETTER")? get_MAX_SETTER_RETRIES() :get_MAX_GETTER_RETRIES()
	counter=(!counter)?(max_retries+1):counter
	int interval = 1*60 * (counter as int) // the interval delay will increase if multiple retries have already been made

	if (counter > max_retries) {
		traceEvent(settings.logFilter,"get_exception_interval_delay>error max retries ($max_retries), counter=${counter}, exiting", settings.trace, GLOBAL_LOG_WARN)
		return 0
	}        
	if (counter>=5) {
		interval=(get_RETRY_DELAY_FACTOR() *  counter * 60)  // increase delay even more when number of retries >5            
	}            
	if (counter>=7) {
		interval=interval + (get_RETRY_DELAY_FACTOR() * counter * 60)  // increase delay even more when number of retries >7           
	}            
	return interval    
}

private boolean refresh_tokens() {
	// Nest logic for refresh_tokens not available for the moment.
	parent.refreshThisChildAuthTokens(this)    
	return true
}

synchronized void save_data_auth(auth) {

	data?.auth?.access_token = auth.access_token 
//	data?.auth?.refresh_token = auth.refresh_token
	data?.auth?.expires_in = auth.expires_in
	data?.auth?.token_type = "Bearer" 
//	data?.auth?.scope = auth?.scope
	data?.auth?.authexptime = auth.authexptime
	traceEvent(settings.logFilter,"save_data_auth>saved data.auth=$data.auth")
}


private def isLoggedIn() {
	if (data?.auth?.access_token == null) {
		traceEvent(settings.logFilter,"isLoggedIn> no data auth", settings.trace,GLOBAL_LOG_TRACE)
		return false
	} 
	return true
}

private def isTokenExpired() {
	def buffer_time_expiration=5  // set a 5 min. buffer time 
	def time_check_for_exp = now() + (buffer_time_expiration * 60 * 1000)
/*    
	if (!data?.auth?.authexptime) {
		login()    
	}    
*/  
	if (data?.auth?.authexptime) {
    
		double authExpTimeInMin= ((data?.auth?.authexptime - time_check_for_exp)/60000).toDouble().round(0)  
		traceEvent(settings.logFilter,"isTokenExpired>expiresIn timestamp: ${data.auth.authexptime} > timestamp check for exp: ${time_check_for_exp}?",settings.trace)
		traceEvent(settings.logFilter,"isTokenExpired>expires in ${authExpTimeInMin.intValue()} minutes",settings.trace)
		traceEvent(settings.logFilter,"isTokenExpired>data.auth= $data.auth",settings.trace)
		if (authExpTimeInMin <0) {
//			traceEvent(settings.logFilter,"isTokenExpired>auth token buffer time  expired (${buffer_time_expiration} min.), countdown is ${authExpTimeInMin.intValue()} minutes, need to refresh tokens now!",
//				settings.trace, GLOBAL_LOG_WARN)        
		}    
		if (authExpTimeInMin < (0-buffer_time_expiration)) {
//			traceEvent(settings.logFilter,"isTokenExpired>refreshing tokens is more at risk (${authExpTimeInMin} min.),exception count may increase if tokens not refreshed!", settings.trace, GLOBAL_LOG_WARN)
		}    
		if (data.auth.authexptime > time_check_for_exp) {
//			traceEvent(settings.logFilter,"isTokenExpired> not expired...", settings.trace)
			return false
		}            
	}
//	traceEvent(settings.logFilter,"isTokenExpired> expired...", settings.trace)
	return true
}


// Determine id from settings or initialSetup
private def determine_sensor_id(sensor_id) {
	def sensorId=device.currentValue("sensorId")
    
	if ((sensor_id != null) && (sensor_id != "")) {
		sensorId = sensor_id
	} else if ((settings.sensorId != null) && (settings.sensorId  != "")) {
		sensorId = settings.sensorId.trim()
		traceEvent(settings.logFilter,"determine_sensor_id> sensorId = ${settings.sensorId}", settings.trace)
	} else if (data?.auth?.sensorId) {
		settings.appKey = data.auth.appKey
		settings.sensorId = data.auth.sensorId
		sensorId=data.auth.sensorId
		traceEvent(settings.logFilter,"determine_sensor_id> sensorId from data.auth= ${data.auth.sensorId}",settings.trace)
	} else if ((sensorId !=null) && (sensorId != "")) {
		settings.sensorId = sensorId
		traceEvent(settings.logFilter,"determine_sensor_id> sensorId from device= ${sensorId}",settings.trace)
	}
    
	if ((sensor_id != "") && (sensorId && sensor_id !=sensorId)) {
		sendEvent(name: "sensorId", displayed: (settings.trace?:false),value: sensorId)    
	}
	return sensorId
}

// Get the appKey for authentication
private def get_appKey() {
	return data?.auth?.appKey    
    
}    

// @Get the privateKey for authentication
private def get_privateKey() {
	
	return data?.auth?.privateKey
}    
   


// Called by MyNextServiceMgr for initial creation of a child Device
void initialSetup(auth_data, device_sensor_id) {
	settings.trace=true
	settings?.logFilter=5
    
	traceEvent(settings.logFilter,"initialSetup>begin",settings.trace)
//	log.debug "initialSetup> structure_id = ${structure_id}"
	log.debug "initialSetup> device_sensor_id = ${device_sensor_id}"
//	log.debug "initialSetup> device_client_id = ${device_client_id}"
//	log.debug "initialSetup> private_key_id = ${private_key_id}"
//	traceEvent(settings.logFilter,"initialSetup> structure_id = ${structure_id}",settings.trace)
	traceEvent(settings.logFilter,"initialSetup> device_sensor_id = ${device_sensor_id}",settings.trace)
//	traceEvent(settings.logFilter,"initialSetup> device_client_id = ${device_client_id}",settings.trace)
//	traceEvent(settings.logFilter,"initialSetup> private_key_id = ${private_key_id}",settings.trace)
//	settings?.structureId = structure_id
	settings.sensorId = device_sensor_id
	sendEvent(name: "sensorId", value:device_tstat_id,  displayed: (settings.trace?: false))    
//	sendEvent(name: "structureId", value: structure_id,  displayed: (settings.trace?: false))
	data?.auth=settings
//	data?.auth?.appKey=device_client_id
//	data?.auth?.privateKey=private_key_id
	save_data_auth(auth_data)    
	log.debug "initialSetup> settings = $settings"
	log.debug "initialSetup> data_auth = $data.auth"
	log.debug "initialSetup>end"
	traceEvent(settings.logFilter,"initialSetup> settings = $settings",settings.trace)
	traceEvent(settings.logFilter,"initialSetup> data_auth = $data.auth",settings.trace)
	traceEvent(settings.logFilter,"initialSetup>end",settings.trace)

	runIn(1*60, "refresh")
	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()
	state?.currentMode=device.currentValue("sensorMode")    
    
}



private boolean isWeekday() {
	Calendar myDate = Calendar.getInstance()
	int dow = myDate.get (Calendar.DAY_OF_WEEK)
	boolean isWeekday = ((dow >= Calendar.MONDAY) && (dow <= Calendar.FRIDAY))
	return isWeekday
}

private def cToF(temp) {
	return (temp * 1.8 + 32)
}
private def cToF_Rounded(temp) {
	if (temp==null) return 0
	def temp_inF= temp * 1.8 + 32
	return temp_inF.toDouble().round()
}
private def fToC(temp) {
	return (temp - 32).toDouble() / 1.8
}
private def milesToKm(distance) {
	return (distance * 1.609344) 
}
private def get_API_URI_ROOT() {
	traceEvent(settings.logFilter, "get_API_URI_ROOT> root=${data?.auth?.nest_czfe_url}", settings.trace)   
	def root=data?.auth?.nest_czfe_url    
	return root
}

private def get_API_VERSION() {
	return "v5"
}

private def get_USER_AGENT() { 

	return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) " +
             "AppleWebKit/537.36 (KHTML, like Gecko) " +
             "Chrome/75.0.3770.100 Safari/537.36"
}

private def get_REFERER() { 

	return "https://home.nest.com/"
}

// Maximum URL redirect
private def  get_MAX_REDIRECT() {
	return 10
}

private def get_MAX_ERROR_WITH_REDIRECT_URL() {
	return 15
}

// Maximum number of command retries for setters
private def get_MAX_SETTER_RETRIES() {
	return 10
}


// Maximum number of command retries for getters
private def get_MAX_GETTER_RETRIES() {
	return 2
}

private def get_RETRY_DELAY_FACTOR() {
	return 3.1
}


private def get_MAX_TIPS() {
	return 5
}

private def getCustomImagePath() {
	return "https://raw.githubusercontent.com/yracine/device-type-myNext/master/icons/"
}    

private def ISODateFormat(dateString, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	String timezoneInString = new Date().format("zzz", myTimezone)
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz")
	Date aDate = sdf.parse(dateString.substring(0,19) + ' ' + timezoneInString)
	String ISODateInString =new Date(aDate.getTime()).format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	return ISODateInString
}


private def formatDate(dateString) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm zzz")
	Date aDate = sdf.parse(dateString)
	return aDate
}
private def formatTimeInTimezone(dateTime, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	String dateInLocalTime =new Date(dateTime).format("yyyy-MM-dd HH:mm:ss zzz", myTimezone)
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



def retrieveDataForGraph() {
	def scale = state?.scale
	Date todayDate = new Date()
	def todayDay = new Date().format("dd",location.timeZone)
	String mode = device.currentValue("sensorMode")    
	String todayInLocalTime = todayDate.format("yyyy-MM-dd", location.timeZone)
	String timezone = new Date().format("zzz", location.timeZone)
	String todayAtMidnight = todayInLocalTime + " 00:00 " + timezone
	Date startOfToday = formatDate(todayAtMidnight)
	Date startOfWeek = startOfToday -7
	def MIN_DEVIATION_TEMP=(scale=='C'?1:2)    
	def MIN_DEVIATION_HUM=10    
    
	traceEvent(settings.logFilter,"retrieveDataForGraph>today at Midnight in local time= ${todayAtMidnight}",settings.trace)
	def heatingSetpointTable = []
	def coolingSetpointTable = []
	def humidityTable = []
	def temperatureTable = []
	def heatingSetpointData = device.statesSince("heatingSetpoint", startOfWeek, [max:200])
	def coolingSetpointData = device.statesSince("coolingSetpoint", startOfWeek, [max:200])
	def humidityData = device.statesSince("humidity", startOfWeek, [max:200])
	def temperatureData = device.statesSince("temperature", startOfWeek, [max:200])

	def previousValue=null
	int maxInd=(heatingSetpointData) ? (heatingSetpointData?.size()-1) :-1
	for (int i=maxInd; (i>=0);i--) {
		// filter some values        
		if (i !=maxInd) previousValue = heatingSetpointData[i+1]?.floatValue
		if ((i==0) || (i==maxInd) || ((heatingSetpointData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (heatingSetpointData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
			heatingSetpointTable.add([heatingSetpointData[i].date.getTime(),heatingSetpointData[i].floatValue])
		}		           
	}
	previousValue=null
	maxInd=(coolingSetpointData)  ? (coolingSetpointData?.size()-1) :-1   
	for (int i=maxInd; (i>=0);i--) {
		if (i !=maxInd) previousValue = coolingSetpointData[i+1]?.floatValue
		// filter some values        
		if ((i==0) || (i==maxInd) || ((coolingSetpointData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (coolingSetpointData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
 			coolingSetpointTable.add([coolingSetpointData[i].date.getTime(),coolingSetpointData[i].floatValue])
		}            
	} /* end for */            
	previousValue=null
	maxInd=(humidityData) ? (humidityData?.size()-1) :-1    
	for (int i=maxInd; (i>=0);i--) {
		if (i !=maxInd) previousValue = humidityData[i+1]?.value
		// filter some values        
		if ((i==0) || (i==maxInd) || ((humidityData[i]?.value <= (previousValue - MIN_DEVIATION_HUM)) || (humidityData[i]?.value >= (previousValue + MIN_DEVIATION_HUM)) )) {
 			humidityTable.add([humidityData[i].date.getTime(),humidityData[i].value])
		}            
	} /* end for */            
	previousValue=null
	maxInd=(temperatureData) ? temperatureData?.size()-1 :-1    
	for (int i=maxInd; (i>=0);i--) {
		// filter some values        
		if (i !=maxInd) previousValue = temperatureData[i+1]?.floatValue
		if ((i==0) || (i==maxInd) || ((temperatureData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (temperatureData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
			temperatureTable.add([temperatureData[i].date.getTime(),temperatureData[i].floatValue])
		}
	} /* end for */            
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
	if (heatingSetpointTable == []) { // if heatingSetpoint has not changed for a week
		def currentHeatingSetpoint=device.currentValue("heatingSetpoint")
		if (currentHeatingSetpoint) { 
			heatingSetpointTable.add([startOfWeek.getTime(),currentHeatingSetpoint])		        
			heatingSetpointTable.add([todayDate.getTime(),currentHeatingSetpoint])		        
		}    
	} else {
		def currentHeatingSetpoint=device.currentValue("heatingSetpoint")
		if (currentHeatingSetpoint) { 
			heatingSetpointTable.add([todayDate.getTime(),currentHeatingSetpoint])		        
		}    
	}    
 	if (coolingSetpointTable == []) {  // if coolingSetpoint has not changed for a week
		def currentCoolingSetpoint=device.currentValue("coolingSetpoint")
		if (currentCoolingSetpoint) { 
			coolingSetpointTable.add([startOfWeek.getTime(),currentCoolingSetpoint])		        
			coolingSetpointTable.add([todayDate.getTime(),currentCoolingSetpoint])		        
		}    
	} else {
		def currentCoolingSetpoint=device.currentValue("coolingSetpoint")
		if (currentCoolingSetpoint) { 
			coolingSetpointTable.add([todayDate.getTime(),currentCoolingSetpoint])		        
		}    
	}    
 	if (humidityTable == []) {  // if humidity has not changed for a week
		def currentHumidity=device.currentValue("humidity")
		if (currentHumidity) { 
			humidityTable.add([startOfWeek.getTime(),currentHumidity])		        
			humidityTable.add([todayDate.getTime(),currentHumidity])		        
		}    
	} else {
		def currentHumidity=device.currentValue("humidity")
		if (currentHumidity) { 
			humidityTable.add([todayDate.getTime(),currentHumidity])		        
		}    
	}    
	if (mode=='auto') {    
		float median = ((device.currentValue("coolingSetpoint") + device.currentValue("heatingSetpoint"))?.toFloat()) /2
		float currentTempAtTstat = device.currentValue("temperature")?.toFloat()        
		if (currentTempAtTstat> median) {
			mode='cool'
		} else {
			mode='heat'
		}   
	}
	state?.currentMode=mode     
	state?.heatingSetpointTable = heatingSetpointTable
	state?.coolingSetpointTable = coolingSetpointTable
	state?.humidityTable = humidityTable
	state?.temperatureTable = temperatureTable
	traceEvent(settings.logFilter,"retrieveDataForGraph>temperatureTable (size=${state?.temperatureTable.size()}) =${state?.temperatureTable}",settings.trace,GLOBAL_LOG_TRACE)  
	traceEvent(settings.logFilter,"retrieveDataForGraph>state.currentMode= ${state?.currentMode}",settings.trace)    
	traceEvent(settings.logFilter,"retrieveDataForGraph>heatingSetpointTable (size=${state?.heatingSetpointTable.size()}) =${state?.heatingSetpointTable}",settings.trace, GLOBAL_LOG_TRACE)  
	traceEvent(settings.logFilter,"retrieveDataForGraph>coolingSetpointTable (size=${state?.coolingSetpointTable.size()}) =${state?.coolingSetpointTable}",settings.trace, GLOBAL_LOG_TRACE)  
	traceEvent(settings.logFilter,"retrieveDataForGraph>humidityTable (size=${state?.humidityTable.size()}) =${state?.humidityTable}",settings.trace,GLOBAL_LOG_TRACE)  
}

def getStartTime() {
	long startTime = new Date().getTime().toLong()
    
	if (state?.currentMode == 'heat') {    
		if ((state?.heatingSetpointTable) && (state?.heatingSetpointTable?.size() > 0)) {
			startTime = state?.heatingSetpointTable.min{it[0]}[0].toLong()
		}
	} else {        
		if ((state?.coolingSetpointTable) && (state?.coolingSetpointTable?.size() > 0)) {
			startTime = state?.coolingSetpointTable.min{it[0]}[0].toLong()
		}
	}        
	if ((state?.humidityTable) && (state?.humidityTable?.size() > 0)) {
		startTime = Math.min(startTime, state.humidityTable.min{it[0]}[0].toLong())
	}
	return startTime
}


String getDataString(Integer seriesIndex) {
	def dataString = ""
	def dataTable = []
	def dataArray    
	switch (seriesIndex) {
		case 1:
			dataTable = state?.heatingSetpointTable
			break
		case 2:
			dataTable = state?.coolingSetpointTable
			break
		case 3:
			dataTable = state?.temperatureTable
			break
		case 4:
			dataTable = state?.humidityTable
			break
	}
	dataTable.each() {
		dataString += "[new Date(${it[0]}),"
		if (seriesIndex==1) {
			dataString += "${it[1]},null,null],"
		}
		if (seriesIndex==2) {
			dataString += "${it[1]},null,null],"
		}
		if (seriesIndex==3) {
			dataString += "null,${it[1]},null],"
		}
		if (seriesIndex==4) {
			dataString += "null,null,${it[1]}],"
		}
        
	}
	        
	if (dataString == "") {
		def todayDate = new Date()
		if (seriesIndex==1) {
			dataString = "[new Date(todayDate.getTime()),0,null,null],"
		}
		if (seriesIndex==2) {
			dataString = "[new Date(todayDate.getTime()),0,null,null],"
		}
		if (seriesIndex==3) {
			dataString = "[new Date(todayDate.getTime()),null,0,null],"
		}
		if (seriesIndex==4) {
			dataString = "[new Date(todayDate.getTime()),null,null,0],"
		}
	}

//	traceEvent(settings.logFilter,"seriesIndex= $seriesIndex, dataString=$dataString",settings.trace)
    
	return dataString
}



def getGraphHTML() {
	String dataRows  
	def colorMode    
	def mode = state?.currentMode
    
	if (mode=='heat') {
		colorMode='#FF0000'  
		dataRows = "${getDataString(1)}" + "${getDataString(3)}" + "${getDataString(4)}"
	} else {
		mode='cool' 
		colorMode='#269bd2'  
		dataRows = "${getDataString(2)}" + "${getDataString(3)}" + "${getDataString(4)}"
	}    
//	traceEvent(settings.logFilter,"getGraphHTML>mode= ${state?.currentMode}, dataRows=${dataRows}",settings.trace)    
	Date maxDateTime= new Date()
	Date minDateTime= new Date(getStartTime())
	def minDateStr= "new Date(" +  minDateTime.getTime() + ")"
	def maxDateStr= "new Date(" +  maxDateTime.getTime() + ")"

	Date yesterday=maxDateTime -1
	def yesterdayStr= "new Date(" +  yesterday.getTime() + ")"
//	traceEvent(settings.logFilter,"minDataStr=$minDateStr, maxDateStr=$maxDateStr, yesterdayStr=$yesterdayStr",settings.trace)    
   
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
						data.addColumn('number', '${mode}SP');
						data.addColumn('number', 'Ambient');
						data.addColumn('number', 'Humidity');
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
								0: {targetAxisIndex: 0, color: '${colorMode}',lineWidth: 1},
								1: {targetAxisIndex: 0, color: '#f1d801',lineWidth: 1},
								2: {targetAxisIndex: 1, color: '#44b621',lineWidth: 1}
							},
							vAxes: {
								0: {
									title: 'Temperature',
									format: 'decimal',
									textStyle: {color: '${colorMode}'},
									titleTextStyle: {color: '${colorMode}'}
								},
								1: {
									title: 'Humidity(%)',
									format: 'decimal',
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
	  		<h3 style="font-size: 20px; font-weight: bold; text-align: center; background: #ffffff; color: #44b621;">TempVsHumidity</h3>
			<body>
				<button id="change">Change View Window</button>
				<div id="chart_div"></div>
			</body>
		</html>
	"""
	render contentType: "text/html", data: html, status: 200
}

@Field int GLOBAL_LOG_ERROR=1
@Field int GLOBAL_LOG_WARN= 2
@Field int GLOBAL_LOG_INFO=3
@Field int GLOBAL_LOG_DEBUG=4
@Field int GLOBAL_LOG_TRACE=5

def traceEvent(logFilter,message, displayEvent=false, traceLevel=GLOBAL_LOG_DEBUG, sendMessage=true) {
	int filterLevel=(logFilter)?logFilter.toInteger():GLOBAL_LOG_WARN

	if ((displayEvent) || (sendMessage)) {
		def results = [
			name: "verboseTrace",
			value: message,
			displayed: ((displayEvent)?: false)
		]	

		if ((displayEvent) && (filterLevel >= traceLevel)) {
			switch (traceLevel) {
				case GLOBAL_LOG_ERROR:
					log.error "${message}"
				break
				case GLOBAL_LOG_WARN:
					log.warn "${message}"
				break
				case GLOBAL_LOG_INFO:
					log.info  "${message}"
				break
				case GLOBAL_LOG_TRACE:
					log.trace "${message}"
				break
				case GLOBAL_LOG_DEBUG:
				default:
					log.debug "${message}"
				break
			}  /* end switch*/              
		} /* end if displayEvent*/
		if (sendMessage) sendEvent (results)
	}
}