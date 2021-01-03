/***
 *  My Flair Thermostat 
 *     Note: The thermostat is read only, it does not have any Actuator capabilities (no setters)
 *  Copyright 2017-2020 Yves Racine
 *  LinkedIn profile: www.linkedin.com/in/yracine
 *  Version v1.2
 *  Refer to readme file for installation instructions.
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

// for the UI

preferences {

	//	Preferences are optional as the Service Manager (MyFlairServiceMgr) will populate them

	input("structureId", "text", title: "StructureId", description:
		"The structureId of your location\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("thermostatId", "text", title: "Serial #", description:
		"The id of your thermostat\n(not needed when using MyFlairServiceMgr, leave it blank)")
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
													 
	definition(name: "Flair Thermostat V2", author: "Yves Racine", mnmn: "SmartThingsCommunity", vid: "d7156c31-fc97-3b19-af8b-2dba5b7c84cb", ocfDeviceType:"oic.d.thermostat", namespace: "acrosscable12814") {
    //SmartThings-smartthings-Z-Wave_Thermostat
//		capability "thermostat"
		capability "thermostatHeatingSetpoint"
		capability "thermostatCoolingSetpoint"
		//capability "thermostatSetpoint"
		capability "thermostatMode"
		capability "thermostatFanMode"
		capability "thermostatOperatingState"        
		capability "Relative Humidity Measurement"
		capability "Temperature Measurement"
		capability "acrosscable12814.thermostatSetpoint"
        capability "acrosscable12814.flairRoom"
		capability "acrosscable12814.flairZone"
		capability "acrosscable12814.flairStatus"
		capability "acrosscable12814.flairThermostatName"
		capability "acrosscable12814.flairThermostatMake"
		capability "acrosscable12814.flairThermostatModel"
		capability "acrosscable12814.flairThermostatCapabilities"

		capability "Polling"
		capability "Refresh"
		capability "Health Check"
		capability "Sensor"

		attribute "structureId", "string"
		attribute "thermostatId", "string"
		attribute "thermostatSourceId", "string"
		attribute "thermostatName", "string"
		attribute "thermostatMake", "string"
		attribute "thermostatModel", "string"
		attribute "capabilities", "string"
		attribute "temperatureDisplay", "number"
		attribute "coolingSetpointDisplay", "number"
		attribute "heatingSetpointDisplay", "number"
		attribute "thermostatOperatingState", "string"        
		attribute "createdAt","string"
		attribute "updatedAt","string"
		attribute "read","string"
		attribute "remoteState","string"
		attribute "staticVentsCount","number"
		attribute "setBy","string"
		attribute "changeSet","string"
		attribute "tstatData","string"       
		attribute "tstatList","string"       
		attribute "tstatStatesData","string"       

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

		attribute "verboseTrace", "string"

		command "getThermostatInfo"
		command "getThermostatStates"
		command "getRoom"
		command "getZone"
		command "getZonesList"
	}        
	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
    
		multiAttributeTile(name:"thermostatMulti", type:"generic", width:6, height:4,canChangeIcon: true) {
			tileAttribute("device.temperatureDisplay", key: "PRIMARY_CONTROL") {
				attributeState("default", label:'${currentValue}', unit:"dF",backgroundColor:"#44b621")
			}
			tileAttribute("device.thermostatOperatingState", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'Currently ${currentValue}')
			}                
		}
		valueTile("temperatureDisplay", "device.temperatureDisplay", width: 2, height: 2) {
			state("temperatureDisplay", label:'${currentValue}', unit:"dF",
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
		valueTile("name", "device.thermostatName", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("model", "device.thermostatModel", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("make", "device.thermostatMake", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("capabilities", "device.capabilities", inactiveLabel: false, width: 4,
			height: 1, decoration: "flat") {
			state "default", label: 'Able to Cool/Heat ${currentValue}', 
			backgroundColor: "#ffffff"
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/autoMode.png"
		}
		valueTile("roomName", "device.roomName", inactiveLabel: false, width: 4, height: 1,decoration: "flat") {
			state "default", label: 'Room ${currentValue}', backgroundColor: "#ffffff"
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/room.png"
		}
		valueTile("zoneName", "device.zoneName", inactiveLabel: false, width: 4, height: 2) {
			state "default", label: 'Zone(s) ${currentValue}', backgroundColor: "#ffffff" 
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/zoning.jpg"
		}
		valueTile("thermostatSetpoint", "device.thermostatSetpoint", width: 2 , height: 2, inactiveLabel: false) {
			state ("default", label:'Setpoint ${currentValue}', unit:"F",
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
		standardTile("humidity", "device.humidity", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: 'Humidity ${currentValue}%', 
			backgroundColor: "#ffffff", icon: "st.Weather.weather2"
		}
 		standardTile("mode", "device.thermostatMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2,) {
			state "heat", label: '${name}', 
				icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/heatMode.png", backgroundColor: "#ffffff"
			state "off", label: '${name}', 
				icon: "st.Outdoor.outdoor19" 
			state "cool", label: '${name}', 
				icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/coolMode.png"
			state "auto", label: '${name}',
				icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/autoMode.png"
 			backgroundColor: "#ffffff"
		}
        
		standardTile("fanMode", "device.thermostatFanMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "auto", label: '${name}', 
				icon: "st.Appliances.appliances11", backgroundColor: "#ffffff"
			state "on", label: '${name}',  
				icon: "st.Appliances.appliances11",backgroundColor: "#ffffff"
		}
		standardTile("operatingState", "device.thermostatOperatingState", width: 2, height: 2) {
			state "idle", label:'${name}', backgroundColor:"#ffffff"
			state "heating", label:'${name}', backgroundColor:"#e86d13"
			state "cooling", label:'${name}', backgroundColor:"#00A0DC"
		}
      
		valueTile("updatedAt", "device.updatedAt", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Updated ${currentValue}', backgroundColor: "#ffffff"
		}
		standardTile("refresh", "device.temperature", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {
			state "default", label: 'Refresh',action: "refresh", icon:"st.secondary.refresh", 			
			backgroundColor: "#ffffff"
		}
       
		htmlTile(name:"graphHTML", action: "getGraphHTML", width: 6, height: 8,  whitelist: ["www.gstatic.com"])
		main("thermostatMulti")
		details(["thermostatMulti",
			"name",
			"mode",	
			"fanMode",
			"thermostatSetpoint",
			"make", 
			"model",
			"humidity",            
			"capabilities",
			"roomName",
			"refresh",            
			"zoneName",
//			"updated",
			"graphHTML"
		])

	}
    
}


def getTempBackgroundColors() {
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
				[value: 37, color: "#bc2323"],
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
}

mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}

def setHeatingSetpoint(temp) {
	sendEvent(name: "heatingSetpoint", value: temp, unit: "C")
}

def setCoolinSetpoint(temp) {
	sendEvent(name: "coolingSetpoint", value: temp, unit: "C")
}

def setThermostatMode(mode) {
	sendEvent(name: "thermostatMode", value: mode)
    
}

def setThermostatFanMode(mode) {
	sendEvent(name: "thermostatFanMode", value: mode)
}

void installed() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	state?.scale=getTemperatureScale() 
	if (settings.trace) { 
			log.debug("installed>$device.displayName installed with settings: ${settings.inspect()}")
	}
}

/* Ping is used by Device-Watch in attempt to reach the device
*/
def ping() {
	poll()
}

def updated() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])

	state?.scale=getTemperatureScale() 
	retrieveDataForGraph()    
	traceEvent(settings.logFilter,"updated>$device.displayName updated with settings: ${settings.inspect()}",
		settings.trace,get_LOG_TRACE())        
}


//remove from the selected devices list in Service Manager
void uninstalled() {
	traceEvent(settings.logFilter, "executing uninstalled for ${this.device.displayName}", settings.trace)
	parent.purgeChildDevice(this)    
}

// parse events into attributes
def parse(String description) {

}


void updateZones(zones) {
	traceEvent(settings.logFilter,"updateZones>zones from parent=$zones",settings.trace,get_LOG_TRACE())        
	if (data?.zones) {
		data?.zones=[]    
	}    
	data?.zones=zones
	traceEvent(settings.logFilter,"updateZones>data.zones=${data?.zones}",settings.trace,get_LOG_TRACE())        
}

void updateStructures(structures) {
	traceEvent(settings.logFilter,"updateStructures>structures from parent=$structures",settings.trace,get_LOG_TRACE())        
	if (data?.structures) {
		data?.structures=[]    
	}    
	data?.structures=structures
	traceEvent(settings.logFilter,"updateStructures>data.structures=${data.structures}",settings.trace,get_LOG_TRACE())        
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

void updateRooms(rooms) {
	traceEvent(settings.logFilter,"updateRooms>rooms from parent=$rooms",settings.trace,get_LOG_TRACE())        
	if (data?.rooms) {
		data?.rooms=[]    
	}    
	data?.rooms=rooms
	traceEvent(settings.logFilter,"updateRooms>data.rooms=${data.rooms}",settings.trace,get_LOG_TRACE())        
}

//	@id		Id of the room, by default the current one
//	@forceUpdate	forceUpdate of the local cache, by default false
//	@postData	flag used to post the corresponding room data as json, by default false

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

//	@id		Id of the zone [required]
//	@forceUpdate	forceUpdate of the local cache, by default false
//	@postData	flag used to post the corresponding zone data as json, by default false
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
	traceEvent(settings.logFilter,"getStructure>results =${results} for id=$id", settings.trace)
	return results    
}

// thermostatId is single thermostatId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private def refresh_thermostat(thermostatId, asyncValues=null) {
	def todayDay = new Date().format("dd",location.timeZone)
	def room
	String zonesList
    
	def structureId=determine_structure_id()    
	thermostatId=determine_tstat_id(thermostatId)    

	if (!data?.thermostatStates) {
		data?.thermostatStates=[]
	}        

	if (!asyncValues) {
		traceEvent(settings.logFilter, "refresh_thermostat>manual refresh", settings.trace, get_LOG_INFO())
		getThermostatInfo(thermostatId)
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter, "refresh_thermostat>$exceptionCheck for thermostatId=$thermostatId", settings.trace, get_LOG_ERROR())
			return    
		}    
		getThermostatStates(thermostatId, 'current-state')
	} else {
		traceEvent(settings.logFilter, "refresh_thermostat>poll refresh with values=$asyncValues", settings.trace, get_LOG_INFO())
		if (asyncValues.data instanceof Collection) {
 			data?.thermostatStates=asyncValues.data
		} else {
            data?.thermostatStates[0]=asyncValues.data            
		}            
	
	}    
    
	traceEvent(settings.logFilter,"refresh_thermostat> thermostatStates[0]= ${data?.thermostats[0]}", settings.trace)    
//	traceEvent(settings.logFilter,"refresh_thermostat> thermostatStates[0]= ${data?.thermostatStates[0]}", settings.trace)    
	def roomId
	try {    
		roomId=data?.thermostatStates[0]?.relationships?.room?.data?.id    
	} catch (any) {        
		traceEvent(settings.logFilter, "refresh_thermostat>roomId not found- thermostat $thermostatId not associated to a room", settings.trace, get_LOG_WARN())
	}      
	roomId= (roomId)?: device.currentValue("roomId")   // get the value at initialSetup if empty 
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
	def mode=(data?.thermostatStates[0]?.attributes?.mode) ? data?.thermostatStates[0]?.attributes?.mode?.toLowerCase():'off'    
	def dataEvents = [
		thermostatSourceId:data?.thermostatStates[0]?.attributes?."source-id",
 		thermostatName:data?.thermostatStates[0]?.attributes?.name,
		thermostatMake:data?.thermostatStates[0]?.attributes?.make,
		thermostatModel:data?.thermostatStates[0]?.attributes?."thermostat-model",
		capabilities:data?.thermostatStates[0]?.attributes?.capabilities,
		createdAt:formatDateInLocalTime(data?.thermostatStates[0]?.attributes?."created-at"),
		updatedAt:formatDateInLocalTime(data?.thermostatStates[0]?.attributes?."updated-at"),
		setBy:data?.thermostatStates[0]?.attributes?."set-by",
//		read:data?.thermostatStates[0]?.attributes?.read,
		remoteState:data?.thermostatStates[0]?.attributes?."remote-state",
		staticVentsCount:data?.thermostatStates[0]?.attributes?."static-vents",
		changeSet:data?.thermostatStates[0]?.attributes?."changeset",
		thermostatMode:(mode=='float'? 'off': mode),
		temperature: data?.thermostatStates[0]?.attributes?."ambient-temperature-c",
 		temperatureDisplay: data?.thermostatStates[0]?.attributes?."ambient-temperature-c",
		humidity: data?.thermostatStates[0]?.attributes?."humidity",
		coolingSetpoint: data?.thermostatStates[0]?.attributes?."lower-setpoint-c",
		coolingSetpointDisplay: data?.thermostatStates[0]?.attributes?."lower-setpoint-c",
		heatingSetpoint:  data?.thermostatStates[0]?.attributes?."upper-setpoint-c", 
		heatingSetpointDisplay:  data?.thermostatStates[0]?.attributes?."upper-setpoint-c", 
		thermostatSetpoint:  data?.thermostatStates[0]?.attributes?."target-temperature-c", 
		thermostatOperatingState: getThermostatOperatingState(),
		thermostatFanMode:  data?.thermostatStates[0]?.attributes?."fan-state"?.toLowerCase(),
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
	if (!data?.thermostatStates[0]?.attributes?."target-temperature-c") {
		if (mode=='cool') {
			dataEvents.thermostatSetpoint= data?.thermostatStates[0]?.attributes?."lower-setpoint-c"		    
		} else if (mode=='auto') {
			def median =  ((data?.thermostatStates[0]?.attributes?."lower-setpoint-c" + 		    
				data?.thermostatStates[0]?.attributes?."upper-setpoint-c")/2).toFloat().round(1)		    
			dataEvents.thermostatSetpoint= median
		} else {
			dataEvents.thermostatSetpoint= data?.thermostatStates[0]?.attributes?."upper-setpoint-c"		    
		}        
	}    
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_thermostat>done for thermostatId =${thermostatId}", settings.trace)
}



// @Get the basic thermostat status (heating,cooling,fan only)
// @To be called after a poll() or refresh() to have the latest status

//def getThermostatOperatingState() {
//
//	def results = (data?.thermostatStates[0]?.attributes?."operating-state".contains('cool'))?'cooling':
//    				(data?.thermostatStates[0]?.attributes?."operating-state".contains('heat'))?'heating':
//			        (data?.thermostatStates[0]?.attributes?."operating-state".contains('fan'))?'fan only':'idle'
//	 
//	return results                    
//}
def getThermostatOperatingState() {
	log.debug("getThermostatOperatingState: ${data?.thermostats[0]?.attributes}")
	def results = 'idle'
    
    if (data?.thermostats[0]?.attributes?."operating-state" != null) {
    	results = (data?.thermostats[0]?.attributes?."operating-state".contains('cool'))?'cooling':
    				(data?.thermostats[0]?.attributes?."operating-state".contains('heat'))?'heating':
			        (data?.thermostats[0]?.attributes?."operating-state".contains('fan'))?'fan only':'idle'
    }
	return results             
}

// @refresh() has a different polling interval as it is called by the UI (contrary to poll).
void refresh() {
	def thermostatId= determine_tstat_id("") 	    
	def poll_interval=0.5   // set a 30 sec. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"refresh>thermostatId = ${thermostatId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
 			"not refreshing data...", settings.trace)
		return
	}
	state.lastPollTimestamp = now()
	refresh_thermostat(thermostatId)
  
}
void poll() {
	String URI_ROOT = "${get_URI_ROOT()}"
    
    
	def thermostatId= determine_tstat_id("") 	    

	def poll_interval=1   // set a minimum of 1 min. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"poll>thermostatId = ${thermostatId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
				"not refreshing data...", settings.trace)
		return
	}
	if (!isLoggedIn()) {
		login()    
	}    
    
 	if (isTokenExpired()) {
		traceEvent(settings.logFilter,"poll>need to refresh tokens",settings.trace)
       
		if (!refresh_tokens()) {
			traceEvent(settings.logFilter,"poll>$exceptionCheck, not able to renew the refresh token", settings.trace, get_LOG_ERROR())         
		} else {
        
			// Reset Exceptions counter as the refresh_tokens() call has been successful 
			state?.exceptionCount=0
		}            
        
	}
    
	traceEvent(settings.logFilter,"poll>about to call pollAsyncResponse for thermostatId = ${thermostatId}...", settings.trace)
//	log.debug "poll>about to call pollAsyncResponse for thermostatId = ${thermostatId}..."
//	log.debug "poll>data.auth= ${data.auth}..."
//	log.debug "poll>settings= ${settings}..."
	    
	def params = [
		uri: "${URI_ROOT}/api/thermostats/${thermostatId}",
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"

		]
	]
	asynchttp_v1.get('pollAsyncResponseTstatInfo', params)

	params = [
			uri: "${URI_ROOT}/api/thermostats/${thermostatId}/thermostat-states?page[size]=1",
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	asynchttp_v1.get('pollAsyncResponseTstatStates', params)
	traceEvent(settings.logFilter,"poll>done for thermostatId = ${thermostatId}", settings.trace)
}


def pollAsyncResponseTstatInfo(response, data) {	
	def TOKEN_EXPIRED=401
	String URI_ROOT = "${get_URI_ROOT()}"
    
  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponseTstatInfo>Flair's Access token has expired, trying to refresh tokens now...", settings.trace,get_LOG_WARN())
			refresh_tokens()
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseTstatInfo>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseTstatInfo>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues=null
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponseTstatInfo>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
			traceEvent(settings.logFilter,"pollAsyncResponseTstatInfo>responseValues=$responseValues", settings.trace,get_LOG_TRACE())
			def id = responseValues.data.attributes?.id
			if (settings.trace) {                
				def name = responseValues.data.attributes?.name
				def model = responseValues.data.attributes?.model
				traceEvent(settings.logFilter,"pollAsyncResponseTstatInfo>tstatId=${id}, name= $name, model=$model")
			} 
			refresh_thermostat(id,responseValues)            
		}                
		state?.exceptionCount=0                
	}        
}

def pollAsyncResponseTstatStates(response, data) {	
	def TOKEN_EXPIRED=401
  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponseTstatStates>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()      
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseTstatStates>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseTstatStates>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues =null   
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponseTstatStates>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
            
			traceEvent(settings.logFilter,"pollAsyncResponseTstatStates>responseValues=$responseValues", settings.trace,get_LOG_TRACE())
			def tstatId = responseValues.data[0]?.relationships?.thermostat?.data?.id       
			if (settings.trace) {
				def createdAt = responseValues.data[0]?.attributes?."created-at"
				def mode = responseValues.data[0]?.attributes?.mode
				def fanMode = responseValues.data[0]?.attributes?."fan-state"
				def operatingState =responseValues.data[0]?.attributes?."operating-state"
				def coolingSetpoint= responseValues.data[0]?.attributes?."lower-setpoint-c"
				def heatingSetpoint= responseValues.data[0]?.attributes?."upper-setpoint-c"
				def targetTemperature = responseValues.data[0]?.attributes?."target-temperature-c"
				def temperature = responseValues.data[0]?.attributes?."ambient-temperature-c"
				def humidity = responseValues.data[0]?.attributes?."humidity"
				traceEvent(settings.logFilter,"pollAsyncResponseTstatStates>tstatId=${tstatId},mode=$mode,createdAt=$createdAt,targetTemperature= $targetTemperature," +
							"fanMode=$fanMode,operatingState=$operatingState,coolingSetpoint=$coolingSetpoint,heatingSetpoint=$heatingSetpoint,temperature=$temperature,humidity=$humidity",                    
					settings.trace)     
			}
			refresh_thermostatStates(tstatId,responseValues)            
			retrieveDataForGraph()            
			state.lastPollTimestamp = now()
			state?.exceptionCount=0
		}                
	}        
}

// thermostatId is single thermostatId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private def refresh_thermostatStates(thermostatId, asyncValues=null) {
    
	thermostatId=determine_tstat_id(thermostatId)    
	if (!asyncValues) {
		getThermostatStates(thermostatId, 'current-state')
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter, "refresh_thermostat>$exceptionCheck for thermostatId=$thermostatId", settings.trace, get_LOG_ERROR())
			return    
		}    
	} else {
		if (asyncValues.data instanceof Collection) {
 			data?.thermostatStates=asyncValues.data
		} else {
 			data?.thermostatStates[0]=asyncValues.data            
		}            
    
	}    
    
	traceEvent(settings.logFilter,"refresh_thermostat> thermostatStates[0]= ${data?.thermostatStates[0]}", settings.trace)  
	def mode=data?.thermostatStates[0]?.attributes?.mode?.toLowerCase()   
	def dataEvents = [
		setBy:data?.thermostatStates[0]?.attributes?."set-by",
//		read:data?.thermostatStates[0]?.attributes?.read?.toString(),
		remoteState:data?.thermostatStates[0]?.attributes?."remote-state",
		changeSet:data?.thermostatStates[0]?.attributes?."changeset",
		thermostatMode:(mode=='float'? 'off': mode),
		temperature: data?.thermostatStates[0]?.attributes?."ambient-temperature-c",
 		temperatureDisplay: data?.thermostatStates[0]?.attributes?."ambient-temperature-c",
		humidity: data?.thermostatStates[0]?.attributes?."humidity",
		coolingSetpoint: data?.thermostatStates[0]?.attributes?."lower-setpoint-c",
		coolingSetpointDisplay: data?.thermostatStates[0]?.attributes?."lower-setpoint-c",
		heatingSetpoint:  data?.thermostatStates[0]?.attributes?."upper-setpoint-c", 
		heatingSetpointDisplay:  data?.thermostatStates[0]?.attributes?."upper-setpoint-c", 
		thermostatSetpoint:  data?.thermostatStates[0]?.attributes?."target-temperature-c", 
		thermostatOperatingState: getThermostatOperatingState(),
		thermostatFanMode:  data?.thermostatStates[0]?.attributes?."fan-state"?.toLowerCase()
	]
	if (!data?.thermostatStates[0]?.attributes?."target-temperature-c") {
		if (mode=='cool') {
			dataEvents.thermostatSetpoint= data?.thermostatStates[0]?.attributes?."lower-setpoint-c"		    
		} else if (mode=='auto') {
			def median =  ((data?.thermostatStates[0]?.attributes?."lower-setpoint-c" + 		    
				data?.thermostatStates[0]?.attributes?."upper-setpoint-c")/2)?.toFloat().round(1)		    
			dataEvents.thermostatSetpoint= median
		} else {
			dataEvents.thermostatSetpoint= data?.thermostatStates[0]?.attributes?."upper-setpoint-c"		    
		}        
	}    
          
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_thermostatStates>done for thermostatId =${thermostatId}", settings.trace)
	retrieveDataForGraph()    
}

private void generateEvent(Map results) {
    
	state?.scale = getTemperatureScale() // make sure to display in the right scale
	def scale = state?.scale
    
	if (results) {
		results.each { name, value ->
			def isDisplayed = true

			String upperFieldName = name.toUpperCase()
// 			Temperature variable names for display contain 'display'
			log.debug("Param: ${upperFieldName}: ${value}")

			if (upperFieldName.contains("DISPLAY")) {  

				double tempValue = getTemperatureValue(value).round(1)
				String tempValueString = String.format('%2.1f', tempValue)
				if (scale == "C") {
					if ((upperFieldName.contains("PROGRAM") || upperFieldName.contains("SETPOINT"))) { 
						if (tempValueString.matches(".*([.,][456])")) {                
							tempValueString=String.format('%2d.5', tempValue.intValue())                
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 456=> rounded to .5", settings.trace)	
						} else if (tempValueString.matches(".*([.,][789])")) {  
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
							tempValue=tempValue.intValue() + 1                        
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						} else {
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 0123=> rounded to previous .0", settings.trace)	
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						}
					}
				} else {
					tempValue = tempValue.round()
					tempValueString = String.format('%2d', tempValue.intValue())            
				}
                
				def isChange = isStateChange(device, name, tempValueString.toString())
                
				isDisplayed = isChange
                log.debug("${upperFieldName} event: ${name} - ${tempValueString} ${scale}")
				sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed)                                     									 

// 			Temperature variable names contain 'temp' or 'setpoint' (not for display)           

			} else if ((upperFieldName.contains("TEMP")) || (upperFieldName.contains("SETPOINT"))) {  
                                
				double tempValue = getTemperatureValue(value).round(1)
				String tempValueString = String.format('%2.1f', tempValue)
				if (tempValueString != '0.0') {    // don't post value if all zeros                
                
					if (scale == "C") {
						if ((upperFieldName.contains("PROGRAM") || upperFieldName.contains("SETPOINT"))) { 
							if (tempValueString.matches(".*([.,][456])")) {                
								tempValueString=String.format('%2d.5', tempValue.intValue())                
								traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 456=> rounded to .5", settings.trace)	
							} else if (tempValueString.matches(".*([.,][789])")) {  
								traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
								tempValue=tempValue.intValue() + 1                        
								tempValueString=String.format('%2d.0', tempValue.intValue())               
							} else {
								traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 0123=> rounded to previous .0", settings.trace)	
								tempValueString=String.format('%2d.0', tempValue.intValue())               
							}
						}
					}                    
                
					def isChange = isStateChange(device, name, tempValueString)
                
					isDisplayed = isChange
                    log.debug("Field: ${name} - {$tempValueString} ${scale}")
					sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed, isStateChange: isChange)
                    sendEvent(name: name, value: tempValueString, unit: scale)
                    
                    if (upperFieldName.contains("CURRENTTEMPERATURE")) {
                    	sendEvent(name: "temperature", value: tempValueString, unit: scale)
                    }
                    if (upperFieldName.contains("RMSETPOINT")) {
                    	sendEvent(name: "thermostatSetpoint", value: tempValueString, unit: scale)
                        //sendEvent(name: "heatingSetpoint", value: tempValueString, unit: scale)
                        //sendEvent(name: "coolingSetpoint", value: tempValueString, unit: scale)
                    }                    
				}                    

			} else if (upperFieldName.contains("SPEED")) {

// 			Speed variable names contain 'speed'

 				double speedValue = getSpeed(value).round(1)
				def isChange = isStateChange(device, name, speedValue.toString())
				isDisplayed = isChange
                log.debug("speed event: ${name} - ${ speedValue.toString()} ${getDistanceScale()}")
				sendEvent(name: name, value: speedValue.toString(), unit: getDistanceScale(), displayed: isDisplayed, isStateChange: isChange)                                     									 
			} else if (upperFieldName.contains("HUMIDITY")) {
				value = (value?:0)
				double humValue = value?.toDouble().round(0)
				String humValueString = String.format('%2d', humValue.intValue())
				def isChange = isStateChange(device, name, humValueString)
				isDisplayed = isChange
                log.debug("HUMIDITY event: ${name} - ${humValueString}")
				sendEvent(name: name, value: humValueString, unit: "%", displayed: isDisplayed, isStateChange: isChange)
			} else if (upperFieldName.contains("DATA")) { // data variable names contain 'data'
	
				log.debug("DATA event: ${name} - ${value}")
				sendEvent(name: name, value: value, displayed: (settings.trace?:false))                                     									 

			//eeddir
			} else if (upperFieldName.contains("CAPABILITIES")) {
            	log.debug("${upperFieldName} event: ${name} - ${value}")
                if (value.contains("Both")) {
					sendEvent(name: "flairThermostatCapabilities", value: "Heat/Cool")
                } else {
                	sendEvent(name: "flairThermostatCapabilities", value: value)
                }

			} else if (upperFieldName.contains("ROOMNAME")) {
            	log.debug("${upperFieldName} event: ${name} - ${value}")
				sendEvent(name: "flairRoom", value: value)

			} else if (upperFieldName.contains("ZONENAME")) { 
				log.debug("${upperFieldName} event: ${name} - ${value}")
                sendEvent(name: "flairZone", value: value)
 
			} else if (upperFieldName.contains("THERMOSTATOPERATINGSTATE")) { 
				log.debug("${upperFieldName} event: ${name} - ${value}")
                sendEvent(name: "thermostatOperatingState", value: value)

			} else if (upperFieldName.contains("THERMOSTATNAME")) { 
				log.debug("${upperFieldName} event: ${name} - ${value}")
                sendEvent(name: "flairThermostatName", value: value)

			} else if (upperFieldName.contains("THERMOSTATMAKE")) { 
				log.debug("${upperFieldName} event: ${name} - ${value}")
                sendEvent(name: "flairThermostatMake", value: value)

			} else if (upperFieldName.contains("THERMOSTATMODEL")) { 
				log.debug("${upperFieldName} event: ${name} - ${value}")
                sendEvent(name: "flairThermostatModel", value: value)

			} else if (upperFieldName.contains("UPDATEDAT")) { 
				log.debug("${upperFieldName} event: ${name} - ${value}")
                sendEvent(name: "flairStatus", value: "Update: ${value}")

 			} else {
				if (value != null && value != 'null' && value != '[:]' && value != '[]') {           
					def isChange = isStateChange(device, name, value.toString())
					isDisplayed = isChange

					log.debug("Other event: ${name} - ${value.toString()}")
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

private def getSpeed(value) {
	if (value == null) return 0.0
	double miles = value
	if (state?.scale == "C"){
		return milesToKm(miles)
	} else {
		return miles
	}
}

private def getDistanceScale() {
	def scale= state?.scale
	if (scale == 'C') {
		return "kmh"
	}
	return "mph"
}


private void api(method, id, args, success = {}) {
	def MAX_EXCEPTION_COUNT=5
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
			state.exceptionCount=0
		}            
        
	}
    
	def methods = [
		'getTstatInfo': 
			[uri:"${URI_ROOT}/api/thermostats/${id}", 
				type:'get'],
		'getTstatStates': 
			[uri:"${URI_ROOT}/api/thermostats/${id}/thermostat-states", 
				type:'get']
		]
	def request = methods.getAt(method)
	if (request.type=="get" && args) {
		def args_encoded = java.net.URLEncoder.encode(args.toString(), "UTF-8")
		request.uri=request.uri + "?${args_encoded}"    
//		request.uri=request.uri + "?${args}"    
	}    
	doRequest(request.uri, args, request.type, success)
	if (state.exceptionCount >= MAX_EXCEPTION_COUNT) {
		def exceptionCheck=device.currentValue("verboseTrace")
		traceEvent(settings.logFilter,"api>error: found a high number of exceptions (${state.exceptionCount}), last exceptionCheck=${exceptionCheck}, about to reset counter",true)         
		if (!exceptionCheck.contains("Unauthorized")) {          
			state.exceptionCount = 0  // reset the counter as long it's not unauthorized exception
			sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		}            
	}        

}

// Need to be authenticated in before this is called. So don't call this. Call api.
private void doRequest(uri, args, type, success) {
	def params = [
		uri: uri,
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//			'Content-Type': "text/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"
		],
		query: [format: 'json']
	]
	try {
		traceEvent(settings.logFilter,"doRequest>about to ${type} with uri ${params.uri}, (unencoded)args= ${args}", settings.trace)
		traceEvent(settings.logFilter,"doRequest>about to ${type} with params= ${params}", settings.trace)
		if (type == 'post') {
			params?.body = args 
			httpPostJson(params, success)

		} else if (type == 'get') {
			httpGet(params, success)
		}
		/* when success, reset the exception counter */
		state.exceptionCount=0
		traceEvent(settings.logFilter,"doRequest>done with ${type}", settings.trace)

	} catch (java.net.UnknownHostException e) {
		traceEvent(settings.logFilter,"doRequest> Unknown host ${params.uri}", true, get_LOG_ERROR())
	} catch (java.net.NoRouteToHostException e) {
		traceEvent(settings.logFilter,"doRequest> No route to host ${params.uri}", true, get_LOG_ERROR())
	} catch (e) {
		traceEvent(settings.logFilter,"doRequest>exception $e for ${params.uri}", settings.trace, get_LOG_ERROR())
		state?.exceptionCount = (state?.exceptionCount?:0) +1
		throw e        
	} 
    
}



// @id		Id of the thermostat, by default, retrieve all thermostats under a user 
// @postData	indicates whether the data should be posted for further processing
void getThermostatInfo(id, postData=false) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	def tstatData = []
	def tstatList = ""
	def bodyReq = ""
	def statusCode = true
	int j = 0

	traceEvent(settings.logFilter, "getThermostatInfo>bodyReq=${bodyReq},id=$id", settings.trace)

	if (!data?.thermostatStates) {
		data?.thermostatStates=[]    
	}    
	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getTstatInfo', id, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getThermostatInfo>thermostatId=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
			
				traceEvent(settings.logFilter, "getThermostatInfo>resp.data=${resp.data}", settings.trace)
				if (resp.data.data instanceof Collection) {
					data?.thermostatStates =resp.data.data
				} else {
					data?.thermostatStates[0] =resp.data.data
				}
				data?.thermostatStates?.each {
					def thermostatId = it?.id
					def name = it?.attributes?.name
					def model = it?.attributes?.model
					def make = it?.attributes?.make
					def tstatStates = it?.relationships?."thermostat-states"
					def room = it.relationships?.room                         
                        
//					log.debug "getThermostatInfo>thermostatId=${thermostatId}, name= $name, make=$make, model=$model," +
// 						"room=$room, tstatStates=$tstatStates"
					traceEvent(settings.logFilter, "getThermostatInfo>thermostatId=${thermostatId}, name= $name, make=$make, model=$model," +
 						"room=$room, tstatStates=$tstatStates",settings.trace)
					if (postData) {
						traceEvent(settings.logFilter, "getThermostatInfo>adding ${it} to ventData", settings.trace)
						tstatData << it // to be transformed into Json later
					}
					tstatList = tstatList + thermostatId + ','
				} /* end for each thermostat */
				traceEvent(settings.logFilter, "getThermostatInfo>done for thermostatId=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getThermostatInfo>error=${statusCode.toString()} for thermostatId=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	def tstatDataJson = ""

	if (tstatData != []) {
		statDataJson = new groovy.json.JsonBuilder(tstatData)
	}
	/*	
	traceEvent(settings.logFilter,"getThermostatInfo>ventDataJson=${ventDataJson}", settings.trace)
	*/
	def tstatEvents = [
			tstatData: "${tstatDataJson.toString()}",
			tstatList: "${tstatList.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"getThermostatInfo>ventListEvents to be sent= ${ventListEvents}", settings.trace)
	*/
	generateEvent(tstatEvents)

}

// @id			Id of the thermostat object, by default the current thermostat
// @method		Possible values: current-state, previous-state, by default=all states
// @postData		indicates whether the data should be posted for further processing  [optional]
// @postTimestamp	timestamp (from) threshold to post states [optional]

void getThermostatStates(id, method="", postData=false,postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	String FLAIR_CURRENT_STATE="current-state"    
	String FLAIR_PREVIOUS_STATE="previous-state"   
	String FLAIR_ALL_STATES="getTstatStates"    
	def tstatData = []
	def MAX_INDICE=5    
	def statusCode = true
	int j = 0

	if (!id) {
		id = device.currentValue("thermostatId")
		if (!id) {        	
			traceEvent(settings.logFilter, "getThermostatStates>id=$id exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	def bodyReq = ""
	if (!data?.thermostatStates) {
		data?.thermostatStates=[]    
	}    

	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_STATE)?FLAIR_CURRENT_STATE:((method==FLAIR_PREVIOUS_STATE)?FLAIR_PREVIOUS_STATE:FLAIR_ALL_STATES)    
/*    
	if (method in [FLAIR_CURRENT_STATE, FLAIR_PREVIOUS_STATE] ) {
		bodyReq=bodyReq + "page[size]=1"    
	}
*/    
	traceEvent(settings.logFilter, "getThermostatStates>method=$method, bodyReq=${bodyReq}, id=$id", settings.trace)

	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getTstatStates', id, bodyReq) {resp->
			statusCode = resp.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getThermostatStates>id=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getThermostatStates>resp.data=${resp.data}", settings.trace)
				if (resp.data.data instanceof Collection) {
					data?.thermostatStates =resp.data.data
				} else {
					data?.thermostatStates[0] =resp.data.data
				}
				int max_ind = (data?.thermostatStates) ? (data?.thermostatStates.size()-1): -1				                
				max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind      
                //eeddir 
				for (i in 0..max_ind) {
					def createdAt = data?.thermostatStates[i]?.attributes?."created-at"
					def targetTemperature = data?.thermostatStates[i]?.attributes?."target-temperature-c"
					def mode = data?.thermostatStates[i]?.attributes?.mode
					def fanMode = data?.thermostatStates[i]?.attributes?."fan-state"
					def operatingState = data?.thermostatStates[i]?.attributes?."operating-state"
					def tstatId = data?.thermostatStates[i]?.attributes?.data?.id
					def coolingSetpoint= data?.thermostatStates[i]?.attributes?."lower-setpoint-c"
					def heatingSetpoint=  data?.thermostatStates[i]?.attributes?."upper-setpoint-c"
					def temperature=  data?.thermostatStates[i]?.attributes?."ambient-temperature-c"
					def humidity=  data?.thermostatStates[i]?.attributes?.humidity
					traceEvent(settings.logFilter,"getThermostatStates>tstatId=${id}, state no ${i}, mode=$mode,createdAt=$createdAt,targetTemperature= $targetTemperature," +
								"fanMode=$fanMode,operatingState=$operatingState,coolingSetpoint=$coolingSetpoint,heatingSetpoint=$heatingSetpoint,temperature=$temperature,humidity=$humidity",                    
						settings.trace)     

					if (method == FLAIR_CURRENT_STATE) {
						if (postData) { 
							tstatData << data?.thermostatStates[i]
						}
						break                   
					} else if ((method == FLAIR_PREVIOUS_STATE) && (((max_ind > 0) && (i==1)) || (max_ind==0))) {
						if (postData) { 
							tstatData =[] // just generate the json for the last row
							tstatData << data?.thermostatStates[i]
						}                            
						break                   
					} else if (postData) {
						if (postTimestamp) {
							// Save states greater than timestamp 
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getThermostatStates>adding ${data?.thermostatStates[i]} to tstatData, createdDate (${createdDate.getTime()}) is greater than timestamp ($postTimestamp)", settings.trace)
								tstatData << data?.thermostatStates[i] // to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getThermostatStates>adding ${data?.thermostatStates[i]} to tstatData", settings.trace)
							tstatData << data?.thermostatStates[i]// to be transformed into Json later
						}
					}
				} /* end for each state  */
				traceEvent(settings.logFilter, "getThermostatStates>done for id=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getThermostatStates>error=${statusCode.toString()} for id=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	generate_tstat_state_json()
}


private def generate_tstat_state_json(tstatData) {
	def tstatDataJson = ""

	if (tstatData != []) {
		tstatDataJson = new groovy.json.JsonBuilder(tstatData)
	}
	/*	
	traceEvent(settings.logFilter,"generate_tstat_state_json>ventDataJson=${tstatDataJson}", settings.trace)
	*/
	def tstatEvents = [
			tstatStatesData: "${tstatDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"generate_tstat_state_json>tstatEvents to be sent= ${tstatEvents}", settings.trace)
	*/
	generateEvent(tstatEvents)
}

private def refreshLocalAuthToken() {	
	boolean refresh_success=false
	def REFRESH_SUCCESS_CODE=200    
 
	traceEvent(settings.logFilter,"refreshLocalAuthToken>about to refresh auth token", settings.trace)
	def stcid = get_appKey()
	def privateKey=get_privateKey()    

	def refreshParams = [
			method: 'POST',
			uri   : "${get_URI_ROOT()}",
			path  : "/oauth/token",
			query : [grant_type: 'refresh_token', refresh_token: "${data?.auth?.refresh_token}", client_id: stcid, client_secret: privateKey ]
	]

	traceEvent(settings.logFilter,"refreshLocalAuthToken>refreshParams=$refreshParams", settings.trace)
	def jsonMap
	httpPost(refreshParams) { resp ->
		if (resp.status == REFRESH_SUCCESS_CODE) {
			traceEvent(settings.logFilter,"refreshLocalAuthToken>token refresh done resp = ${resp.data}", settings.trace)
			jsonMap = resp.data

			if (resp.data) {
				data.auth.access_token= resp.data.access_token
				data.auth.refresh_token= resp.data.refresh_token
				data.auth.expires_in = resp.data.expires_in
				data.auth.token_type = resp.data.token_type
				data.auth.scope = resp?.data?.scope
				def authexptime = new Date((now() + (resp?.data?.expires_in  * 1000))).getTime()
				data.auth.authexptime=authexptime 						                        
				traceEvent(settings.logFilter,"refreshLocalAuthToken>new access token = ${data.auth.access_token}", settings.trace)
				traceEvent(settings.logFilter,"refreshLocalAuthToken>new refresh token = ${data.auth.refresh_token}", settings.trace)
				refresh_success=true   
			} /* end if resp.data */
		} else {
			traceEvent(settings.logFilter,"refreshLocalAuthToken>refresh failed ${resp.status} : ${resp.status.code}", true, get_LOG_ERROR())
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

	if (data?.auth?.thermostatId) {
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
	traceEvent(settings.logFilter,"refreshChildTokens>old data.auth= $data.auth", settings.trace)
	if ((!data?.auth?.authexptime) || ((data?.auth?.authexptime) && (auth.authexptime > data?.auth?.authexptime))) {    
		if (!data?.auth?.authexptime) { // if info lost
			def varSettings=[:]
			varSettings=settings        
			data?.auth=varSettings    
			if (!settings.appKey) { // if appKey is lost
				settings.appKey=auth?.clientId
			}            
			if (!settings.privateKey) { // if privateKey is lost
				settings.privateKey=auth?.privateKey
			}            
		}            
		save_data_auth(auth)        
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
	if (data?.auth?.access_token == null) {
		traceEvent(settings.logFilter,"isLoggedIn> no data auth", settings.trace,get_LOG_TRACE())
		return false
	} 
	return true
}
private def isTokenExpired() {
	def buffer_time_expiration=5  // set a 5 min. buffer time 
	def time_check_for_exp = now() + (buffer_time_expiration * 60 * 1000)
	double authExpTimeInMin= ((data.auth.authexptime - time_check_for_exp)/60000).toDouble().round(0)  
	traceEvent(settings.logFilter,"isTokenExpired>expiresIn timestamp: ${data?.auth?.authexptime} > timestamp check for exp: ${time_check_for_exp}?", settings.trace)
	traceEvent(settings.logFilter,"isTokenExpired>expires in ${authExpTimeInMin.intValue()} minutes", settings.trace, get_LOG_INFO())
	if (authExpTimeInMin <0) {
		traceEvent(settings.logFilter,"isTokenExpired>auth token buffer time  expired (${buffer_time_expiration} min.), countdown is ${authExpTimeInMin.intValue()} minutes, need to refresh tokens now!",
			settings.trace,get_LOG_WARN())        
	}    
	if (authExpTimeInMin < (0-buffer_time_expiration)) {
		traceEvent(settings.logFilter,"isTokenExpired>refreshing tokens is more at risk (${authExpTimeInMin} min.),exception count may increase if tokens not refreshed!",
			settings.trace,get_LOG_WARN())        
	}    
	if (data.auth.authexptime > time_check_for_exp) {
		traceEvent(settings.logFilter,"isTokenExpired> not expired", settings.trace, get_LOG_INFO())
		return false
	}
	traceEvent(settings.logFilter,"isTokenExpired>expired", settings.trace, get_LOG_INFO())
	return true
}


// @Determine id from settings or initalSetup
private def determine_tstat_id(tstat_id) {
	def tstatId=device.currentValue("thermostatId")
    
	if ((tstat_id != null) && (tstat_id != "")) {
		tstatId = tstat_id
	} else if ((settings.thermostatId != null) && (settings.thermostatId  != "")) {
		tstatId = settings.thermostatId.trim()
		traceEvent(settings.logFilter,"determine_tstat_id> tstatId from settings = ${settings.thermostatId}", settings.trace)
	} else if (data?.auth?.thermostatId) {
		settings?.thermostatId = data?.auth?.thermostatId
		traceEvent(settings.logFilter,"determine_tstat_id> tstatId from data.auth = ${data?.auth?.thermostatId}", settings.trace)
	} else if ((tstatId != null) && (tstatId != "")) {
		settings?.thermostatId= tstatId
		traceEvent(settings.logFilter,"determine_tstat_id> tstatId from device = ${tstatId}", settings.trace)
	}
	if ((tstat_id != "") && (tstat_id != tstatId) && (tstatId)) {
		sendEvent(name: "thermostatId", displayed: (settings.trace?:false),value: tstatId)    
	}
	return tstatId
}


// @Determine id from settings or initalSetup
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


// @Called by MyFlairServiceMgr for initial creation of a child Device
void initialSetup(device_client_id,device_private_key,access_token,refresh_token,token_type,token_authexptime,device_structure_id,device_tstat_id) {
	def varSettings=[:]
	settings?.trace=true
	settings?.logFilter=2
	if (settings.trace) {
		log.debug "initialSetup>begin"
		log.debug "initialSetup> device_tstat_Id = ${device_tstat_id}"
		log.debug "initialSetup> device_client_id = ${device_client_id}"
		log.debug "initialSetup> device_private_key = ${device_private_key}"
		log.debug "initialSetup> token_type = ${token_type}"
		log.debug "initialSetup> token_authexptime = ${token_authexptime}"
		log.debug "initialSetup> device_structure_Id = ${device_structure_id}"
	}	

	settings?.appKey= device_client_id
	settings?.privateKey= device_private_key
	settings?.structureId = device_structure_id
	settings?.thermostatId = device_tstat_id
	varSettings=settings	
	data?.auth=[:]
	data?.auth=varSettings
    
	data?.auth?.access_token= access_token
	data?.auth?.refresh_token = refresh_token
	data?.auth?.token_type = token_type
	data?.auth?.authexptime= token_authexptime
    
	sendEvent(name: "thermostatId", displayed: (settings.trace?:false), value: device_tstat_id)    
	sendEvent(name: "structureId", displayed: (settings.trace?: false), value: device_structure_id)
	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()

    sendEvent(name: "flairThermostatMode", value: "n/a")
    sendEvent(name: "flairThermostatMake", value: "n/a")
    sendEvent(name: "flairThermostatModel", value: "n/a")
    sendEvent(name: "flairThermostatCapabilities", value: "n/a")
    sendEvent(name: "flairRoom", value: "n/a")    
    sendEvent(name: "flairZone", value: "n/a")
    sendEvent(name: "flairStatus", value: "n/a")
    sendEvent(name: "temperature", value: 0, unit: "C")
    sendEvent(name: "supportedThermostatModes", value: "[off,heat,cool,auto]")
    sendEvent(name: "supportedThermostatFanModes", value: "[auto,on]")    

//	refresh_thermostat(device_tstat_id)
	if (settings.trace) {
		log.debug "initialSetup> settings = $settings"
		log.debug "initialSetup> data.auth = ${data.auth}"
		log.debug "initialSetup>end"
	}


}

private def toQueryString(Map m) {
	return m.collect { k, v -> "${k}=${URLEncoder.encode(v.toString())}" }.sort().join("&")
}

private def cToF(temp) {
	return (temp * 1.8 + 32)
}
private def fToC(temp) {
	return (temp - 32).toDouble() / 1.8
}
private def get_URI_ROOT() {
	return "https://api.flair.co"
}

private def get_API_VERSION() {
	return "1"
}

private def get_APPLICATION_VERSION() {
	return "application/vnd.api+json; co.flair.api.version=${get_API_VERSION()}"
}


private def ISODateFormat(dateString) {
 	SimpleDateFormat ISO8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date aDate = ISO8601format.parse(dateString.substring(0,19) + 'Z')
	return aDate
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


private def formatDate(dateString) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm zzz")
	Date aDate = sdf.parse(dateString)
	return aDate
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

def retrieveDataForGraph() {
	def scale = state?.scale
	Date todayDate = new Date()
	def todayDay = new Date().format("dd",location.timeZone)
	String mode = device.currentValue("thermostatMode")    
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
	def heatingSetpointData = device.statesSince("thermostatSetpoint", startOfWeek, [max:200])
	def coolingSetpointData = device.statesSince("thermostatSetpoint", startOfWeek, [max:200])
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
	traceEvent(settings.logFilter,"retrieveDataForGraph>temperatureTable (size=${state?.temperatureTable.size()}) =${state?.temperatureTable}",settings.trace,get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>state.currentMode= ${state?.currentMode}",settings.trace)    
	traceEvent(settings.logFilter,"retrieveDataForGraph>heatingSetpointTable (size=${state?.heatingSetpointTable.size()}) =${state?.heatingSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>coolingSetpointTable (size=${state?.coolingSetpointTable.size()}) =${state?.coolingSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>humidityTable (size=${state?.humidityTable.size()}) =${state?.humidityTable}",settings.trace,get_LOG_TRACE())  
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
private def getCustomImagePath() {
	return "https://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/"
}

private def getStandardImagePath() {
	return "https://cdn.device-icons.smartthings.com"
}