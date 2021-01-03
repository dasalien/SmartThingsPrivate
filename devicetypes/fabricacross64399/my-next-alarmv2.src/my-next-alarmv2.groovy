/***
 *  My Next Alarm
 *  Copyright 2018-2020 Yves Racine
 *  LinkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *  Version 3.0.1
 *  Refer to readme file for installation instructions.
 
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
import groovy.json.*
import groovy.transform.Field

include 'asynchttp_v1'

// for the UI

preferences {

//	Preferences are no longer required when created with the Service Manager (MyNextServiceMgr).

	input("protectId", "text", title: "internal Id", description:
		"The internal protectId\n(not needed when using MyNextServiceMgr, leave it blank)")
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces) or leave it empty (no tracing)")
	input("logFilter", "number",title: "(1=ERROR only,2=<1+WARNING>,3=<2+INFO>,4=<3+DEBUG>,5=<4+TRACE>)",  range: "1..5",
 		description: "optional" )        
}
metadata {
	definition (name: "My Next AlarmV2",  namespace:"fabricacross64399", mnmn: "SmartThingsCommunity", vid: "0d41d6a2-c2fd-3544-92c4-598cc4248a7d",ocfDeviceType: "x.com.st.d.sensor.smoke", author: "yracine") {
		//vid: 0d41d6a2-c2fd-3544-92c4-598cc4248a7d
		capability "Smoke Detector"
		capability "Carbon Monoxide Detector"
		capability "Sensor"
		capability "Battery"  // Not present as a percentage
		capability "Health Check"
		capability "Polling"
		capability "Refresh"
		capability "Presence Sensor"
		capability "Motion Sensor"  // for better motion detection, change the polling interval to 1 minute in MyNextManagerV2.
		capability "fabricacross64399.sethomeoraway"
        
		command "getStructure"        
/*
		setStructure(String attributes) 
		parameters: attributes must be a valid groovy map or json structure
 		see list of structure attributes at https://thingsthataresmart.wiki/index.php?title=My_Next_Alarm#Information 
*/        
		command "setStructure"  
		command "setStructureHome"
		command "setStructureAway"
		command "getProtectInfo"        
//		command "getProtectList"

/*
		setProtectSettings(thermostatId,String thermostatSettings) 
		parameters: protectId (By defaut current protect), protectSettings must be a valid groovy map or json structure
		ex. setProtectSettings("", "{night_light_enable: false}")
 		see list of attributes at https://thingsthataresmart.wiki/index.php?title=My_Next_Tstat#Information                                       
*/
		command "setProtectSettings" 
        
		command "produceSummaryReport"        
		command "save_data_auth"        
//		structure attributes 

		attribute "structure_id","string"
		attribute "st_away","string"
		attribute "st_name","string"
		attribute "st_country_code","string"
		attribute "st_postal_code","string"
		attribute "st_time_zone","string"
//		attribute "st_peak_period_start_time","string"
//		attribute "st_peak_period_end_time","string"
//		attribute "st_eta_trip_id","string"
//		attribute "st_estimated_arrival_window_begin","string"
//		attribute "st_estimated_arrival_window_end","string"
//		attribute "st_eta_begin","string"
//		attribute "st_wwn_security_state","string"        

//		New attributes in V2
		attribute "supportedStructures", "string"	
		attribute "homeOrAway", "string"	
		attribute "st_members", "string"
		attribute "st_user", "string"
		attribute "st_away_timestamp", "string"
		attribute "st_manual_away_timestamp", "string"
		attribute "st_manual_eco_timestamp", "string"
		attribute "st_away_setter", "string"
		attribute "st_eta", "string"
		attribute "st_eta_preconditioning_active", "string"
		attribute "st_eta_unique_id", "string"
		attribute "st_set_manual_eco_all", "string"
		attribute "st_vacation_mode", "string"
		attribute "st_demand_charge_enabled", "string"

		attribute "protectsList","string" 
		attribute "protectId","string"
		attribute "NestAlarmState", "string"
		attribute "alarmState", "string"
		attribute "locale", "string"
		attribute "battery_status","string"        
		attribute "battery_level","string"        
		attribute "software_version","string"
		attribute "where_id","string"
		attribute "where_name","string"
		attribute "label","string"
		attribute "name_long","string"
		attribute "is_online","string"
		attribute "onlineState","string"
		attribute "last_connection","string"
		attribute "last_api_check","string"
		attribute "ui_color_state","string"
		attribute "co_alarm_state","string"
		attribute "smoke_alarm_state","string"
		attribute "last_manual_test_time","string"
		attribute "is_manual_test_active","string"
		attribute "verboseTrace", "string"
		attribute "smoke","string"
		attribute "carbonMonoxide","string"
		attribute "summaryReport", "string"

//		new attributes V2

		attribute "component_smoke_test_passed", "string"
		attribute "component_co_test_passed", "string"
		attribute "component_pir_test_passed", "string"
		attribute "component_speaker_test_passed", "string"
		attribute "component_heat_test_passed", "string"
		attribute "auto_away", "string"
		attribute "auto_away_decision_time_secs", "string"
		attribute "heat_status", "string"
		attribute "night_light_enable", "string"
		attribute "night_light_continuous", "string"
		attribute "night_light_brightness", "string"
		attribute "home_alarm_link_capable", "string"
		attribute "home_alarm_link_connected", "string"
		attribute "home_alarm_link_type", "string"
		attribute "wired_or_battery", "string"
		attribute "capability_level", "string"
		attribute "home_away_input", "string"
		attribute "model", "string"
		attribute "hushed_state", "string"
		attribute "last_connect_time","string"
		attribute "replace_by_date_utc_secs","string"
		attribute "device_born_on_date_utc_secs","string"
         
        
	}


	tiles (scale: 2){
		multiAttributeTile(name:"smoke", type: "generic", width: 6, height: 4) {
			tileAttribute ("device.alarmState", key: "PRIMARY_CONTROL", backgroundColor:getBackgroundColors()) {
				attributeState("clear", label:"clear", icon:"st.alarm.smoke.clear", backgroundColor:"#44b621",defaultState: true)  
				attributeState("smoke", label:"SMOKE", icon:"st.alarm.smoke.smoke", backgroundColor:"#e86d13")
				attributeState("carbonMonoxide", label:"MONOXIDE", icon:"st.alarm.carbon-monoxide.carbon-monoxide", backgroundColor:"#e86d13")
				attributeState("tested", label:"TEST", icon:"st.alarm.smoke.test", backgroundColor:"#e86d13")
			}
			tileAttribute("device.onlineState", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'${currentValue}')
			}
            
		}
		standardTile("refresh", "device.battery_status", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {
			state "default", label: 'Refresh',action: "refresh", icon:"st.secondary.refresh", 			
			backgroundColor: "#ffffff"
		}
		standardTile("NestAlarmState", "device.NestAlarmState", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {        
			state("clear", label:"clear", backgroundColor:"#44b621", icon:"st.alarm.smoke.clear")
			state("warning_smoke", label:  "Smoke\nWARNING", backgroundColor:"#f1d801",icon:"st.alarm.smoke.smoke")
			state("emergency_smoke", label:"Smoke\nURGENT", backgroundColor:"#bc2323",icon:"st.alarm.smoke.smoke")
			state("warning_co", label:  "CO\nWARNING", backgroundColor:"#f1d801",icon:"st.alarm.carbon-monoxide.carbon-monoxide")
			state("emergency_co", label:"CO\nURGENT", backgroundColor:"#bc2323", icon:"st.alarm.carbon-monoxide.carbon-monoxide")
			backgroundColor: "#ffffff"
		}
		standardTile("WiredOrBattery", "device.wired_or_battery", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {        
			state("0", label:"wired", backgroundColor:"#ffffff", icon:"st.alarm.smoke.clear")
			state("default", label:"battery", backgroundColor:"#ffffff", icon:"st.alarm.smoke.clear")
		}            
		valueTile("battery", "device.battery_status", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "default", label:'Battery ${currentValue}'
		}
		valueTile(	"lastManualTest", "device.last_manual_test_time",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
				state("default",
				label:'Manual Test ${currentValue}',
				backgroundColor: "#ffffff",
			)
		}
        
 		valueTile(	"lastConnection", "device.last_connection",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
			state("default",
				label:'LastConnect ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
		valueTile(	"lastAPICheck", "device.last_api_check",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
 			state("default",
				label:'LastAPICheck ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
  		valueTile(	"swVersion", "device.software_version",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
			state("default",
				label:'swVersion ${currentValue}',
				backgroundColor: "#ffffff",
			)
		}
		standardTile("motion", "device.motion", width: 2, height: 2) {
			state("active", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#00a0dc")
			state("inactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff", defaultState:true)
		}
            
	
		main "smoke"
		details(["smoke",
			"NestAlarmState",        
			"WiredOrBattery",
			"battery",            
			"lastManualTest",
			"lastConnection",
			"lastAPICheck",
			"motion",            
			"swVersion",            
			"refresh"
			])
	}
}
def getBackgroundColors() {
//	if (data?.protects[0]?.ui_color_state) {
//		return data?.protects[0]?.ui_color_state    
//	} else {
		return "#ffffff"    
//	}    
}



void installed() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "cloud", scheme:"untracked"]), displayed: false)
	state?.scale=getTemperatureScale() 
	if (settings.trace) { 
		log.debug("installed>$device.displayName installed with settings: ${settings.inspect()} and state variables= ${state.inspect()}")
	}
	state?.redirectURL=null
	state?.retriesCounter=0        
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
	state?.retriesCounter=0       
	state?.redirectURLcount=0            
	state?.redirectURL=null
	state?.scale=getTemperatureScale() 
	traceEvent(settings.logFilter,"updated>$device.displayName updated with settings: ${settings.inspect()} and state variables= ${state.inspect()}", settings.trace)
      
}

//remove from the selected devices list in Service Manager
void uninstalled() {
	traceEvent(settings.logFilter, "executing uninstalled for ${this.device.displayName}", settings.trace)
	parent.purgeChildDevice(this)    
}

// handle commands

void setHomeOrAway(String awayOrHome) {
	if (awayOrHome.toUpperCase() == 'AWAY') {
		away()    
	} else {
		present()    
	}    
}    


void present() {
	setStructureHome()
  
}
void away() {
	setStructureAway()
}
void home() {
	setStructureHome()    
}
void setStructureAway() {

	double away_timestamp=(now() / 1000) // epoch in seconds
	long timestamp= (double)away_timestamp.intValue()    
	traceEvent(settings.logFilter,"setStructureAway>away_timestamp=$away_timestamp, timestamp=$timestamp", settings.trace)
	setStructure([away: true, away_setter:0, away_timestamp: away_timestamp.intValue()])
	sendEvent(name: 'homeOrAway', value: 'Away',isStateChange:true)
	    
}



void setStructureHome() {

	setStructure([away: false])
	sendEvent(name: 'homeOrAway', value: 'Home',isStateChange:true)
    
}


// settings can be anything supported by Nest at the ST community wiki for MyNextTstat
void setStructure(String structureSettings) {
	def settings=[:]
	try {
		settings = new groovy.json.JsonSlurper().parseText(structureSettings)   
		traceEvent(settings.logFilter,"setThermostatSettings>was able to convert json settings  to a map",settings.trace, GLOBAL_LOG_INFO)
	} catch (e) {
		traceEvent(settings.logFilter,"setThermostatSettings>was not able to convert settings {$structureSettings) to a map, not a valid json map",settings.trace, GLOBAL_LOG_ERROR)
		return
	}
//	call the corresponding method with the map  
	setStructure(settings)    
    
}



void setStructure(attributes = []) {
	def NEST_SUCCESS=200
	def TOKEN_EXPIRED=401    
	def REDIRECT_ERROR=307    
	def BLOCKED=429
	def interval=1*60    
	
   	def structureId= device.currentValue("structure_id")	    
	traceEvent(settings.logFilter,"setStructure>called with values ${attributes} for ${structureId}",settings.trace)
//	log.debug("setStructure>called with values ${attributes} for ${structureId}")
	if (attributes == null || attributes == "" || attributes == [] ) {
		traceEvent(settings.logFilter, "setStructure>attribute set is empty, exiting", settings.trace)
		return        
	}
	def bodyReq = [object_key:"structure.${structureId}",op:"MERGE",value:attributes]    
	int statusCode
	def exceptionCheck  
	api('setStructure', structureId, bodyReq) {resp ->
		statusCode = resp?.status
		state?.lastStatusCodeForStructure=statusCode				                
		exceptionCheck=device.currentValue("verboseTrace")
		if (statusCode == NEST_SUCCESS) {
			/* when success, reset the exception counter */
			state.exceptionCount=0
			if ((data?."replayStructureId${state?.retriesStructureCounter}" == null) ||
				(state?.retriesStructureCounter > get_MAX_SETTER_RETRIES())) {          // reset the counter if last entry is null
				reset_replay_data('Structure')                
				state?.retriesStructureCounter=0
			}            
			traceEvent(settings.logFilter,"setStructure>done for ${structureId}", settings.trace)
			runIn(1*60, "refresh_structure_async", [overwrite:true])				                
		} else {
			traceEvent(settings.logFilter,"setStructure> error=${statusCode.toString()} for ${structureId}", true, GLOBAL_LOG_ERROR)
		} /* end if statusCode */
	} /* end api call */                
	if (exceptionCheck?.contains("exception")) {
		sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		traceEvent(settings.logFilter,"setStructure>exception=${exceptionCheck}", true, GLOBAL_LOG_ERROR)
	}                
	if ((statusCode == BLOCKED) ||
		(exceptionCheck?.contains("Nest response error")) || 
		(exceptionCheck?.contains("ConnectTimeoutException"))) {
		state?.retriesStructureCounter=(state?.retriesStructureCounter?:0)+1        
		if (!(interval=get_exception_interval_delay( state?.retriesStructureCounter))) {  
			traceEvent(settings.logFilter,"setStructure>too many retries", true, GLOBAL_LOG_ERROR)
			state?.retriesStructureCounter=0            
			reset_replay_data('Structure')                
			return    
		}            
		state.lastPollTimestamp = (statusCode==BLOCKED) ? (now() + (interval * 1000)):(now() + (1 * 60 * 1000)) 
		data?."replayStructureAttributes${state?.retriesStructureCounter}"=attributes
		data?."replayStructureId${state?.retriesStructureCounter}"=structureId        
		traceEvent(settings.logFilter,"setStructure>about to call setStructureReplay,interval=$interval,retries counter=${state?.retriesStructureCounter}", true, GLOBAL_LOG_INFO)
		runIn(interval, "setStructureReplay", [overwrite:true])              
	}    
}



void setStructureReplay() {
	def exceptionCheck=""

	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) {
		def structureId = data?."replayStructureId$i"
		if (structureId == null) continue  // already processed        
		def attributes = data?."replayStructureAttributes$i"
		def poll_interval=1 
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		traceEvent(settings.logFilter,"setStructureReplay>about to call setStructure,retries counter=$i", true, GLOBAL_LOG_INFO)
		setStructure(structureId,attributes) 
		exceptionCheck=device.currentValue("verboseTrace")
		if (exceptionCheck?.contains("done")) {
			data?."replayStructureId$i"=null        
		} /* end if */
	} /* end for */
	if (exceptionCheck?.contains("done")) { // if last command executed OK, then reset the counter
		reset_replay_data('Structure')                
		state?.retriesStructureCounter=0
	} 
    
}    
    
void refresh_structure_async() {
	getStructure(false) 	// force update of the local cache            
}



// parse events into attributes
def parse(String description) {

}

// protectId		single protectId 
private def refresh_protect(protectId="") {
	def todayDay = new Date().format("dd",location.timeZone)
	def structure
	protectId=determine_protect_id(protectId)    
	def scale = getTemperatureScale()
	state?.scale= scale    

	if (!data?.protects) {
		data?.protects=[]
	}        
	if ((!state?.today) || (state?.today != todayDay))  {
		traceEvent(settings.logFilter,"refresh_protect>about to call parent.getStructures(), type=where", detailedNotif)
		parent.getStructures(false, cache_timeout,'["where"]')
		traceEvent(settings.logFilter,"refresh_protect>about to call parent.getStructures(), type=buckets", detailedNotif)
		parent.getStructures(false, cache_timeout,'["buckets"]')
		state?.today=todayDay        
	}  

   
	def dataEvents = [
		protectId:  data?.protects[0]?.id,
		structure_id:  data?.protects[0]?.structure_id,
 		protectName:data?.protects[0]?.name,
		alarmState:((data?.protects[0]?.alarm_state in ['warning','emergency']) ? 'smoke': 
			(data?.protects[0]?.co_state in ['warning','emergency'])? 'carbonMonoxide' : 'clear'),
		NestAlarmState:((data?.protects[0]?.alarm_state in ['warning','emergency']) ? data?.protects[0]?.alarm_state + '_smoke': 
			(data?.protects[0]?.co_state in ['warning','emergency'])? data?.protects[0]?.co_state + '_co' : 'clear'),
		onlineState:(data?.protects[0]?.is_online?.toString()=='true')?'Online' : 'Offline',
		"battery_status":data?.protects[0]?.battery_health_state,        
		"battery_level":data?.protects[0]?.battery_level,        
		battery: (data?.protects[0]?.wired_or_battery)? getBatteryUsage(): 100,		 // evaluated, not precise       
//		"is_manual_test_active": data?.protects[0]?.is_manual_test_active,
		"last_manual_test_time": (data?.protects[0]?.last_manual_test_time)? formatTimeInTimezone((data?.protects[0]?.last_manual_test_time*1000))?.substring(0,16):"",
		"locale": data?.protects[0]?.device_locale,
		"software_version": data?.protects[0]?.software_version,
		"where_id": data?.protects[0]?.where_id,
		"where_name": data?.protects[0]?.where_name,
//		"label": data?.protects[0]?.label,
		"name_long":data?.protects[0]?.long_name,
		"is_online": data?.protects[0]?.is_online.toString().minus('[').minus(']'),
		"last_api_check": formatTimeInTimezone(now())?.substring(0,16),
		"last_connect_time": data?.protects[0]?.last_connection,
		"last_connection": (data?.protects[0]?.last_connection)? formatTimeInTimezone(data?.protects[0]?.last_connection)?.substring(0,16):"",
		"smoke": ((data?.protects[0]?.alarm_state in ['warning','emergency']) ? 'detected' : 'clear'),
		"smoke_alarm_state": data?.protects[0]?.alarm_state,
		"carbonMonoxide": ((data?.protects[0]?.co_state in ['warning','emergency']) ? 'detected' : 'clear'),
		"co_alarm_state": data?.protects[0]?.co_state,
//		"ui_color_state":data?.protects[0]?.ui_color_state,
		"component_smoke_test_passed": data?.protects[0]?.component_smoke_test_passed,
		"component_co_test_passed": data?.protects[0]?.component_co_test_passed,
		"component_pir_test_passed": data?.protects[0]?.component_pir_test_passed,
		"component_speaker_test_passed": data?.protects[0]?.component_speaker_test_passed,
		"component_heat_test_passed": data?.protects[0]?.component_heat_test_passed,
		"auto_away": data?.protects[0]?.auto_away,
		"auto_away_decision_time_secs": data?.protects[0]?.auto_away_decision_time_secs,
		"motion": (data?.protects[0]?.auto_away) ? 'inactive' : 'active',        
		"heat_status": data?.protects[0]?.heat_status,
		"night_light_enable": data?.protects[0]?.night_light_enable,
		"night_light_continuous": data?.protects[0]?.night_light_continuous,
		"night_light_enable": data?.protects[0]?.night_light_enable,
		"night_light_brightness": data?.protects[0]?.night_light_brightness,
		"home_alarm_link_capable": data?.protects[0]?.home_alarm_link_capable,
		"home_alarm_link_connected": data?.protects[0]?.home_alarm_link_connected,
		"home_alarm_link_type": data?.protects[0]?.home_alarm_link_type,
		"wired_or_battery": data?.protects[0]?.wired_or_battery,
		"capability_level": data?.protects[0]?.capability_level,
		"home_away_input": data?.protects[0]?.home_away_input,
		"model": data?.protects[0]?.model,
		"hushed_state": data?.protects[0]?.hushed_state,
		"replace_by_date_utc_secs":data?.protects[0]?.replace_by_date_utc_secs,
		"device_born_on_date_utc_secs":data?.protects[0]?.device_born_on_date_utc_secs,
		"supportedStructures": "[Away,Home]"
 	]
	if (dataEvents.alarmState=='clear' && dataEvents.is_manual_test_active.toString()=='true') {
		dataEvents.alarmState='tested'    
	}    
	generateEvent(dataEvents)   
	traceEvent(settings.logFilter, "refresh_protect>about to call getStructure()")
	structure=getStructure(true) 
	if (structure) {
		traceEvent(settings.logFilter, "refresh_protect>structure name= ${structure?.name}")
		dataEvents= [
			"st_away": structure?.away,
			"homeOrAway": ((structure?.away) ? 'Away' : 'Home'),            
			"st_name":structure?.name,
			"st_country_code": structure?.country_code,
			"st_postal_code":structure?.postal_code,
//			"st_peak_period_start_time":(structure?.peak_period_start_time)?formatDateInLocalTime(structure?.peak_period_start_time)?.substring(0,16):"",
//			"st_peak_period_end_time":(structure?.peak_period_end_time)?formatDateInLocalTime(structure?.peak_period_end_time)?.substring(0,16):"",
			"st_time_zone":structure?.time_zone,
//			"st_eta_begin":(structure?.eta_begin)?formatDateInLocalTime(structure?.eta_begin)?.substring(0,16):"",
//			"st_wwn_security_state": structure?.wwn_security_state
//			"st_members": structure?.st_members,
			"st_user": structure?.user,
			"st_away_timestamp": structure?.away_timestamp,
			"st_manual_away_timestamp":structure?.manual_away_timestamp,
			"st_manual_eco_timestamp":structure?.manual_eco_timestamp,
			"st_away_setter":structure?.away_setter,
			"st_eta":structure?.eta,
			"st_eta_preconditioning_active":structure?.eta_preconditioning_active,
			"st_eta_unique_id":structure?.eta_unique_id,
			"st_set_manual_eco_all":structure?.set_manual_eco_all,
			"st_vacation_mode":structure?.vacation_mode,
			"st_demand_charge_enabled":structure?.demand_charge_enabled
		]
		if (dataEvents?.st_away.toString() == 'true') { 
			dataEvents?.presence= "not present"
		} else {        
			dataEvents?.presence= "present"
		}            
        
		generateEvent(dataEvents)        
        
	}    
	traceEvent(settings.logFilter,"refresh_protect>done for protectId =${protectId}", settings.trace)
    
}

// refresh() has a different polling interval as it is called by lastPollTimestampthe UI (contrary to poll).
void refresh() {
	def protectId= determine_protect_id("") 	    
	def poll_interval=0.5  // set a 30 sec. poll interval to avoid unecessary load on Nest servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"refresh>protectId = ${protectId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), not refreshing data...",
			settings.trace)	            
		return
	}
	state.lastPollTimestamp = now()    
	getProtectInfo(protectId, false)
	refresh_protect(protectId)
  
}


void poll() {
   
	def protectId= determine_protect_id("") 	    

	def poll_interval=1   // set a minimum of 1min. poll interval to avoid unecessary load on Nest servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"poll>protectId = ${protectId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), not refreshing data...",
			settings.trace, GLOBAL_LOG_INFO)            
		return
	}
	getProtectInfo(protectId, true)   
	refresh_protect(protectId)    
	traceEvent(settings.logFilter,"poll>done for protectId =${protectId}", settings.trace)

}
private def getBatteryUsage() {  // this is estimated as voltage is not available
	float nominal_voltage=5400
	double pct_battery

	def battery_level=device.currentValue("battery_level")
	def battery_status=device.currentValue("battery_status")
	if (battery_level) {
		pct_battery=((battery_level.toFloat()/nominal_voltage) * 100).round(0)
	} else {
		pct_battery=(battery_status=='ok')? 80 : (battery_status=='low')? 40 : 15
	}        
	return (pct_battery.intValue())    
}


private void generateEvent(Map results) {
	traceEvent(settings.logFilter,"generateEvent>parsing data $results", settings.trace)
    
	state?.scale = getTemperatureScale() // make sure to display in the right scale
	def scale = state?.scale
	if (results) {
		results.each { name, value ->
			def isDisplayed = true

			String upperFieldName=name.toUpperCase()    

// 			Temperature variable names for display contain 'display'            

			if ((upperFieldName?.contains("_STATE")) || (upperFieldName?.contains("_ACTIVE")) || 
				(upperFieldName?.contains("_ENABLED")) || (upperFieldName?.contains("HAS_"))) { 
				String valueString=value.toString()                
				value= (value && valueString != 'null') ? value : false            
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), displayed: isDisplayed)                                     									 
 			} else if (((upperFieldName.contains("HUMIDITY")) || (upperFieldName == "BATTERY"))) {
				value=(value?:0)
 				double humValue = value?.toDouble().round(0)
				String humValueString = String.format('%2d', humValue.intValue())
				def isChange = isStateChange(device, name, humValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: humValueString, unit: "%", displayed: isDisplayed, isStateChange: isChange)
			} else if (upperFieldName?.contains("DATA")) { // data variable names contain 'data'

				sendEvent(name: name, value: value, displayed: (settings.trace?:false))                                     									 

			} else if (value != null && value.toString() != 'null' && value.toString() != '[:]' && value.toString() != '[]' && value.toString() != '[null]') {           
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), isStateChange: isChange, displayed: isDisplayed)       
			}
		}
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


private void api( method, id, args=null, success = {}) {
	def MAX_EXCEPTION_COUNT=20
	String URI_ROOT = "${get_API_URI_ROOT()}"
 	def TOKEN_EXPIRED=401
    
  
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
		'setProtectSettings':
			[uri: "${URI_ROOT}/${get_API_VERSION()}/put", type: 'put'],
		'setStructure':
			[uri: "${URI_ROOT}/${get_API_VERSION()}/put", type: 'put'],
		]
	def request = methods.getAt(method)
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

// Need to be authenticated in before this is called. So don't call this. Call api.
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
			parent.refreshAllChildAuthTokens()  
		}    	
	}
}

void produceSummaryReport(pastDaysCount) {
	traceEvent(settings.logFilter,"produceSummaryReport>begin",settings.trace, GLOBAL_LOG_TRACE)
	def countEvents, countTested, countSmokeWarnings,countCoWarnings,countCoEmergencies,countSmokeEmergencies,countBatteryEvents
	boolean found_values=false
	Date todayDate = new Date()
	Date startOfPeriod = todayDate - pastDaysCount
	long min_timestamp,max_timestamp

	def events = device.statesSince("NestAlarmState", startOfPeriod, [max:200])
	def testEvents = device.statesSince("last_manual_test_time", startOfPeriod, [max:200])
	def batteryEvents = device.statesSince("battery_status", startOfPeriod, [max:200])
	def currentBatteryState= device.currentValue("battery_status")
	def event_with_min_timestamp, event_with_max_timestamp    
	if (events) {    
		countEvents =  events.count{it}
		countSmokeWarnings =  events.count{it?.value.toString()=='warning_smoke'}
		countCoWarnings =  events.count{it?.value.toString()=='warning_co'}
		countSmokeEmergencies =  events.count{it?.value.toString()=='emergency_smoke'}
		countCoEmergencies =  events.count{it?.value.toString()=='emergency_co'}
		event_with_min_timestamp=events.min{it?.date.getTime()}  		        
		event_with_max_timestamp=events.max{it?.date.getTime()}		        
		max_timestamp= (event_with_max_timestamp) ? event_with_max_timestamp.date.getTime() : null
		min_timestamp= (event_with_min_timestamp) ? event_with_min_timestamp.date.getTime() : null
        
		found_values=true        
	}
	if (testEvents) {    
		countTested =  testEvents.count{it?.value=='true'}
		found_values=true        
	}    
	if (batteryEvents) {
		countBatteryEvents = batteryEvents.count{it} 
		found_values=true        
	}    
    
 
	if (!found_values) {
		traceEvent(settings.logFilter,"produceSummaryReport>found no values for report,exiting",settings.trace)
		sendEvent(name: "summaryReport", value: "")
		return        
	}    
	String roomName =device.currentValue("where_name")
	String scale=getTemperatureScale(), unitScale='Farenheit',timePeriod="In the past ${pastDaysCount} days"
//	def struct_HomeAwayMode= device.currentValue("st_away")
	if (scale=='C') { 
		unitScale='Celsius'    
	}    
	if (pastDaysCount <2) {
		timePeriod="In the past day"    
	}    
//	String stName=device.currentValue("st_name")    
	String summary_report = "At your home" 
	summary_report=summary_report + "${timePeriod}"    
	if (roomName) {	
		summary_report= summary_report + ",in the room ${roomName} where the Nest Protect ${device.displayName} is located"
	} else {
    
		summary_report= summary_report + ",at the Nest Protect ${device.displayName},"
	}    
	if (countEvents) {
		summary_report= summary_report + ",there were $countEvents event(s) triggered by the Nest Protect, which include the following" 
	}
	if (countTested) {
		summary_report= summary_report + ",$countTested test event(s) were recorded" 
	}
	if (countCoWarnings) {
		summary_report= summary_report + ",$countCoWarnings carbon monoxide warning event(s)" 
	}
	if (countCoEmergencies) {
		summary_report= summary_report + ",$countCoEmergencies carbon monoxide emergency event(s)" 
	}
	if (countSmokeWarnings) {
		summary_report= summary_report + ",$countSmokeWarnings smoke warning event(s)" 
	}
	if (countSmokeEmergencies) {
		summary_report= summary_report + ",$countSmokeEmergencies smoke emergency event(s)" 
	}
    
	if (countBatteryEvents) {
		summary_report= summary_report + ",$countBatteryEvents battery event(s).The current battery state is ${currentBatteryState}" 
	}
    
	if ((min_timestamp != max_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(min_timestamp)					    
		summary_report= summary_report + ".The Protect's earliest event recorded (${event_with_min_timestamp.value}) was on ${timeInLocalTime.substring(0,16)}" 
	}
	if ((min_timestamp != max_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(max_timestamp)					    
		summary_report= summary_report + ".The Protect's last event recorded (${event_with_max_timestamp.value}) was on ${timeInLocalTime.substring(0,16)}" 
	}
    

	sendEvent(name: "summaryReport", value: summary_report, isStateChange: true)
    
	traceEvent(settings.logFilter,"produceSummaryReport>end",settings.trace, GLOBAL_LOG_TRACE)

}
def getStructure(useCache=true) {
	def structure=[:]
	def structure_id=device.currentValue("structure_id")
	if (structure_id) {    
		parent.getObject(structure_id,"structure",useCache)
		parent.updateStructure(this, structure_id)   
		structure=data?.structure[0]
	}        
	return structure    
    
}


void updateChildStructureData(objects) {
	traceEvent(settings.logFilter,"updateChildStructureData>objects from parent=$objects",settings.trace,GLOBAL_LOG_TRACE)        
	if (!data?.structure) {
		data?.structure=[]    
	}    
	data?.structure=objects
	traceEvent(settings.logFilter,"updateChildStructureData>data?.structure=${data?.structure}",settings.trace,GLOBAL_LOG_TRACE)        
}



void updateChildData(objects) {
	traceEvent(settings.logFilter,"updateChildData>objects from parent=$objects",settings.trace,GLOBAL_LOG_TRACE)        
	if (!data?.protects) {
		data?.protects=[]    
	}    
	data?.protects=objects
	traceEvent(settings.logFilter,"updateChildData>data.protects=${data.protects}",settings.trace,GLOBAL_LOG_TRACE)        
}

 //
//	if no protectId is provided, it is defaulted to the current protectId 
void getProtectInfo(protectId, useCache=true) {
  
	protectId= determine_protect_id(protectId)	
	parent.getObject(protectId,"protect",useCache)
	parent.updateObjects(this, "protect",protectId)  
}


void getProtectInfoReplay() {
	def id = data?.replayId
	def poll_interval=1 
	state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
	traceEvent(settings.logFilter,"getProtectInfoReplay>about to call getProtectInfo for ${id}",settings.trace, GLOBAL_LOG_INFO)
	getProtectInfo(id) 
}    
 

// protectId may be a list of serial# separated by ",", no spaces (ex. '123456789012,123456789013') 
//	if no protectId is provided, it is defaulted to the current protectId 
// settings can be anything supported by Nest at https://developers.nest.com/documentation/cloud/api-Protect
void setProtectSettings(protectId,String protectSettings) {
	def settings=[:]
	try {
		settings = new groovy.json.JsonSlurper().parseText(protectSettings)   
		traceEvent(settings.logFilter,"setProtectSettings>was able to convert settings to a map",settings.trace, GLOBAL_LOG_INFO)
	} catch (e) {
		traceEvent(settings.logFilter,"setProtectSettings>was not able to convert settings ($protectSettings) to a map, not a valid json map",settings.trace, GLOBAL_LOG_ERROR)
		return
	}
//	call the corresponding method with the map  
	setProtectSettings(protectId, settings)    
    
}

// protectId may be a list of serial# separated by ",", no spaces (ex. '123456789012,123456789013') 
//	if no protectId is provided, it is defaulted to the current protectId 
// settings can be anything supported by Nest at https://developers.nest.com/documentation/cloud/api-Protect
void setProtectSettings(protectId,protectSettings = []) {
	def TOKEN_EXPIRED=401    
	def REDIRECT_ERROR=307    
	def BLOCKED=429
	
	def interval=1 * 60
    
   	protectId= determine_protect_id(protectId) 	    
	if (state?.lastStatusCodeForSettings==BLOCKED) {    
		def poll_interval=1  // set a minimum of 1 min interval to avoid unecessary load on Nest servers
		def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
		if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
			traceEvent(settings.logFilter,"setProtectSettings>protectId = ${protectId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), throttling in progress, command not being processed",
				settings.trace, GLOBAL_LOG_ERROR)            
			return
		}
	}
	traceEvent(settings.logFilter,"setProtectSettings>called with values ${tstatSettings} for ${protectId}",settings.trace)
	if (protectSettings == null || protectSettings == "" || protectSettings == [] ) {
		traceEvent(settings.logFilter, "setProtectSettings>protectSettings set is empty, exiting", settings.trace)
		return        
	}
	bodyReq = [object_key:"topaz.${protectId}",op:"MERGE",value:protectSettings]    
	int statusCode
	def exceptionCheck    
	api('setProtectSettings', protectId, bodyReq) {resp ->
		statusCode = resp?.status
		state?.lastStatusCodeForSettings=statusCode				                
		if (statusCode== REDIRECT_ERROR) {
			if (!process_redirectURL( resp?.headers.Location)) {
				traceEvent(settings.logFilter,"setStructure>Nest redirect: too many redirects, count =${state?.redirectURLcount}", true, GLOBAL_LOG_ERROR)
				return                
			}
			traceEvent(settings.logFilter,"setProtectSettings>Nest redirect: about to call setProtectSettings again, count =${state?.redirectURLcount}", true)
			doRequest( resp?.headers.Location, bodyReq, 'put') {redirectResp->
				statusCode=redirectResp?.status            
			}            
			return                
		}		    
		if (statusCode==BLOCKED) {
			traceEvent(settings.logFilter,"setProtectSettings>protectId=${protectId},Nest throttling in progress, error=$statusCode", settings.trace, GLOBAL_LOG_ERROR)
			interval=1 * 60   // set a minimum of 1min. interval to avoid unecessary load on Nest servers
		}			
		if (statusCode==TOKEN_EXPIRED) {
			traceEvent(settings.logFilter,"setProtectSettings>protectId=${protectId},error $statusCode, need to re-login at Nest", settings.trace, GLOBAL_LOG_WARN)
			return            
		}
		exceptionCheck=device.currentValue("verboseTrace")
		if (statusCode == NEST_SUCCESS) {
			/* when success, reset the exception counter */
			state.exceptionCount=0
			state?.redirectURLcount=0   
			if ((data?."replaySettingsId${state?.retriesSettingsCounter}" == null) ||
				(state?.retriesSettingsCounter > get_MAX_SETTER_RETRIES())) {          // reset the counter if last entry is null
				reset_replay_data('Settings')                
				state?.retriesSettingsCounter=0
			}            
			traceEvent(settings.logFilter,"setProtectSettings>done for ${protectId}", settings.trace)
		} else {
			traceEvent(settings.logFilter,"setProtectSettings> error=${statusCode.toString()} for ${protectId}", true, GLOBAL_LOG_ERROR)
		} /* end if statusCode */
	} /* end api call */                
	if (exceptionCheck?.contains("exception")) {
		sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		traceEvent(settings.logFilter,"setProtectSettings>exception=${exceptionCheck}", true, GLOBAL_LOG_ERROR)
	}                
	if ((statusCode == BLOCKED) ||
		(exceptionCheck?.contains("ConnectTimeoutException"))) {
		state?.retriesSettingsCounter=(state?.retriesSettingsCounter?:0)+1            
		interval=1*60 * state?.retriesSettingsCounter // the interval delay will increase if multiple retries have already been made
		if (!(interval= get_exception_interval_delay(state?.retriesSettingsCounter))) {   
			traceEvent(settings.logFilter,"setProtectSettings>too many retries", true, GLOBAL_LOG_ERROR)
			reset_replay_data('Settings')
			return        
		}        
		state.lastPollTimestamp = (statusCode==BLOCKED) ? (now() + (interval * 1000)):(now() + (1 * 60 * 1000)) 
		data?."replaySettingsId${state?.retriesSettingsCounter}"=protectId
		data?."replaySettings${state?.retriesSettingsCounter}"=protectSettings    
		traceEvent(settings.logFilter,"setProtectSettings>about to call setProtectSettingsReplay,retries counter=${state?.retriesSettingsCounter}", true, GLOBAL_LOG_INFO)
		runIn(interval, "setProtectSettingsReplay", [overwrite: true])          
	}    
    
}

void setProtectSettingsReplay() {
	def exceptionCheck=""

	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) {
		def protectId = data?."replaySettingsId$i"
		if (id == null) continue  // already processed        
		def protectSettings = data?."replaySettings$i"
		def poll_interval=1 
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		setProtectSettings(protectId,protectSettings) 
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
	data?.auth?.nest_czfe_url = auth?.nest_czfe_url    
	data?.auth?.token_type =   "Bearer" 
//	data?.auth?.token_type =   auth.token_type 
//	data?.auth?.scope = auth?.scope
	data?.auth?.authexptime = auth.authexptime
	traceEvent(settings.logFilter,"save_data_auth>saved data.auth=$data.auth")
}

private void save_redirectURL(redirectURL) {
	state?.redirectURL=redirectURL  // save the redirect location for further call purposes
	traceEvent(settings.logFilter,"save_redirectURL>${state?.redirectURL}",settings.trace)  
}


private def isLoggedIn() {
	if (data?.auth?.access_token == null) {
		traceEvent(settings.logFilter,"isLoggedIn> no data auth", settings.trace,GLOBAL_LOG_TRACE)
		return false
	} 
	return true
}

private def isTokenExpired() {
	def buffer_time_expiration=5 // set a 5 min. buffer time 
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

// Determine id from settings or initalSetup
private def determine_protect_id(protect_id) {
	def protectId=device.currentValue("protectId")
    
	if ((protect_id != null) && (protect_id != "")) {
		protectId = protect_id
	} else if ((settings.protectId != null) && (settings.protectId  != "")) {
		protectId = settings.protectId.trim()
		traceEvent(settings.logFilter,"determine_protect_id>protectId = ${settings.protectId}", settings.trace)
	} else if (data?.auth?.protectId) {
		settings.appKey = data.auth.appKey
		settings.protectId = data.auth.protectId
		protectId=data.auth.protectId
		traceEvent(settings.logFilter,"determine_protect_id>protectId from data.auth= ${data.auth.protectId}",settings.trace)
	} else if ((protectId !=null) && (protectId != "")) {
		settings.protectId = protectId
		traceEvent(settings.logFilter,"determine_protect_id> protectId from device= ${protectId}",settings.trace)
	}
    
	if ((protect_id != "") && (protectId && protect_id != protectId)) {
		sendEvent(name: "protectId", displayed: (settings.trace?:false),value: protectdId)    
	}
	return protectId
}

// Get the appKey for authentication
private def get_appKey() {
	return data?.auth?.appKey    
    
}    

// @Get the privateKey for authentication
private def get_privateKey() {
	
	return data?.auth?.privateKey
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


// Called by MyNextServiceMgr for initial creation of a child Device
void initialSetup(auth_data,device_protect_id) {
	settings.trace=true
	settings?.logFilter=5
    
	traceEvent(settings.logFilter,"initialSetup>begin",settings.trace)
	log.debug "initialSetup>begin"
//	log.debug "initialSetup> structure_id = ${structure_id}"
	log.debug "initialSetup> device_protect_id = ${device_protect_id}"
//	log.debug "initialSetup> device_client_id = ${device_client_id}"
//	log.debug "initialSetup> private_key_id = ${private_key_id}"

//	traceEvent(settings.logFilter,"initialSetup> structure_id = ${structure_id}",settings.trace)
	traceEvent(settings.logFilter,"initialSetup> device_protect_id = ${device_protect_id}",settings.trace)
//	traceEvent(settings.logFilter,"initialSetup> device_client_id = ${device_client_id}",settings.trace)
//	traceEvent(settings.logFilter,"initialSetup> private_key_id = ${private_key_id}",settings.trace)
//	settings?.structureId = structure_id
	settings?.protectId = device_protect_id
	sendEvent(name: "protectId", value:device_protect_id,  displayed: (settings.trace?: false))    
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

	runIn(1*60,"refresh")
	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()
    
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