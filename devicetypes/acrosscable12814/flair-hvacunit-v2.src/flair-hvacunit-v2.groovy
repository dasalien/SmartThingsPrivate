/*
 *  My Flair HvacUnit 
 *  Copyright 2017-2020 Yves Racine
 *  LinkedIn profile: www.linkedin.com/in/yracine
 *  Version 2.2
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
	input("hvacUnitId", "text", title: "Serial #", description:
		"The id of your HvacUnit\n(not needed when using MyFlairServiceMgr, leave it blank)")
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
	// Automatically generated. Make future change here.
	definition(name: "Flair HvacUnit V2", author: "Yves Racine",  mnmn: "SmartThings", vid: "SmartThings-smartthings-Z-Wave_Thermostat",ocfDeviceType: "x.com.st.mode.airconditioner",namespace: "acrosscable12814") {
		capability "Air Conditioner Mode"        
		capability "Fan Speed"        
		capability "Sensor"
		capability "Thermostat"
		capability "ThermostatHeatingSetpoint"
		capability "ThermostatCoolingSetpoint"
		capability "ThermostatSetpoint"
		capability "ThermostatMode"
		capability "ThermostatFanMode"
		capability "ThermostatOperatingState"        
//		capability "Relative Humidity Measurement"
		capability "Temperature Measurement"
		capability "Polling"
		capability "Switch"
		capability "Refresh"
		capability "Health Check"

		attribute "structureId", "string"
		attribute "hvacUnitId", "string"
		attribute "hvacUnitName", "string"
		attribute "hvacUnitMake", "string"
		attribute "type", "string"
		attribute "power", "string"
		attribute "capabilities", "string"
		attribute "makeId", "string"
		attribute "codesetId", "string"
		attribute "constraints", "string"
		attribute "buttonPresses", "string"
		attribute "swingMode", "string"
		attribute "temperatureDisplay", "number"
		attribute "coolingSetpointDisplay", "number"
		attribute "heatingSetpointDisplay", "number"
		attribute "thermostatSetpointDisplay", "number"
		attribute "thermostatFanSpeed", "string"
		attribute "verboseTrace", "string"
		attribute "hvacUnitOperatingState", "string"        
		attribute "createdAt","string"
		attribute "updatedAt","string"
		attribute "setBy","string"
		attribute "changeSet","string"
		attribute "hvacUnitData","string"       
		attribute "hvacUnitList","string"       
		attribute "hvacUnitStatesData","string"       

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
		attribute "zoneData","string"

		attribute "summaryReport","string"
		attribute "verboseTrace","string"

		command "getHvacUnitInfo"
		command "getHvacUnitStates"
		command "setHvacUnit"
//		command "setHvacUnitState"	// No Longer supported by Flair APIs
		command "levelUp"
		command "levelDown"
		command "coolLevelUp"
		command "coolLevelDown"
		command "heatLevelUp"
		command "heatLevelDown"
		command "coolLevelUp"
		command "coolLevelDown"
		command "setThermostatFanMode"
		command "setThermostatFanSpeed"
		command "setFanSpeed"
		command "fanLow"
		command "fanMedium"
		command "fanHigh"
		command "fanOff"
		command "fanAuto"
		command "fanOn"
		command "swingOn"
		command "swingOff"
		command "swingTop"
		command "setSwingMode"
		command "dry" 
		command "fanOnly" 
		command "setAirConditionerMode"        
        
        
		command "getRoom"        
		command "setRoom"
		command "setRoomType"
		command "setRoomLevel"
//		command "setRoomWindows"		/ Not supported currently by the Flair APIs
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
//		command "setRoomOccupancyMode"
		command "setRoomPucksInactive"        
		command "getZone"        
		command "getZonesList"
		command "produceSummaryReport"
	}        
	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
    
		multiAttributeTile(name: "hvacMulti", type: "lighting", width: 6, height: 4, canChangeIcon: true, decoration: "flat" ) {
			tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
				attributeState "off", label: '${name}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
				attributeState "on", label: '${name}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
			}
			tileAttribute("device.thermostatOperatingState", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'Currently ${currentValue}')
			}   		
		}
		valueTile("name", "device.hvacUnitName", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		standardTile("occupancyMode", "device.rmOccupancyMode", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: 'RoomOccMode ${currentValue}', 
			backgroundColor: "#ffffff",
			icon: "st.Home.home4"
		}
		valueTile("make", "device.hvacUnitMake", inactiveLabel: false, width: 4,height: 1,
			decoration:"flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
//			icon: "https://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/split.png"
		}
		valueTile("type", "device.type", inactiveLabel: false, width: 4,
			height: 1, decoration: "flat") {
			state "default", label: 'Type ${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("inactive", "device.rmPucksInactive", inactiveLabel: false, width: 2,
			height: 2) {
			state "true", label: 'Puck ${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("temperatureDisplay", "device.temperatureDisplay", width: 2, height: 2) {
			state("temperatureDisplay", label:'Ambient ${currentValue}', unit:"dF",
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
		valueTile("capabilities", "device.capabilities", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: 'Able to Cool/Heat ${currentValue}', 
			backgroundColor: "#ffffff"
//			icon: "https://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/autoMode.png"
		}
		valueTile("roomName", "device.roomName", inactiveLabel: false, width: 4, height: 1,decoration: "flat") {
			state "default", label: 'Room ${currentValue}', backgroundColor: "#ffffff"
//			icon: "https://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/room.png"
		}
		valueTile("zoneName", "device.zoneName", inactiveLabel: false, width: 4, height: 2) {
			state "default", label: 'Zone(s) ${currentValue}', backgroundColor: "#ffffff" 
//			icon: "https://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/zoning.jpg"
		}
		standardTile("levelDown", "device.thermostatSetpoint", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "default", action:"levelDown", icon: "${getCustomImagePath()}coolDown.png", backgroundColor: "#ffffff"

		}
		standardTile("levelUp", "device.thermostatSetpoint", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "default", action:"levelUp", icon: "${getCustomImagePath()}heatUp.png", backgroundColor: "#ffffff"
		}

		valueTile("thermostatSetpoint", "device.thermostatSetpointDisplay", width: 2, height: 2, inactiveLabel: false) {
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

 		standardTile("mode", "device.thermostatMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "heat", label: '${name}', action: "dry", 
				icon: "${getCustomImagePath()}heatMode.png", backgroundColor: "#ffffff",
				nextState: "dry"
			state "dry", label: '${name}', action: "cool", 
				icon: "${getCustomImagePath()}coolMode.png", backgroundColor: "#ffffff",
				nextState: "cool"
			state "cool", label: '${name}', action: "fanOnly", 
				icon: "${getCustomImagePath()}coolMode.png", backgroundColor: "#ffffff",
				nextState: "fan"
			state "fan", action: "heat", label: '${name}',
				icon: "${getCustomImagePath()}Fan.png", backgroundColor: "#ffffff",
				nextState: "heat"
		}
             
		standardTile("fanMode", "device.thermostatFanMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "auto", label: '${name}', action: "thermostat.fanOn", 
				icon: "st.Appliances.appliances11", backgroundColor: "#ffffff",
				nextState: "on"
			state "on", label: '${name}', action: "thermostat.fanAuto", 
				icon: "st.Appliances.appliances11",backgroundColor: "#ffffff",
				nextState: "auto"
		}
		standardTile("swingMode", "device.swingMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "top", label: 'Swing ${name}', action: "swingOff", 
				icon: "st.motion.motion.inactive", backgroundColor: "#ffffff",
				nextState: "off"
			state "off", label: 'Swing ${name}', action: "swingOn", 
				icon: "st.motion.motion.inactive",backgroundColor: "#ffffff",
				nextState: "on"
			state "on", label: 'Swing ${name}', action: "swingTop", 
				icon: "st.motion.motion.inactive",backgroundColor: "#ffffff",
				nextState: "top"
		}
		standardTile("fanSpeed", "device.thermostatFanSpeed", inactiveLabel: false,decoration: "flat", width: 2, height: 2) {
			state "auto", label: '${name}', action: "fanLow", 
				icon: "st.Appliances.appliances11", backgroundColor: "#ffffff",
				nextState: "low"
			state "low", label: '${name}', action: "fanMedium", 
				icon: "st.Appliances.appliances11",backgroundColor: "#ffffff",
				nextState: "medium"
			state "medium", label: '${name}', action: "fanHigh", 
				icon: "st.Appliances.appliances11",backgroundColor: "#ffffff",
				nextState: "high"
			state "high", label: '${name}', action: "fanAuto", 
				icon: "st.Appliances.appliances11",backgroundColor: "#ffffff",
				nextState: "auto"
		}
		standardTile("operatingState", "device.thermostatOperatingState", width: 2, height: 2) {
			state "idle", label:'${name}', backgroundColor:"#ffffff"
			state "heating", label:'${name}', backgroundColor:"#e86d13"
			state "cooling", label:'${name}', backgroundColor:"#00A0DC"
		}
		valueTile("updatedAt", "device.updatedAt", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Updated ${currentValue}', backgroundColor: "#ffffff"
		}
		standardTile("refresh", "device.thermostatOperatingState", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {
			state "default", label: 'Refresh',action: "refresh", icon:"st.secondary.refresh", 			
			backgroundColor: "#ffffff"
		}
       
		htmlTile(name:"graphHTML", action: "getGraphHTML", width: 6, height: 8,  whitelist: ["www.gstatic.com"])
		main("hvacMulti")
		details(["hvacMulti",
			"name",
			"mode",	
			"fanSpeed",
			"swingMode",            
			"inactive",
			"temperatureDisplay",            
 			"thermostatSetpoint", "levelUp", "levelDown",
			"capabilities",
			"make", 
			"type",
			"refresh",
			"roomName",
			"zoneName",            
//			"updatedAt",
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

// hvacUnitId is single hvacUnitId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private def refresh_hvacUnit(hvacUnitId, asyncValues=null) {
	def todayDay = new Date().format("dd",location.timeZone)
	def room
	String zonesList
    
	def structureId=determine_structure_id()    
	hvacUnitId=determine_hvac_unit_id()    

	if (!data?.hvacUnits) {
		data?.hvacUnits=[]
	}        
    
	if (!asyncValues) {
		traceEvent(settings.logFilter, "refresh_hvacUnit>manual refresh", settings.trace, get_LOG_INFO())
		getHvacUnitInfo(hvacUnitId)
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter, "refresh_hvacUnit>$exceptionCheck for hvacUnitId=$hvacUnitId", settings.trace, get_LOG_ERROR())
			return    
		}    
//		getHvacUnitStates(hvacUnitId, 'current-state') // do not get state anymore
	} else {
		traceEvent(settings.logFilter, "refresh_hvacUnit>poll refresh with values=$asyncValues", settings.trace, get_LOG_INFO())
		if (asyncValues.data instanceof Collection) {
 			data?.hvacUnits=asyncValues.data
		} else {
 			data?.hvacUnits[0]=asyncValues.data            
		}            
		
	}    
    
	traceEvent(settings.logFilter,"refresh_hvacUnit> hvacUnits[0]= ${data?.hvacUnits[0]}", settings.trace)    
//	traceEvent(settings.logFilter,"refresh_hvacUnit> hvacUnitStates[0]= ${data?.hvacUnitStates[0]}", settings.trace)    
	def roomId    
	try {    
		roomId=data?.hvacUnits[0]?.relationships?.room?.data?.id    
	} catch (any) {        
		traceEvent(settings.logFilter, "refresh_hvacUnit>roomId not found- hvacUnit $hvacUnitId not associated to a room", settings.trace, get_LOG_WARN())
	}      
	roomId= (roomId)?: device.currentValue("roomId") // get the previous (saved) roomId value if any 
    
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
	def dataEvents = [
 		hvacUnitName:data?.hvacUnits[0]?.attributes?.name,
		hvacUnitMake:data?.hvacUnits[0]?.attributes?.make,
		capabilities:data?.hvacUnits[0]?.attributes?.capabilities,
		switch:data?.hvacUnits[0]?.attributes?.power?.toString().toLowerCase(),
		type: data?.hvacUnits[0]?.attributes?.type,
		makeId: data?.hvacUnits[0]?.attributes?."make-id",
		codesetId:data?.hvacUnits[0]?.attributes?."codeset-id",
		constraints:data?.hvacUnits[0]?.attributes?.constraints.toString(),
//		createdAt:formatDateInLocalTime(data?.hvacUnits[0]?.attributes?."created-at"),
//		updatedAt:formatDateInLocalTime(data?.hvacUnits[0]?.attributes?."updated-at"),
//		setBy:data?.hvacUnitStates[0]?.attributes?."set-by",
//		changeSet:data?.hvacUnitStates[0]?.attributes?."changeset",
		buttonPresses:data?.hvacUnits[0]?.attributes?."button-presses",        
		thermostatMode:data?.hvacUnits[0]?.attributes?.mode?.toLowerCase(),
		airConditionerMode:data?.hvacUnits[0]?.attributes?.mode?.toLowerCase(),
//		humidity: data?.hvacUnits[0]?.attributes?."humidity",
		coolingSetpoint: data?.hvacUnits[0]?.attributes?."temperature",
		coolingSetpointDisplay: data?.hvacUnits[0]?.attributes?."temperature",
		heatingSetpoint: data?.hvacUnits[0]?.attributes?."temperature",
		heatingSetpointDisplay: data?.hvacUnits[0]?.attributes?."temperature",
		thermostatSetpoint: data?.hvacUnits[0]?.attributes?."temperature",
		thermostatSetpointDisplay: data?.hvacUnits[0]?.attributes?."temperature",
		thermostatOperatingState: getHvacOperatingState(),
		fanSpeed: data?.hvacUnits[0]?.attributes?."fan-speed"?.toLowerCase(),
		thermostatFanMode: (data?.hvacUnits[0]?.attributes?."fan-speed" in ['Low','Medium', 'High'])? 'on': 'auto',
		temperature: room?.attributes."current-temperature-c",
		temperatureDisplay: room?.attributes."current-temperature-c",
		swingMode: data?.hvacUnits[0]?.attributes?."swing"?.toLowerCase(),      
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
	generateEvent(dataEvents)
	def supportedThermostatModes=['off', 'fan']
	if (dataEvents?.capabilities?.toLowerCase()?.contains('cool')) {
		supportedThermostatModes << 'cool' 
		        
	}    
	if (dataEvents?.capabilities?.toLowerCase()?.contains('heat')) {
		supportedThermostatModes << 'heat' 
	}   
	if (dataEvents?.capabilities?.toLowerCase()?.contains('both')) {
		supportedThermostatModes << 'cool' << 'heat'
		        
	} 
	def supportedThermostatFanModes=[]
	supportedThermostatFanModes << 'auto' << 'off' << 'on'    
	if ((dataEvents?.constraints?.toLowerCase()?.contains('auto=')) || (dataEvents?.constraints?.toLowerCase()?.contains('"auto":')) || 
		(dataEvents?.constraints?.toLowerCase()?.contains('"auto="'))) {
		supportedThermostatModes << 'auto' 
	}    
	if (dataEvents?.constraints?.toLowerCase()?.contains('dry')) {
		supportedThermostatModes << 'dry' 
	}    
	if (dataEvents?.constraints?.toLowerCase()?.contains('fan hi')) {
		supportedThermostatFanModes << 'high' 
	}    
	if (dataEvents?.constraints?.toLowerCase()?.contains('fan mid')) {
		supportedThermostatFanModes << 'medium' 
	}    
	if (dataEvents?.constraints?.toLowerCase()?.contains('fan low')) {
		supportedThermostatFanModes << 'low' 
	}    
    
	def isChanged= isStateChange(device, "supportedThermostatModes", supportedThermostatModes?.toString())    
	sendEvent(name: "supportedThermostatModes", value: supportedThermostatModes, isStateChange: isChanged, displayed: (settings.trace?:false))	
	isChanged= isStateChange(device, "supportedThermostatFanModes", supportedThermostatFanModes?.toString())    
	sendEvent(name: "supportedThermostatFanModes", value: supportedThermostatFanModes, isStateChange: isChanged, displayed: (settings.trace?:false))	
	state?.supportedThermostatFanModes= supportedThermostatFanModes
	state?.supportedThermostatModes= supportedThermostatModes
    
	traceEvent(settings.logFilter,"refresh_hvacUnit>done for hvacUnitId =${hvacUnitId}", settings.trace)
}


// @Get the basic hvacUnit status (heating,cooling,fan only)
// @To be called after a poll() or refresh() to have the latest status

def getHvacOperatingState() {
	def results
    
	def roomId= (roomId)?: device.currentValue("roomId") // get the previous (saved) roomId value if any 
	def room
	double actualTemp
    
	if (roomId) {    
		room=getRoom(roomId)    
		actualTemp =(room?.attributes?."current-temperature-c") ? room?.attributes?."current-temperature-c"?.toDouble() : (state?.scale=='C')?21:73
	}
	double coolSP= data?.hvacUnits[0]?.attributes?."temperature".toDouble()
	double heatSP= data?.hvacUnits[0]?.attributes?."temperature".toDouble()
	def mode = (data?.hvacUnits[0]?.attributes?.mode)? data?.hvacUnits[0]?.attributes?.mode.toLowerCase():'off'
    
	if (device.currentValue("switch") == 'on') {
		results = (mode in ['cool', 'dry'] && (actualTemp < coolSP))?'cooling':(mode.contains('heat') && (actualTemp < heatSP))?'heating':(mode.contains('fan'))?'fan only':'idle'
	} else {
		results = 'idle'	       
	}
           
	return results                    
}


// @refresh() has a different polling interval as it is called by the UI (contrary to poll).
void refresh() {
	def hvacUnitId= determine_hvac_unit_id("") 	    
	def poll_interval=0.5   // set a 30 sec. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"refresh>hvacUnitId = ${hvacUnitId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
 			"not refreshing data...", settings.trace)
		return
	}
	refresh_hvacUnit(hvacUnitId)
	state.lastPollTimestamp = now()
  
}

void on() {
	def FLAIR_ON='On'
	def ST_ON=FLAIR_ON.toLowerCase()
    
	def hvacUnitId= determine_hvac_unit_id("") 	   
	traceEvent(settings.logFilter, "on>begin for hvacUnitId=${hvacUnitId}", settings.trace)
	setHvacUnit("", ['power': FLAIR_ON])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "on>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "switch", value: ST_ON)
	sendEvent(name: "thermostatOperatingState", value:getHvacOperatingState())    
	traceEvent(settings.logFilter, "on>done for hvacUnitId=${hvacUnitId}", settings.trace)
}

void off() {
	def FLAIR_OFF='Off'
	def ST_OFF= FLAIR_OFF.toLowerCase()

	def hvacUnitId= determine_hvac_unit_id("") 	   
	traceEvent(settings.logFilter, "off>begin for hvacUnitId=${hvacUnitId}", settings.trace)
	setHvacUnit("", ['power': FLAIR_OFF])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "off>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "switch", value: ST_OFF)
	sendEvent(name: "thermostatOperatingState", value:getHvacOperatingState())    
	traceEvent(settings.logFilter, "off>done for hvacUnitId=${hvacUnitId}", settings.trace)
}



void poll() {
	String URI_ROOT = "${get_URI_ROOT()}"
	def hvacUnitId= determine_hvac_unit_id("") 	    

	def poll_interval=1   // set a minimum of 1 min. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"poll>hvacUnitId = ${hvacUnitId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
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
    
	traceEvent(settings.logFilter,"poll>about to call pollAsyncResponse for hvacUnitId = ${hvacUnitId}...", settings.trace)
//	log.debug "poll>about to call pollAsyncResponse for hvacUnitId = ${hvacUnitId}..."
//	log.debug "poll>data.auth= ${data.auth}..."
//	log.debug "poll>settings= ${settings}..."
	    
	def params = [
		uri: "${URI_ROOT}/api/hvac-units/${hvacUnitId}",
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//			'Content-Type': "text/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"

		]
	]
	asynchttp_v1.get('pollAsyncResponseHvacInfo', params)

/*
Do not get the state anymore

	params = [
			uri: "${URI_ROOT}/api/hvac-units/${hvacUnitId}/current-state?page[size]=1",
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
				'Content-Type': "text/json",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	asynchttp_v1.get('pollAsyncResponseHvacStates', params)
*/    
	traceEvent(settings.logFilter, "poll>done for hvacUnitId = ${hvacUnitId}", settings.trace)
}

def pollAsyncResponseHvacInfo(response, data) {	
	def TOKEN_EXPIRED=401
	String URI_ROOT = "${get_URI_ROOT()}"
    
 
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponseHvacInfo>Flair's Access token has expired, trying to refresh tokens now...", settings.trace,get_LOG_WARN())
			refresh_tokens()      
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseHvacInfo>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseHvacInfo>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
        
	} else {
		def responseValues=null    
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponseHvacInfo>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
			traceEvent(settings.logFilter,"pollAsyncResponseHvacInfo>responseValues=$responseValues", settings.trace,get_LOG_TRACE())
			def id = responseValues.data?.attributes?.id
			if (settings.trace) {                
				def name = responseValues.data?.attributes?.name
				def make = responseValues.data?.attributes?.make
				def type = responseValues.data?.attributes?.type
				traceEvent(settings.logFilter,"pollAsyncResponseHvacInfo>hvacUnitId=${id}, name= $name, make=$make, type=$type")
			}     
			refresh_hvacUnit(id,responseValues)            
			retrieveDataForGraph()            
			state.lastPollTimestamp = now()
			state?.exceptionCount=0                
		}        
	}        
}

def pollAsyncResponseHvacStates(response, data) {	
	def TOKEN_EXPIRED=401
  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponseHvacStates>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponseHvacStates>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponseHvacStates>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues=null     
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponseHvacStates>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
            
			traceEvent(settings.logFilter,"pollAsyncResponseHvacStates>responseValues=$responseValues", settings.trace,get_LOG_TRACE())
			def hvacUnitId = responseValues?.data?.relationships?."current-for"?.data?.id       
			if (settings.trace) {
				def createdAt = responseValues.data?.attributes?."created-at"
				def mode = responseValues.data?.attributes?.mode
				def fanMode = responseValues.data?.attributes?."fan-speed"
//				def operatingState = responseValues.data?.attributes?."operating-state"
				def coolingSetpoint= responseValues.data?.attributes?."temperature"
				def heatingSetpoint=  responseValues.data?.attributes?."temperature"
				def targetTemperature = responseValues.data?.attributes?."temperature"
//				def temperature = responseValues.data?.attributes?."ambient-temperature-c"
//				def humidity = responseValues.data?.attributes?."humidity"
				traceEvent(settings.logFilter,"pollAsyncResponseHvacStates>hvacUnitId=${hvacUnitId},mode=$mode,createdAt=$createdAt,targetTemperature= $targetTemperature," +
							"fanMode=$fanMode,coolingSetpoint=$coolingSetpoint,heatingSetpoint=$heatingSetpoint",                    
					settings.trace)     
			}
			refresh_hvacUnitStates(hvacUnitId,responseValues)            
			retrieveDataForGraph()            
			state.lastPollTimestamp = now()
			state?.exceptionCount=0
		}                
                
	}        
}

// @hvacUnitId is single hvacUnitId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private void refresh_hvacUnitStates(hvacUnitId, asyncValues=null) {

	hvacUnitId=determine_hvac_unit_id(hvacUnitId)
	traceEvent(settings.logFilter,"refresh_hvacUnitStates>about to refresh $hvacUnitId based on $asyncValues", settings.trace)

	if (!data?.hvacUnitStates) {    
		data?.hvacUnitStates=[]
	}    

	if (!asyncValues) {
		getHvacUnitStates(hvacUnitId, 'current-state')
    } else {
		if (asyncValues.data instanceof Collection) {                
			data?.hvacUnitStates=asyncValues.data
		} else {
			data?.hvacUnitStates[0]=asyncValues.data
		}                
	}
	traceEvent(settings.logFilter,"refresh_hvacUnit> hvacUnitStates[0]= ${data?.hvacUnitStates[0]}", settings.trace)    

	def dataEvents = [
		switch: data?.hvacUnitStates[0]?.attributes?.power?.toString().toLowerCase(),
		setBy:data?.hvacUnitStates[0]?.attributes?."set-by",
		changeSet:data?.hvacUnitStates[0]?.attributes?."changeset",
		buttonPresses:data?.hvacUnitStates[0]?.attributes?."button-presses",        
		thermostatMode:data?.hvacUnitStates[0]?.attributes?.mode?.toLowerCase(),
		airConditionerMode:data?.hvacUnitStates[0]?.attributes?.mode?.toLowerCase(),
//		humidity: data?.hvacUnitStates[0]?.attributes?."humidity",
		coolingSetpoint: data?.hvacUnitStates[0]?.attributes?."temperature",
		coolingSetpointDisplay: data?.hvacUnitStates[0]?.attributes?."temperature",
		heatingSetpoint: data?.hvacUnitStates[0]?.attributes?."temperature",
		heatingSetpointDisplay: data?.hvacUnitStates[0]?.attributes?."temperature",
		thermostatSetpoint: data?.hvacUnitStates[0]?.attributes?."temperature",
		thermostatSetpointDisplay: data?.hvacUnitStates[0]?.attributes?."temperature",
		thermostatOperatingState: getHvacOperatingState(),
		fanSpeed: data?.hvacUnitStates[0]?.attributes?."fan-speed"?.toLowerCase(),
		thermostatFanMode: ((data?.hvacUnitStates[0]?.attributes?."fan-speed" in ['Low','Medium', 'High'])? 'on': 'auto')
	]
          
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_hvacUnitStates>done for hvacUnitId =${hvacUnitId}", settings.trace)
}

private void generateEvent(Map results) {
    
	state?.scale = getTemperatureScale() // make sure to display in the right scale
	def scale = state?.scale
    
	if (results) {
		results.each { name, value ->
			def isDisplayed = true

			String upperFieldName=name.toUpperCase()
// 			Temperature variable names for display contain 'display'            

			if (upperFieldName.contains("DISPLAY")) {  
				double tempValue
				String tempValueString            
                
				if (scale == "C") {
                
					if (upperFieldName.contains("DESIRED")) { 
						tempValue = getTemperatureValue(value).round(1)
						tempValueString = String.format('%2.1f', tempValue)
						if (tempValueString.matches(".*([.,][3456])")) {                
							tempValueString=String.format('%2d.5', tempValue.intValue())                
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
						} else if (tempValueString.matches(".*([.,][789])")) {  
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
							tempValue=tempValue.intValue() + 1                        
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						} else {
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						}
						tempValue= tempValueString.toDouble().round(1)                        
					}
				                    
					if (upperFieldName.contains("SETPOINT")) { 
						tempValue = value?.toDouble().round(1)
						tempValueString = String.format('%2.1f', tempValue)
						if (tempValueString.matches(".*([.,][3456])")) {                
							tempValueString=String.format('%2d.5', tempValue.intValue())                
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
						} else if (tempValueString.matches(".*([.,][789])")) {  
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
							tempValue=tempValue.intValue() + 1                        
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						} else {
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						}
						tempValue= tempValueString.toDouble().round(1)                        
					}
				                    
				} else {
					tempValue = value?.toDouble().round(0)
					tempValueString = String.format('%2d', tempValue.intValue())            
				}
				def isChange = isStateChange(device, name, tempValueString)
                
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed)                                     									 

// 			Temperature variable names contain 'temp' or 'setpoint' (not for display)           

			} else if (upperFieldName.contains("SETPOINT")) { 
				double tempValue = value?.toDouble().round(0)
				String tempValueString = String.format('%2.1f', tempValue)
				if (scale == "C") {
					if (tempValueString.matches(".*([.,][3456])")) {                
						tempValueString=String.format('%2d.5', tempValue.intValue())                
						traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
					} else if (tempValueString.matches(".*([.,][789])")) {  
						traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
						tempValue=tempValue.intValue() + 1                        
						tempValueString=String.format('%2d.0', tempValue.intValue())               
					} else {
						traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
						tempValueString=String.format('%2d.0', tempValue.intValue())               
					}
				}
				tempValue= tempValueString.toDouble().round(1)                        
				def isChange = isStateChange(device, name, tempValueString)
                
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed)                                     									 
			} else if (((upperFieldName.contains("TEMP"))  || (upperFieldName.contains("DESIRED")))) {  
                                
				double tempValue = getTemperatureValue(value).round(1)
				String tempValueString = String.format('%2.1f', tempValue)
				if (scale == "C") {
					if (upperFieldName.contains("DESIRED")) { 
						if (tempValueString.matches(".*([.,][3456])")) {                
							tempValueString=String.format('%2d.5', tempValue.intValue())                
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
						} else if (tempValueString.matches(".*([.,][789])")) {  
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
							tempValue=tempValue.intValue() + 1                        
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						} else {
							traceEvent(settings.logFilter,"generateEvent>$name has value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
							tempValueString=String.format('%2d.0', tempValue.intValue())               
						}
						tempValue= tempValueString.toDouble().round(1)                        
					}
				}
                    
				if (tempValueString != '0.0') {    // don't post value if all zeros                
					def isChange = isStateChange(device, name, tempValueString)
					isDisplayed = isChange
					sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed, isStateChange: isChange)
				}
                    
			} else if (upperFieldName.contains("HUMIDITY")) {
				value=(value?:0)
				double humValue = value?.toDouble().round(0)
				String humValueString = String.format('%2d', humValue.intValue())
				def isChange = isStateChange(device, name, humValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: humValueString, unit: "%", displayed: isDisplayed, isStateChange: isChange)
			} else if (upperFieldName.contains("DATA")) { // data variable names contain 'data'

				sendEvent(name: name, value: value, displayed: (settings.trace?:false))                                     									 

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
		'getHvacInfo': 
			[uri:"${URI_ROOT}/api/hvac-units/${id}", 
				type:'get'],
		'getHvacStates': 
			[uri:"${URI_ROOT}/api/hvac-units/${id}/hvac-unit-states", 
				type:'get'],
		'current-state': [uri: "${URI_ROOT}/api/hvac-units/${id}/current-state",
				type: 'get'],
		'previous-state': [uri: "${URI_ROOT}/api/hvac-units/${id}/hvac-unit-states",
				type: 'get'],
		'setHvacUnitState': [uri: "${URI_ROOT}/api/hvac-unit-states", 
				type: 'post']
	]        
	def request = methods.getAt(method)
	if (request.type=="get" && args) {
		def args_encoded = java.net.URLEncoder.encode(args.String(), "UTF-8")
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
			'Content-Type': "application/json",
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



void levelDown() {
	def capabilities=device.currentValue("capabilities")?.toUpperCase()
	if ((capabilities in ['HEAT', 'BOTH'])) {
		heatLevelDown()
	} 
	if ((capabilities in ['COOL', 'BOTH'])) {
		coolLevelDown()
	} 

}

void levelUp() {
	def capabilities=device.currentValue("capabilities")?.toUpperCase()
	if ((capabilities in ['HEAT', 'BOTH'])) {
		heatLevelUp()
	} 
	if ((capabilities in ['COOL', 'BOTH'])) {
		coolLevelUp()
	} 

}
void coolLevelUp() {
	def scale = state?.scale
	if (scale == 'C') {
		double nextLevel = device.currentValue("coolingSetpoint").toDouble() 
		nextLevel = (nextLevel + 0.5).round(1)        
		if (nextLevel > 30) {
			nextLevel = 30
		}
		setCoolingSetpoint(nextLevel)
	} else {
		int nextLevel = device.currentValue("coolingSetpoint").toInteger() 
		nextLevel = nextLevel + 1    
		if (nextLevel > 99) {
			nextLevel = 99
		}
		setCoolingSetpoint(nextLevel)
	}
}


void coolLevelDown() {
	def scale = state?.scale
	if (scale == 'C') {
		double nextLevel = device.currentValue("coolingSetpoint").toDouble() 
		nextLevel = (nextLevel - 0.5).round(1)        
		if (nextLevel < 10) {
			nextLevel = 10.0
		}
		setCoolingSetpoint(nextLevel)
	} else {
		int nextLevel = device.currentValue("coolingSetpoint").toInteger()  
		nextLevel = (nextLevel - 1)
		if (nextLevel < 50) {
			nextLevel = 50
		}
		setCoolingSetpoint(nextLevel)
	}
}
void heatLevelUp() {
	def scale = state?.scale
	if (scale == 'C') {
		double nextLevel = device.currentValue("heatingSetpoint").toDouble() 
		nextLevel = (nextLevel + 0.5).round(1)        
		if (nextLevel > 30) {
			nextLevel = 30.0
		}
		setHeatingSetpoint(nextLevel)
	} else {
		int nextLevel = device.currentValue("heatingSetpoint").toInteger()  
		nextLevel = (nextLevel + 1)
		if (nextLevel > 99) {
			nextLevel = 99
		}
		setHeatingSetpoint(nextLevel)
	}
}
void heatLevelDown() {
	def scale = state?.scale
	if (scale == 'C') {
		double nextLevel = device.currentValue("heatingSetpoint").toDouble() 
		nextLevel = (nextLevel - 0.5).round(1)        
		if (nextLevel < 10) {
			nextLevel = 10.0
		}
		setHeatingSetpoint(nextLevel)
	} else {
		int nextLevel = device.currentValue("heatingSetpoint").toInteger() 
		nextLevel = (nextLevel - 1)
		if (nextLevel < 50) {
			nextLevel = 50
		}
		setHeatingSetpoint(nextLevel)
	}
}

// handle commands


void setHeatingSetpoint(temp) {
	def capabilities=device.currentValue("capabilities")?.toUpperCase()
	if (!(capabilities in ['HEAT', 'BOTH'])) {
		traceEvent(settings.logFilter,"setHeatingSetpoint to ${temp} not possible, device's capability is only ${capabilities}", settings.trace, get_LOG_WARN())
		return    
	} 
	if (!temp.isNumber()) {
		traceEvent(settings.logFilter,"setHeatingSetpoint>${temp} is not a number ", settings.trace, get_LOG_ERROR())
		return    
	} 
	def tempValue = temp
	def scale = state?.scale
/*    
	if (state?.scale !='C') {
		tempValue = fToC(temp)    
	} 
*/    
	setHvacUnit("", ['temperature':tempValue])
/*
	setRoomSetpoint(temp)

	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck.contains("done"))) {
		return    
	}    
*/    
	sendEvent(name: 'heatingSetpoint', value: temp,unit: scale,isStateChange:true)
	sendEvent(name: 'heatingSetpointDisplay', value: temp,unit: scale,isStateChange:true)
	sendEvent(name: 'thermostatSetpoint', value: temp,unit: scale,isStateChange:true)     
	sendEvent(name: 'thermostatSetpointDisplay', value: temp,unit: scale,isStateChange:true)     
    
        
}


void setCoolingSetpoint(temp) {
	def capabilities=device.currentValue("capabilities")?.toUpperCase()
	if (!(capabilities in ['COOL', 'BOTH'])) {
		traceEvent(settings.logFilter,"setCoolingSetpoint to ${temp} not possible, device's capability is only ${capabilities}", settings.trace, get_LOG_WARN())
		return    
	} 
	if (!temp.isNumber()) {
		traceEvent(settings.logFilter,"setCoolingSetpoint>${temp} is not a number ", settings.trace, get_LOG_ERROR())
		return    
	} 
	def scale = state?.scale
    def tempValue = temp
/*    
	if (state?.scale !='C') {
		tempValue = fToC(temp)    
	} 
*/    
	setHvacUnit("", ['temperature':tempValue])
/*
	setRoomSetpoint(temp)

	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck.contains("done"))) {
		return    
	}    
*/    
    
	sendEvent(name: 'coolingSetpoint', value: temp, unit: scale, isStateChange:true)
	sendEvent(name: 'coolingSetpointDisplay', value: temp, unit: scale, isStateChange:true)
	sendEvent(name:'thermostatSetpoint', value: temp, unit: scale,isStateChange:true)     
	sendEvent(name: 'thermostatSetpointDisplay', value: temp,unit: scale,isStateChange:true)     
}
void auto() {
	setThermostatMode('auto')
}
void heat() {
	setThermostatMode('heat')
}
void emergencyHeat() {
	setThermostatMode('heat')
}
void cool() {
	setThermostatMode('cool')
}
void dry() {
	setThermostatMode('dry')
}

void fanOnly() {
	setThermostatMode('fan')
}

void fanOn() {
	setThermostatFanMode('low')
}
void fanAuto() {
	setThermostatFanMode('auto')
}
void fanOff() {   // fanOff is not supported, setting it to 'auto' instead.
	setThermostatFanMode('auto')
}
def fanCirculate() { // fanCirculate is not supported, setting it to 'low' instead.
	fanLow()
}

void fanLow() {
	setThermostatFanSpeed('low')
}

void fanMedium() {
	setThermostatFanSpeed('medium')
}
void fanHigh() {
	setThermostatFanSpeed('high')
}

void setFanSpeed(int fanSpeed) {
	if (fanSpeed==0) {
		fanOff()   
	} else if (fanSpeed==1) {
		fanLow()    
	} else if (fanSpeed==2) {
		fanMedium()    
	} else if (fanSpeed==3) {
		fanHigh()    
	} else {
		traceEvent(settings.logFilter,"setFanSpeed>fan speed is not supported, range=[0..3]",
			true, get_LOG_WARN(), true)
		return            
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck.contains("done"))) {
		return    
	}
	sendEvent(name: 'fanSpeed', value: fanSpeed ,isStateChange:true)
}

void setThermostatFanSpeed(mode) {
	setThermostatFanMode(mode)
}

def getSupportedFanModes() {

	if (!state?.supportedThermostatFanModes) {	
		state?.supportedThermostatFanModes= (device.currentValue("supportedThermostatFanModes"))? 
			device.currentValue("supportedThermostatFanModes").toString().minus('[').minus(']').tokenize(',') : ['off','on','auto', 'low','medium','high']
  
	}
    
	return state?.supportedThermostatFanModes
}    

def getSupportedThermostatModes() {

	if (!state?.supportedThermostatModes) {	
		state?.supportedThermostatModes = (device.currentValue("supportedThermostatModes")) ?
			device.currentValue("supportedThermostatModes").toString().minus('[').minus(']').tokenize(',') : ['off','heat', 'cool', 'auto', 'dry']
	}
    
	return state?.supportedThermostatModes
}


// @mode can be 'on', 'low', 'medium', 'high', 'auto'

void setThermostatFanMode(mode) {	
	mode= mode.toLowerCase()
	def st_mode = (mode in ['on', 'low', 'medium', 'high'])? 'on' : 'auto'
	def supportedThermostatFanModes = getSupportedFanModes()
	if (mode in supportedThermostatFanModes) {
		setHvacUnit("", ['fan-speed': "${mode.capitalize()}"]) // capitalize the fan mode  before sending it to Flair
		def exceptionCheck=device.currentValue("verboseTrace")
		if ((exceptionCheck) && (!exceptionCheck.contains("done"))) {
			return    
		}
	} else {
		traceEvent(settings.logFilter,"setThermostatFanMode>fan $mode mode is not supported in $supportedThermostatFanModes",
			true, get_LOG_WARN(), true)
	}    
        
	sendEvent(name: 'thermostatFanMode', value: st_mode,isStateChange:true)
	sendEvent(name: 'thermostatFanSpeed', value: mode,isStateChange:true)
}

void setAirConditionerMode(mode) {

	setThermostatMode(mode)
   
}
// @mode can be 'heat', 'cool', 'dry', 'auto', 'fan', 'off'
void setThermostatMode(mode) {	
	mode= mode.toLowerCase()
	def supportedThermostatModes = getSupportedThermostatModes()

	def flairMode = (mode == 'emergency heat') ? 'Heat' : mode.capitalize()

	if (mode in supportedThermostatModes) {
		traceEvent(settings.logFilter,"setThermostatMode>about to set $mode",
			true, get_LOG_TRACE())    
		setHvacUnit("", ['mode': "${flairMode}"]) // capitalize the thermostat mode before sending it to Flair
		def exceptionCheck=device.currentValue("verboseTrace")
		if ((exceptionCheck) && (!exceptionCheck.contains("done"))) {
			return    
		}    
	} else {   
		traceEvent(settings.logFilter,"setThermostatMode>cannot change tstat mode as $mode is not supported in $supportedThermostatModes",
			true, get_LOG_WARN(), true)
	}

	sendEvent(name: 'thermostatMode', value: mode,isStateChange:true)
	sendEvent(name: 'airConditionerMode', value: mode,isStateChange:true) 
}

void swingOn() {
	setSwingMode('on')
}

void swingOff() {
	setSwingMode('off')
}

void swingTop() {
	setSwingMode('top')
}

// @mode must be 'top', 'off', 'on'

void setSwingMode(mode) {
	mode= mode.toLowerCase()
	setHvacUnit("", ['swing': "${mode.capitalize()}"]) // capitalize the fan mode  before sending it to Flair
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck.contains("done"))) {
		return    
	}    
	sendEvent(name: 'swingMode', value: st_mode,isStateChange:true)
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


//	@roomId		Id of the Room, by default the current one
//	@attribute	Room Attribute(s) set to be changed in a Map
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



// @id			Id of the hvacUnit, by default the current one
// @stateAttribute	HvacUnit Attribute(s) to be changed in a Map
void setHvacUnitState(hvacUnitId, stateAttributes=[]) {
	def FLAIR_SUCCESS =200
	def FLAIR_CREATED =201    
	def TOKEN_EXPIRED=401


	hvacUnitId = determine_hvac_unit_id(hvacUnitId)
	if (stateAttributes == null || stateAttributes == "" || stateAttributes == [] ) {
		traceEvent(settings.logFilter, "setHvacUnitState>currentState object is empty, exiting", settings.trace)
		return        
	}
    
	def currentStateAttributes =new groovy.json.JsonBuilder(stateAttributes) 
	def bodyReq = '{"data":{"type":"hvac-unit-states","attributes": ' + currentStateAttributes +',"relationships":{"hvac-unit":{"data":{"type":"hvac-units","id":"' + hvacUnitId + '"}}}}}'

	traceEvent(settings.logFilter, "setHvacUnitState>about to call setHvacUnitState api with bodyReq=${bodyReq}", settings.trace)
	int statusCode = 1
	int j = 0
	while ((statusCode != FLAIR_SUCCESS && statusCode != FLAIR_CREATED) && (j++ < 2)) { // retries once if api call fails
		api('setHvacUnitState', hvacUnitId, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "setHvacUnitState>hvacUnitId=${hvacUnitId}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()           
			}
			if (statusCode in [FLAIR_SUCCESS, FLAIR_CREATED]) {
				/* when success, reset the exception counter */
				state.exceptionCount = 0
				traceEvent(settings.logFilter, "setHvacUnitState>done for ${hvacUnitId}", settings.trace)
				log.debug( "setHvacUnitState>done for ${hvacUnitId}")
				state.lastPollTimestamp = now()  // To avoid polling right after setting the vent level
			} else {
				state.exceptionCount = state.exceptionCount + 1
				traceEvent(settings.logFilter, "setHvacUnitState>error=${statusCode.toString()} for ${hvacUnitId}", settings.trace)
				log.debug("setHvacUnitState>error=${statusCode.toString()} for ${hvacUnitId}")
			} /* end if statusCode */
		} /* end api call */
	} /* end while */
}

// @hvacUnitId	Id of the hvacUnit, by default the current one
// @attribute	HvacUnit Attribute(s) to be changed in a Map
void setHvacUnit(hvacUnitId, attributes=[]) {
	String URI_ROOT = "${get_URI_ROOT()}"

	if (!hvacUnitId) {
		hvacUnitId=device.currentValue("hvacUnitId")
    
	}    
	if (attributes == null || attributes == "" || attributes == [] ) {
		traceEvent(settings.logFilter, "setHvacUnit>attributes set is empty, exiting", settings.trace)
		return        
	}
    
	def currentAttributes =new groovy.json.JsonBuilder(attributes) 
	def bodyReq = '{"data":{"id":"' + hvacUnitId + '","type":"hvac-units","attributes":' + currentAttributes +'}}'
	def args = [
			attributes: bodyReq
		]
    
	def params = [
			uri: "${URI_ROOT}/api/hvac-units/${hvacUnitId}",
			body: bodyReq,            
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
				'Content-Type': "text/json",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	traceEvent(settings.logFilter, "setHvacUnit>about to call patchObjectAsync with bodyReq=${bodyReq}", settings.trace)
	asynchttp_v1.patch('patchObjectAsync', params, args)
	traceEvent(settings.logFilter, "setHvacUnit>done for hvacUnitId=${hvacUnitId}", settings.trace)
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
			traceEvent(settings.logFilter,"patchObjectAsync> -  patch api call with attributes $attributes failed, Error: $response.status",settings.trace,get_LOG_ERROR())
			state?.exceptionCount=state?.exceptionCount +1        
		}
        
	} else {
		if (response.status == FLAIR_SUCCESS) {
			traceEvent(settings.logFilter,"patchObjectAsync> - patch api call with attributes $attributes sent successfully",settings.trace)
//			log.debug("patchObjectAsync> - patch api call with attributes $attributes sent successfully")
			state?.exceptionCount=0
			state.lastPollTimestamp = now()
		}
	}
}



// @id		Id of the hvacUnit, by default, retrieve all hvacUnits under a user 
//@postData	indicates whether the data should be posted for further processing
void getHvacUnitInfo(id, postData=false) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	def hvacUnitData = []
	def hvacUnitList = ""
	def bodyReq = ""
	def statusCode = true
	int j = 0

	traceEvent(settings.logFilter, "getHvacUnitInfo>bodyReq=${bodyReq},id=$id", settings.trace)

	if (!data?.hvacUnits) {
		data?.hvacUnits=[]    
	}    
	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getHvacInfo', id, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getHvacUnitInfo>hvacUnitId=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
			
				traceEvent(settings.logFilter, "getHvacUnitInfo>resp.data=${resp.data}", settings.trace)
				if (resp.data.data instanceof Collection) {
					data?.hvacUnits =resp.data.data
				} else {
					data?.hvacUnits[0] =resp.data.data
				}
				data?.hvacUnits?.each {
					def hvacUnitId = it?.id
					def name = it?.attributes?.name
					def make = it?.attributes?.make
					def type = it?.attributes?.type
					def hvacUnitStates = it?.relationships?."hvacUnit-states"
					def room = it.relationships?.room                         
                        
//					log.debug "getHvacUnitInfo>hvacUnitId=${hvacUnitId}, name= $name, make=$make, type=$type," +
// 						"room=$room, hvacUnitStates=$hvacUnitStates"
					traceEvent(settings.logFilter, "getHvacUnitInfo>hvacUnitId=${hvacUnitId}, name= $name, make=$make,type=$type," +
 						"room=$room, hvacUnitStates=$hvacUnitStates",settings.trace)
					if (postData) {
						traceEvent(settings.logFilter, "getVentInfo>adding ${it} to ventData", settings.trace)
						hvacUnitData << it // to be transformed into Json later
					}
					hvacUnitList = hvacUnitList + hvacUnitId + ','
				} /* end for each hvacUnit */
				traceEvent(settings.logFilter, "getHvacUnitInfo>done for hvacUnitId=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getHvacUnitInfo>error=${statusCode.toString()} for hvacUnitId=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	def hvacUnitDataJson = ""

	if (hvacUnitData != []) {
		statDataJson = new groovy.json.JsonBuilder(hvacUnitData)
	}
	/*	
	traceEvent(settings.logFilter,"getHvacUnitInfo>ventDataJson=${ventDataJson}", settings.trace)
	*/
	def hvacUnitEvents = [
			hvacUnitData: "${hvacUnitDataJson.toString()}",
			hvacUnitList: "${hvacUnitList.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"getVentInfo>ventListEvents to be sent= ${ventListEvents}", settings.trace)
	*/
	generateEvent(hvacUnitEvents)

}

// @id			Id of the hvacUnit object, by default the current hvacUnit
// @method		possible values: current-state, previous-state, by default=all states
// @postData		indicates whether the data should be posted for further processing  [optional]
// @postTimestamp	timestamp (from) threshold to post states [optional]

void getHvacUnitStates(id, method="", postData=false,postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	String FLAIR_CURRENT_STATE="current-state"    
	String FLAIR_PREVIOUS_STATE="previous-state"   
	String FLAIR_ALL_STATES="getHvacUnitStates"    
	def hvacUnitData = []
	def statusCode = true
	def MAX_INDICE=5   
	int j = 0

	if (!id) {
		id = device.currentValue("hvacUnitId")
		if (!id) {        	
			traceEvent(settings.logFilter, "getHvacUnitStates>id=$id exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	def bodyReq = ""
	if (!data?.hvacUnitStates) {
		data?.hvacUnitStates=[]    
	}    

	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_STATE)?FLAIR_CURRENT_STATE:((method==FLAIR_PREVIOUS_STATE)?FLAIR_PREVIOUS_STATE:FLAIR_ALL_STATES)   
/*
	if (method in [FLAIR_CURRENT_STATE, FLAIR_PREVIOUS_STATE] ) {
		bodyReq=bodyReq + "page[size]=1"    
	}
*/       
	traceEvent(settings.logFilter, "getHvacUnitStates>method=$method, bodyReq=${bodyReq}, id=$id", settings.trace)

	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api(method, id, bodyReq) {resp->
			statusCode = resp.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getHvacUnitStates>id=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getHvacUnitStates>resp.data=${resp.data}", settings.trace)
				if (resp.data.data instanceof Collection) {
					data?.hvacUnitStates =resp.data.data
				} else {
					data?.hvacUnitStates[0] =resp.data.data
				}
				int max_ind = (data?.hvacUnitStates) ? (data?.hvacUnitStates.size()-1): -1				                
				max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
				for (i in 0..max_ind) {
//					def createdAt = data?.hvacUnitStates[i]?.attributes?."created-at"
					def targetTemperature = data?.hvacUnitStates[i]?.attributes?."temperature"
					def mode = data?.hvacUnitStates[i]?.attributes?.mode
					def fanMode = data?.hvacUnitStates[i]?.attributes?."fan-speed"
//					def operatingState = data?.hvacUnitStates[i]?.attributes?."operating-state"
					def hvacUnitId = data?.hvacUnitStates[i]?.attributes?.data?.id
					def coolingSetpoint= data?.hvacUnitStates[i]?.attributes?."temperature"
					def heatingSetpoint=  data?.hvacUnitStates[i]?.attributes?."temperature"
//					def temperature=  data?.hvacUnitStates[i]?.attributes?."temperature"
//					def humidity=  data?.hvacUnitStates[i]?.attributes?.humidity
					traceEvent(settings.logFilter,"getHvacUnitStates>hvacUnitId=${id}, state no ${i}, mode=$mode,targetTemperature= $targetTemperature," +
								"fanMode=$fanMode,coolingSetpoint=$coolingSetpoint,heatingSetpoint=$heatingSetpoint",                    
						settings.trace)     

					if (method == FLAIR_CURRENT_STATE) {
						if (postData) { 
							hvacUnitData << data?.hvacUnitStates[i]
						}
						break                   
					} else if ((method == FLAIR_PREVIOUS_STATE) && (((max_ind > 0) && (i==1)) || (max_ind==0))) {
						if (postData) { 
							hvacUnitData =[] // just generate the json for the last row
							hvacUnitData << data?.hvacUnitStates[i]
						}                            
						break                   
					} else if (postData) {
						if (postTimestamp) {
							// Save states greater than timestamp 
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getHvacUnitStates>adding ${data?.hvacUnitStates[i]} to hvacUnitData, createdDate (${createdDate.getTime()}) is greater than timestamp ($postTimestamp)", settings.trace)
								hvacUnitData << data?.hvacUnitStates[i] // to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getHvacUnitStates>adding ${data?.hvacUnitStates[i]} to hvacUnitData", settings.trace)
							hvacUnitData << data?.hvacUnitStates[i]// to be transformed into Json later
						}
					}
				} /* end for each state  */
				traceEvent(settings.logFilter, "getHvacUnitStates>done for id=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getHvacUnitStates>error=${statusCode.toString()} for id=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	generate_hvac_state_json()
}


private def generate_hvac_state_json(hvacUnitData) {
	def hvacUnitDataJson = ""

	if (hvacUnitData != []) {
		hvacUnitDataJson = new groovy.json.JsonBuilder(hvacUnitData)
	}
	/*	
	traceEvent(settings.logFilter,"generate_tstat_state_json>ventDataJson=${hvacUnitDataJson}", settings.trace)
	*/
	def hvacUnitEvents = [
			hvacUnitStatesData: "${hvacUnitDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"generate_tstat_state_json>hvacUnitEvents to be sent= ${hvacUnitEvents}", settings.trace)
	*/
	generateEvent(hvacUnitEvents)
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

	if (data?.auth?.hvacUnitId) {
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
private def determine_hvac_unit_id(hvac_unit_id) {
	def hvacUnitId=device.currentValue("hvacUnitId")
    
	if ((hvac_unit_id != null) && (hvac_unit_id != "")) {
		hvacUnitId = hvac_unit_id
	} else if ((settings.hvacUnitId != null) && (settings.hvacUnitId  != "")) {
		hvacUnitId = settings.hvacUnitId.trim()
		traceEvent(settings.logFilter,"determine_hvac_unit_id> hvacUnitId from settings = ${settings.hvacUnitId}", settings.trace)
	} else if (data?.auth?.hvacUnitId) {
		settings?.hvacUnitId = data?.auth?.hvacUnitId
		traceEvent(settings.logFilter,"determine_hvac_unit_id> hvacUnitId from data.auth = ${data?.auth?.hvacUnitId}", settings.trace)
	} else if ((hvacUnitId != null) && (hvacUnitId != "")) {
		settings?.hvacUnitId= hvacUnitId
		traceEvent(settings.logFilter,"determine_hvac_unit_id> hvacUnitId from device = ${hvacUnitId}", settings.trace)
	}
	if ((hvac_unit_id != "") && (hvac_unit_id != hvacUnitId) && (hvacUnitId)) {
		sendEvent(name: "hvacUnitId", displayed: (settings.trace?:false),value: hvacUnitId)    
	}
	return hvacUnitId
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
void initialSetup(device_client_id,device_private_key,access_token,refresh_token,token_type,token_authexptime,device_structure_id,device_hvac_unit_id) {
	def varSettings=[:]
	settings?.trace=true
	settings?.logFilter=2
	if (settings.trace) {
		log.debug "initialSetup>begin"
		log.debug "initialSetup> device_tstat_Id = ${device_hvac_unit_id}"
		log.debug "initialSetup> device_client_id = ${device_client_id}"
		log.debug "initialSetup> device_private_key = ${device_private_key}"
		log.debug "initialSetup> token_type = ${token_type}"
		log.debug "initialSetup> token_authexptime = ${token_authexptime}"
		log.debug "initialSetup> device_structure_Id = ${device_structure_id}"
	}	

	settings?.appKey= device_client_id
	settings?.privateKey= device_private_key
	settings?.structureId = device_structure_id
	settings?.hvacUnitId = device_hvac_unit_id
	varSettings=settings	
	data?.auth=[:]
	data?.auth=varSettings
    
	data?.auth?.access_token= access_token
	data?.auth?.refresh_token = refresh_token
	data?.auth?.token_type = token_type
	data?.auth?.authexptime= token_authexptime
    
	sendEvent(name: "hvacUnitId", displayed: (settings.trace?:false), value: device_hvac_unit_id)    
	sendEvent(name: "structureId", displayed: (settings.trace?: false), value: device_structure_id)
	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()
//	refresh_hvacUnit(device_hvac_unit_id)
	runIn(1*60,"refresh")
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
	return (temp - 32).toFloat() / 1.8
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

void produceSummaryReport(pastDaysCount) {
	traceEvent(settings.logFilter,"produceSummaryReport>begin",settings.trace, get_LOG_TRACE())
	def avg_room_temp,avg_room_desired_temp, avg_room_setpoint, min_room_setpoint=200, max_room_setpoint=0
	int on_count=0, off_count=0
	boolean found_values=false
	Date todayDate = new Date()
	Date startOfPeriod = todayDate - pastDaysCount
	long min_room_timestamp,max_room_timestamp

	def rmSetpointData = device.statesSince("rmSetpoint", startOfPeriod, [max:200])
	def rmTemperatureData = device.statesSince("rmCurrentTemperature", startOfPeriod, [max:200])
	def temperatureData = device.statesSince("rmUserDesiredTemperature", startOfPeriod, [max:200])
	def powerData = device.statesSince("switch",startOfPeriod, [max:200])

	if (rmTemperatureData) {
		avg_room_temp= (rmTemperatureData.sum{it.floatValue.toFloat()}/(rmTemperatureData.size())).toFloat().round(1)
		found_values=true        
	}        
	if (temperatureData) {
		avg_room_desired_temp= (temperatureData.sum{it.floatValue.toFloat()}/ (temperatureData.size())).toFloat().round(1)
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
    
	if (powerData) {    
		def on_set= (powerData.find{it.value==1})
		def off_set= (powerData.find{it.value==0})
		if (on_set) on_count=on_set.count()        
		if (off_set) off_count=off_set.count()        
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
    
	String currentMode=device.currentValue("thermostatMode")    
	String roomName =device.currentValue("roomName")
	String summary_report = "${device.displayName}'s is currently in ${currentMode} mode. ${timePeriod}" 
	if (roomName) {
		summary_report= summary_report + ", in the room ${roomName} where the unit is located"
	}
   
	if (avg_room_desired_temp) {
		summary_report= summary_report + ",the room's average desired temperature was ${avg_room_desired_temp} degrees ${unitScale}"
    
	}
    
	if (avg_room_temp) {
		summary_report= summary_report + ",the room's average temperature was ${avg_room_temp} degrees ${unitScale}"
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
		summary_report= summary_report + ",and the room's maximum setpoint was ${max_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}." 
	}
    
	if (on_level_count) {
		summary_report= summary_report + "Finally, the unit was turned on ${on_count} times" 
	}
	if (off_level_count) {
		summary_report= summary_report + "and, the unit was turned off ${off_count} times." 
	}

	sendEvent(name: "summaryReport", value: summary_report, isStateChange: true)
    
	traceEvent(settings.logFilter,"produceSummaryReport>end",settings.trace, get_LOG_TRACE())

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
	def powerTable = []
	def rmTemperatureTable = []
	def temperatureTable = []
	def rmSetpointTable = []
	def heatingSetpointData = device.statesSince("heatingSetpoint", startOfWeek, [max:200])
	def coolingSetpointData = device.statesSince("coolingSetpoint", startOfWeek, [max:200])
	def powerData = device.statesSince("switch", startOfWeek, [max:200])
	def rmSetpointData = device.statesSince("rmSetpoint", startOfWeek, [max:20])
	def rmTemperatureData = device.statesSince("rmCurrentTemperature", startOfWeek, [max:50])
	def temperatureData = device.statesSince("temperature", startOfWeek, [max:20])

//	traceEvent(settings.logFilter,"retrieveDataForGraph>powerData=$powerData",settings.trace)
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
	maxInd=(coolingSetpointData) ? (coolingSetpointData?.size()-1) :-1   
	for (int i=maxInd; (i>=0);i--) {
		if (i !=maxInd) previousValue = coolingSetpointData[i+1]?.floatValue
		// filter some values        
		if ((i==0) || (i==maxInd) || ((coolingSetpointData[i]?.floatValue <= (previousValue - MIN_DEVIATION_TEMP)) || (coolingSetpointData[i]?.floatValue >= (previousValue + MIN_DEVIATION_TEMP)) )) {
 			coolingSetpointTable.add([coolingSetpointData[i].date.getTime(),coolingSetpointData[i].floatValue])
		}            
	} /* end for */            
	powerData.reverse().each() {
		int currentPowerValue=(it.value=='on')?1:0        
		powerTable.add([it.date.getTime(),currentPowerValue])
	}
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
		}            
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
 	if (powerTable == []) {  // if power has not changed for a week
		def currentPower=device.currentValue("switch")
		int currentPowerValue=(currentPower=='on')?1:0        
		if (currentPower) {
			powerTable.add([startOfWeek.getTime(),currentPowerValue])		        
			powerTable.add([todayDate.getTime(),currentPowerValue])		        
		}    
	} else {
		def currentPower=device.currentValue("switch")
		int currentPowerValue=(currentPower=='on')?1:0        
		if (currentPower) {
			powerTable.add([todayDate.getTime(),currentPowerValue])		        
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

	if (temperatureTable == []) { // if temperature has not changed for a week
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
	state?.currentMode=mode     
	state?.heatingSetpointTable = heatingSetpointTable
	state?.coolingSetpointTable = coolingSetpointTable
	state?.powerTable = powerTable
	state?.rmTemperatureTable = rmTemperatureTable
	state?.temperatureTable = temperatureTable
	state?.rmSetpointTable = rmSetpointTable
//	log.debug "retrieveDataForGraph>state.currentMode= ${state?.currentMode}"
//	log.debug "retrieveDataForGraph>temperatureTable (size=${temperatureTable.size()}) =${temperatureTable}"  
//	log.debug "retrieveDataForGraph>rmSetpointTable (size=${rmSetpointTable.size()}) =${rmSetpointTable}"  
//	log.debug "retrieveDataForGraph>rmTemperatureTable (size=${state?.rmTemperatureTable.size()}) =${state?.rmTemperatureTable}"  
//	log.debug "retrieveDataForGraph>heatingSetpointTable (size=${state?.heatingSetpointTable.size()}) =${state?.heatingSetpointTable}"
//	log.debug "retrieveDataForGraph>coolingSetpointTable (size=${state?.coolingSetpointTable.size()}) =${state?.coolingSetpointTable}"
//	log.debug "retrieveDataForGraph>powerTable (size=${state?.powerTable.size()}) =${state?.powerTable}"
	traceEvent(settings.logFilter,"retrieveDataForGraph>temperatureTable (size=${temperatureTable.size()}) =${temperatureTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>rmSetpointTable (size=${rmSetpointTable.size()}) =${rmSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>rmTemperatureTable (size=${state?.rmTemperatureTable.size()}) =${state?.rmTemperatureTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>state.currentMode= ${state?.currentMode}",settings.trace)    
	traceEvent(settings.logFilter,"retrieveDataForGraph>heatingSetpointTable (size=${state?.heatingSetpointTable.size()}) =${state?.heatingSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>coolingSetpointTable (size=${state?.coolingSetpointTable.size()}) =${state?.coolingSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>powerTable (size=${state?.powerTable.size()}) =${state?.powerTable}",settings.trace,get_LOG_TRACE())  
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
	if ((state?.powerTable) && (state?.powerTable?.size() > 0)) {
		startTime = Math.min(startTime, state.powerTable.min{it[0]}[0].toLong())
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
			dataTable = state?.rmSetpointTable
			break
		case 4:
			dataTable = state?.temperatureTable
			break
		case 5:
			dataTable = state?.powerTable
			break
	}
	dataTable.each() {
		dataString += "[new Date(${it[0]}),"
		if (seriesIndex==1) {
			dataString += "${it[1]},null,null,null],"
		}
		if (seriesIndex==2) {
			dataString += "${it[1]},null,null,null],"
		}
		if (seriesIndex==3) {
			dataString += "null,${it[1]},null,null],"
		}
		if (seriesIndex==4) {
			dataString += "null,null,${it[1]},null],"
		}
		if (seriesIndex==5) {
			dataString += "null,null,null,${it[1]}],"
		}
        
	}
	        
	if (dataString == "") {
		def todayDate = new Date()
		if (seriesIndex==1) {
			dataString = "[new Date(todayDate.getTime()),0,null,null,null],"
		}
		if (seriesIndex==2) {
			dataString = "[new Date(todayDate.getTime()),0,null,null,null],"
		}
		if (seriesIndex==3) {
			dataString = "[new Date(todayDate.getTime()),null,0,null,null],"
		}
		if (seriesIndex==4) {
			dataString = "[new Date(todayDate.getTime()),null,null,0,null,],"
		}
		if (seriesIndex==5) {
			dataString = "[new Date(todayDate.getTime()),null,null,null,0],"
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
		dataRows = "${getDataString(1)}" + "${getDataString(3)}" +  "${getDataString(4)}" + "${getDataString(5)}"
	} else {
		mode='cool'    
		colorMode='#269bd2'  
		dataRows = "${getDataString(2)}" + "${getDataString(3)}" + "${getDataString(4)}" + "${getDataString(5)}"
	}    
//	traceEvent(settings.logFilter,"getGraphHTML>mode= ${state?.currentMode}, dataRows=${dataRows}",settings.trace)    
	Date maxDateTime= new Date()
	Date minDateTime= new Date(getStartTime())
	def minDateStr= "new Date(" +  minDateTime.getTime() + ")"
	def maxDateStr= "new Date(" +  maxDateTime.getTime() + ")"

	Date yesterday=maxDateTime -1
	def yesterdayStr= "new Date(" +  yesterday.getTime() + ")"
   
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
						data.addColumn('number', 'RmSetPnt');
						data.addColumn('number', 'Ambient');
						data.addColumn('number', 'Power');
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
								1: {targetAxisIndex: 0, color: '#1e9cbb'},
								2: {targetAxisIndex: 0, color: '#f1d801'},
								3: {targetAxisIndex: 1, color: '#44b621',lineWidth: 1}
							},
							vAxes: {
								0: {
									title: 'Temperature',
									format: 'decimal',
									textStyle: {color: '${colorMode}'},
									titleTextStyle: {color: '${colorMode}'}
								},
								1: {
									title: '(1:On/0:Off)',
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
	  		<h3 style="font-size: 20px; font-weight: bold; text-align: center; background: #ffffff; color: #44b621;">TempVsPower</h3>
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