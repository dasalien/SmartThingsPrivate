/***
 *  My Puck Device
 *  Copyright 2017-2020 Yves Racine
 *  LinkedIn profile: www.linkedin.com/in/yracine
 *  Version 2.1
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
	input("puckId", "text", title: "Serial #", description:
		"The id of your puck\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("appKey", "text", title: "App Key", description:
		"The application key given by Flair\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("privateKey", "text", title: "Private Key", description:
		"The private key given by Flair\n(not needed when using MyFlairServiceMgr, leave it blank)")
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces)\n or leave it empty (no tracing)")
	input("logFilter", "number",title: "(1=ERROR only,2=<1+WARNING>,3=<2+INFO>,4=<3+DEBUG>,5=<4+TRACE>)",  range: "1..5",
 		description: "optional" )  
	input("tempOffset", "decimal", title: "Temperature Offset", description:
		"Optional: offset for the puck's temp attribute")
	input("humOffset", "number", title: "Humidity Offset", description:
		"Optional: offset for the puck's humidity attribute")
        
	}
metadata {
	// Automatically generated. Make future change here.
	definition(name: "Puck Device V2", author: "Yves Racine", mnmn: "SmartThingsCommunity", vid: "e006e17d-e16e-39ac-9d9d-2eb32e985989", ocfDeviceType:"oic.d.thermostat", namespace: "acrosscable12814") {
    //b8533f08-c56d-3609-8979-6c5a8db2b0c2 //fabricacross64399 
		capability "Relative Humidity Measurement"
		capability "Temperature Measurement"
//		capability "thermostatSetpoint"
		capability "Polling"
		capability "Refresh"
		capability "Sensor"
		capability "Battery"	// to be added later with proper Flair metric
		capability "Actuator"
		capability "Health Check"
		capability "acrosscable12814.thermostatSetpoint"
		capability "acrosscable12814.homeAwayMode"
		capability "acrosscable12814.flairAwayMode"
		capability "acrosscable12814.flairSetpointMode"
		capability "acrosscable12814.flairDropRate"
		capability "acrosscable12814.flairPressure"
		capability "acrosscable12814.flairRoom"
		capability "acrosscable12814.flairZone"
		capability "acrosscable12814.flairRssi"
		capability "acrosscable12814.flairStatus"

		attribute "structureId", "string"
		attribute "puckId", "string"
		attribute "puckName", "string"
		attribute "puckNumber", "string"
		attribute "reportingInterval", "number"
		attribute "temperatureDisplay", "number"
		attribute "puckTemperature", "number"
		attribute "puckTemperatureDisplay", "number"
		attribute "pressure", "number"
		attribute "orientation", "string"
		attribute "inactive", "string"      
		attribute "humidityOffset", "number"        
		attribute "tempOffset", "number"        
		attribute "puckText","string"
		attribute "puckimage","string"
		attribute "irSetup","string"
		attribute "irSetupEnabled","string"
		attribute "irDispatch","string"
		attribute "irDownload","string"
		attribute "firmwareW","string"
		attribute "firmwareB","string" 
		attribute "firmwareS","string" 
		attribute "light", "string"        
		attribute "isGateway","string"
		attribute "puckList","string"    
		attribute "puckColor","string"    
		attribute "puckScale","string"    
		attribute "desiredTemperature","number"
		attribute "dieTemperature","number"
		attribute "desiredTemperatureDisplay","string"
		attribute "bluetoothTxPowerMw","string"
		attribute "beaconIntervalMs","number"
		attribute "dropRate", "number"
		attribute "systemVoltage", "number" 
		attribute "subGhzRadioTxPowerMw","number"
		attribute "messageVersion", "string"
		attribute "rotaryEncodedClicks", "number"
		attribute "buttonPushes", "number"        
		attribute "read","string"
		attribute "dispTtlMs", "string"
		attribute "setBy","string"
		attribute "createdAt","string"
		attribute "updatedAt","string"
		attribute "changeSet","string"
		attribute "puckData","string"       
		attribute "puckStatesData","string"       
		attribute "puckReadingsData","string"       
		attribute "beaconSightingsData","string"
        
 		attribute "stHomeAwayMode","string"
		attribute "stAwayMode","string"                
		attribute "stSPointMode","string"                
		attribute "stLocation","string"                
		attribute "stLocationType","string"                
		attribute "stHome","string"                
		attribute "stName","string"                
		attribute "stCity","string"                
		attribute "stState","string"                
		attribute "stZipCode","string"                
		attribute "stReportingGateway","string"                
		attribute "stActive","string"                
		attribute "stLongitude","string"                
		attribute "stLatitude","string"                
		attribute "stSetupComplete", "string" 
		attribute "stMode", "string" 

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
		attribute "ZoneData","string"

		attribute "verboseTrace", "string"
		attribute "summaryReport","string"

		command "levelUp"
		command "levelDown"
		command "getPuckInfo"
		command "getPuckReadings"
		command "getPuckStates"
//		command "getBeaconSightings"   // Not supported by Flair APIs
		command "getPressure"
		command "getBattery"
		command "getTemperature"
		command "getHumidity"
        
		command "setPuck"			
//		command "setPuckState"	// No longer supported by Flair APIs
//		command "setPuckIsAccessPoint"  // Not supported by Flair APIs
		command "setDesiredTemp"
		command "setPuckScale"
		command "setPuckText"
		command "setPuckImage"
		command "setPuckInactive"			
		command "setPuckHumidityOffset"
		command "setPuckTempOffset"

		command "getStructure"        
		command "setStructure"        
		command "setStructureHomeAwayMode"
		command "setStructureSetpointMode"
		command "setStructureAwayMode"

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
        
		command "homeAwayAutoHome"
		command "homeAwayThirdPartyHome"
		command "awaySmartAway"
		command "awayDeferToRooms"
		command "awayOffOnly"
		command "setpointEvennessActive"
		command "setpointEvenness"
		command "setpointDeferToRooms"
		command "autoMode"
		command "manualMode"
		command "produceSummaryReport"
//		command "setThermostatSetpoint", ["number"]
		        
	}        
	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
    
		multiAttributeTile(name:"puckMulti", type:"generic", width:6, height:4,canChangeIcon: true,backgroundColor:"#44b621") {
			tileAttribute("device.temperatureDisplay", key: "PRIMARY_CONTROL") {
				attributeState("default", label:'${currentValue}', unit:"dF", backgroundColor: "#44b621") 
			}
			tileAttribute("device.thermostatSetpoint", key: "VALUE_CONTROL") {
				attributeState("default", action: "setDesiredTemp")
				attributeState("VALUE_UP", action: "levelUp")
				attributeState("VALUE_DOWN", action: "levelDown")
			}
			tileAttribute("device.humidity", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'Humidity ${currentValue}%', unit:"%")
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
		valueTile("desiredTempDisplay", "device.desiredTemperatureDisplay", width: 2, height: 2) {
			state("temperatureDisplay", label:'Desired ${currentValue}', unit:"dF",
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
		standardTile("levelDown", "device.desiredTemperature", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"levelDown", icon: "${getCustomImagePath()}coolDown.png", backgroundColor: "#ffffff"

		}
		standardTile("levelUp", "device.desiredTemperature", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "default", label:'', action:"levelUp", icon: "${getCustomImagePath()}heatUp.png", backgroundColor: "#ffffff"
		}
        
		valueTile("name", "device.puckName", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("puckDisplayNumber", "device.puckNumber", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "default", label: 'Num ${currentValue}', 
			backgroundColor: "#ffffff"
		}
		valueTile("roomName", "device.roomName", inactiveLabel: false, width: 4, height: 1,decoration: "flat") {
			state "default", label: 'Room ${currentValue}', backgroundColor: "#ffffff"
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/room.png"
		}
		valueTile("zoneName", "device.zoneName", inactiveLabel: false, width: 4, height: 2) {
			state "default", label: 'Zone(s) ${currentValue}', backgroundColor: "#ffffff" 
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/zoning.jpg"
		}
		valueTile("pressure", "device.pressure", width: 4, height: 1,  decoration: "flat") {
			state "pressure", label: 'Pressure ${currentValue}Pa', backgroundColor: "#ffffff", unit: "Pa"
//			icon: "http://raw.githubusercontent.com/yracine/device-type.myecobee/master/icons/pressure.png"
		}
		valueTile("battery", "device.battery", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "battery", label: 'Battery ${currentValue}%', backgroundColor: "#ffffff"
		}
		valueTile("tempOffset", "device.tempOffset", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Temp Offset ${currentValue}', backgroundColor: "#ffffff"
		}
		valueTile("humOffset", "device.humidityOffset", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Hum Offset ${currentValue}%', backgroundColor: "#ffffff"
		}
		valueTile("droprate", "device.dropRate", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Drop Rate ${currentValue}', backgroundColor: "#ffffff"
		}
		standardTile("homeAwayMode", "device.stHomeAwayMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "Autohome", label: 'Autohome', action: "homeAwayThirdPartyHome",
				icon: "st.Home.home4", backgroundColor: "#ffffff", nextState:"ThirdParty"
			state "ThirdParty", label: '3rdParty', action: "homeAwayAutoHome",
				icon: "st.Home.home4",backgroundColor: "#ffffff", nextState:"Autohome"
		}
		standardTile("setpointMode", "device.stSPointMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "EvennessActive", label: 'Evenness Active', action: "setpointEvenness",
				icon: "st.samsung.da.RAC_4line_02_ic_heat", backgroundColor: "#ffffff", nextState:"Evenness"
			state "Evenness", label: 'Evenness', action: "setpointDeferToRooms",
				icon: "st.samsung.da.RAC_4line_02_ic_heat",backgroundColor: "#ffffff", nextState:"DeferRooms"
			state "DeferRooms", label: 'DeferRooms', action: "setpointEvennessActive",
				icon: "st.samsung.da.RAC_4line_02_ic_heat",backgroundColor: "#ffffff", nextState:"EvennessActive"
		}
		valueTile("inactive", "device.inactive", inactiveLabel: false, width: 2,
			height: 2) {
			state "default", label: 'Inactive ${currentValue}', 
			backgroundColor: "#ffffff"
		}
 		standardTile("awayMode", "device.stAwayMode", inactiveLabel: false, width: 2,
			height: 2, decoration: "flat") {
			state "SmartAway", label: 'SmartAway', action: "awayDeferToRooms",
				icon: "st.presence.car.car", backgroundColor: "#ffffff",nextState:"Rooms"
			state "Rooms", label: 'DeferRooms', action: "awayOffOnly", 
				icon: "st.presence.car.car",backgroundColor: "#ffffff",nextState:"Off"
			state "Off", label: 'AwayOff', action: "awaySmartAway", 
				icon: "st.presence.car.car",backgroundColor: "#ffffff",nextState:"SmartAway"
		}
		valueTile("irSetup", "device.irSetupEnabled", inactiveLabel: false, height:2, width:2, decoration: "flat") {
			state "default", label:'IRSetup ${currentValue}', backgroundColor: "#ffffff"
		}
		valueTile("gateway", "device.isGateway", inactiveLabel: false, height:2, width:2, decoration: "flat") {
			state "default", label:'Gateway ${currentValue}', backgroundColor: "#ffffff"
		}
//		standardTile("present", "device.presence", inactiveLabel: false, height:2, width:2, canChangeIcon: false) {
//			state "not present", label:'${name}', backgroundColor: "#ffffff", icon:"st.presence.tile.presence-default" 
//			state "present", label:'${name}', backgroundColor: "#ffffff", icon:"st.presence.tile.presence-default" 
//		}
		valueTile("updatedAt", "device.updatedAt", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
			state "default", label: 'Updated ${currentValue}', backgroundColor: "#ffffff"
		}
		standardTile("refresh", "device.temperature", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {
			state "default", label: 'Refresh',action: "refresh", icon:"st.secondary.refresh", 			
			backgroundColor: "#ffffff"
		}
		htmlTile(name:"graphHTML", action: "getGraphHTML", width: 6, height: 8,  whitelist: ["www.gstatic.com"])
       
		main("puckMulti")
		details(["puckMulti",
			"name",	
			"puckDisplayNumber",	
			"gateway",
			"inactive",
			"battery",
			"updatedAt",            
			"desiredTempDisplay","levelUp","levelDown",
			"homeAwayMode",            
			"awayMode",            
			"setpointMode",
			"irSetup",            
			"droprate",
			"refresh",
			"tempOffset",            
			"pressure",            
			"roomName",
			"humOffset",            
			"zoneName",            
			"graphHTML"             
		])

	}
    
}

def setHomeAwayMode(mode) {
	setStructureHomeAwayMode(mode)
}

def autoHome() {
	setStructureHomeAwayMode("autoHome")
}

def thirdParty() {
	setStructureHomeAwayMode("thirdParty")
}

def setSetpointMode(mode) {
	setStructureSetpointMode(mode)
}
def evennessActive() {
	setStructureSetpointMode("evennessActive")
}

def evenness() {
	setStructureSetpointMode("evenness")
}

def deferRooms() {
	setStructureSetpointMode("deferRooms")
}

def setAwayMode(mode) {
    setStructureAwayMode(mode)
}

def smartAway() {
    setStructureAwayMode("smartAway")
}
def rooms() {
    setStructureAwayMode("rooms")
}

def off() {
    setStructureAwayMode("off")
}

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

void installed() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	state?.scale=getTemperatureScale() 
	if (settings.trace) { 
		traceEvent(settings.logFilter, "installed>$device.displayName updated with settings: ${settings.inspect()}", settings.trace, get_LOG_TRACE())	
	}
}

/*	@ping is used by Device-Watch in attempt to reach the device
*/
def ping() {
	poll()
}

def updated() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])	
	state?.scale=getTemperatureScale() 
	setPuckScale(state?.scale) // set the scale for the Puck
    
    
	if (tempOffset!=null) {
		setPuckTempOffset(tempOffset)
	} else {
		setPuckTempOffset(0)
	}    
	if (humOffset!=null) {
		setPuckHumidityOffset(humOffset)
	} else {
		setPuckHumidityOffset(0)
	}    
	retrieveDataForGraph()    
	data?.pucks=[]
	data?.puckReadings=[]
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

void levelUp() {
	def scale = state?.scale
	def MIN_VALUE_IN_CELSIUS=15
	def MIN_VALUE_IN_F=50
	def nextLevel=null    
	try {    
		nextLevel = device.currentValue("desiredTemperature")
	} catch (any) {        
		traceEvent(settings.logFilter, "levelUp>no desired temperature set for this device", settings.trace, get_LOG_WARN())
	}      
    
	if (scale == 'C') {
		nextLevel=(!nextLevel)? MIN_VALUE_IN_CELSIUS:nextLevel.toDouble()        	
		nextLevel = (nextLevel + 0.5).round(1)        
		if (nextLevel > 30) {
			nextLevel = 30.0
		}
		setDesiredTemp(nextLevel)
	} else {
		nextLevel=(!nextLevel)? MIN_VALUE_IN_F:nextLevel.toInteger()        	
		nextLevel = (nextLevel + 1)
		if (nextLevel > 99) {
			nextLevel = 99
		}
		setDesiredTemp(nextLevel)
	}

}

void levelDown() {
	def scale = state?.scale
	def MIN_VALUE_IN_CELSIUS=15
	def MIN_VALUE_IN_F=50
	def nextLevel=null    
	try {    
		nextLevel = device.currentValue("desiredTemperature")
	} catch (any) {        
		traceEvent(settings.logFilter, "levelDown>no desired temperature set for this device", settings.trace, get_LOG_WARN())
	}      

	if (scale == 'C') {
		nextLevel=(!nextLevel)? MIN_VALUE_IN_CELSIUS:nextLevel.toDouble()        	
		nextLevel = (nextLevel - 0.5).round(1)        
		if (nextLevel < 10) {
			nextLevel = 10.0
		}
		setDesiredTemp(nextLevel)
	} else {
		nextLevel=(!nextLevel)? MIN_VALUE_IN_F:nextLevel.toInteger()        	
		nextLevel = (nextLevel - 1)
		if (nextLevel < 50) {
			nextLevel = 50
		}
		setDesiredTemp(nextLevel)
	}
}

// handle commands


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


void homeAwayAutoHome() {
	setStructureHomeAwayMode('Flair Autohome Autoaway')
}

void homeAwayThirdPartyHome() {
	setStructureHomeAwayMode('Third Party Home Away')
}

void awaySmartAway() {
	setStructureAwayMode('Smart Away')
}

void awayDeferToRooms() {
	setStructureAwayMode('Defer To Rooms')
}

void awayOffOnly() {
	setStructureAwayMode('Off Only')
}

void setpointDeferToRooms() {
	setStructureSetpointMode('Defer To Rooms And Users')
}

void setpointEvenness() {
	setStructureSetpointMode('Home Evenness Flair SetPoint')
}

void setpointEvennessActive() {
	setStructureSetpointMode('Home Evenness For Active Rooms Flair Setpoint')
}

void autoMode() {
	setStructureMode('auto')

}

void manualMode() {
	setStructureMode('manual')

}

//	@id				Id of the structure, by default the current one
//	@forceUpdate	forceUpdate of the local cache, by default false
//	@postData		flag used to post the corresponding room data as json, by default false

def getStructure(id,forceUpdate=false,postData=false) {
	if (!id) {
		id= device.currentValue("structureId")    
		if (!id) {
			traceEvent(settings.logFilter,"getStructure>id is null,exiting", settings.trace)
			return null        
		}        
	}    
	def results = data?.structures.find{it.id==id}
	if ((!results) || (forceUpdate)) { // get the object from parent

		parent.getObject("structures", id)    	
		parent.updateStructures(this)  
		results = data?.structures.find{it.id==id}
	}
	if ((results) && (postData)) {

		def structureDataJson = new groovy.json.JsonBuilder(results)
		/*	
		traceEvent(settings.logFilter,"getStructure>structureDataJson=${structureDataJson}", settings.trace)
		*/
		def structureEvents = [
				structureData: "${structureDataJson.toString()}"
			]
		/*    
		traceEvent(settings.logFilter,"getStructure>structureEvents to be sent= ${structureEvents}", settings.trace)
		*/
		generateEvent(structureEvents)
    
	}    
	traceEvent(settings.logFilter,"getStructure>results =${results} for id=$id", settings.trace)
	return results    
}

//	@id				Id of the room, by default the current one
//	@forceUpdate	forceUpdate of the local cache, by default false
//	@postData		flag used to post the corresponding room data as json, by default false

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

//	@id				Id of the zone [required]
//	@forceUpdate	forceUpdate of the local cache, by default false
//	@postData		flag used to post the corresponding zone data as json, by default false
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

private def getHvacUnits(id) {
	def results = data?.hvacUnits.find{it.id==id}
	return results    
}

// puckId is single puckId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private def refresh_puck(puckId, asyncValues=null) {	
	def structure,room
	String zonesList    
	def todayDay = new Date().format("dd",location.timeZone)
    
	puckId=determine_puck_id(puckId)
	def structureId=determine_structure_id()    

	if (!data?.pucks) {
		data?.pucks=[]
	}        

	if (!asyncValues) {
		traceEvent(settings.logFilter, "refresh_puck>manual refresh", settings.trace, get_LOG_INFO())
		getPuckInfo(puckId)
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter,"poll>$exceptionCheck for puckId =${puckId}", settings.trace,get_LOG_ERROR() )
			return    
		}    
		getPuckReadings(puckId, 'current-reading')
		
	} else {
		traceEvent(settings.logFilter, "refresh_puck>poll refresh with values=$asyncValues", settings.trace, get_LOG_INFO())
		if (asyncValues.data instanceof Collection) {
 			data?.pucks=asyncValues.data
		} else {
 			data?.pucks[0]=asyncValues.data            
		}            
     
	}   
	// split the events into 2 for better performances.

	def dataEvents = [
 		puckName:data?.pucks[0]?.attributes?.name,
 		puckNumber: data?.pucks[0]?.attributes?."display-number",
 		puckColor: data?.pucks[0]?.attributes?."puck-display-color",
		puckTemperature: data?.pucks[0]?.attributes?."current-temperature-c",
		puckTemperatureDisplay:data?.pucks[0]?.attributes?."current-temperature-c",
		humidity: data?.pucks[0]?.attributes?."current-humidity",
		humidityOffset: (data?.pucks[0]?.attributes?."humidity-offset" ?:0),
		tempOffset: data?.pucks[0]?.attributes?."temperature-offset-override-c",
		orientation: data?.pucks[0]?.attributes?.orientation,
		inactive: data?.pucks[0]?.attributes?.inactive,
		dropRate: (data?.pucks[0]?.attributes?."drop-rate")? data?.pucks[0]?.attributes?."drop-rate"?.toFloat().round(3):'0',
		createdAt:formatDateInLocalTime(data?.pucks[0]?.attributes?."created-at"),
		updatedAt:formatDateInLocalTime(data?.pucks[0]?.attributes?."updated-at"),
		reportingInterval: data?.pucks[0]?.attributes?."reporting-interval-ds"
	]
	traceEvent(settings.logFilter,"refresh_puck>dataEvents= ${dataEvents}", settings.trace)
    
	generateEvent(dataEvents)

//	getPuckStates(puckId, 'current-state')  // do not get the state anymore
	//getPuckReadings(puckId, 'current-reading')
	def roomId    
	try {    
		roomId=data.pucks[0]?.relationships?.room?.data?.id    
	} catch (any) {        
		traceEvent(settings.logFilter, "refresh_puck>roomId not found- puck $puckId not associated to a room", settings.trace, get_LOG_WARN())
	}      
	roomId= (roomId)?: device.currentValue("roomId") // get the previous (saved) roomId value if any 
    
	if (roomId) {    
		if ((!asyncValues)  || ((!state?.today) || (state?.today != todayDay))) { // every day refresh the room and zone info
			structure=getStructure(structureId,true)    
			room=getRoom(roomId,true)    
			parent.getObject("rooms",roomId, "zones")
			parent.updateZones(this)    
			state?.today=todayDay        
		} else {   
			room=getRoom(roomId)
		}            
	}    
	structure=getStructure(structureId)    
	zonesList = getZonesList()		            
	traceEvent(settings.logFilter,"refresh_puck>puck[0]= ${data?.pucks[0]}",settings.trace)    
//	traceEvent(settings.logFilter,"refresh_puck>puckStates[0]= ${data?.puckStates[0]}",settings.trace)    
	traceEvent(settings.logFilter,"refresh_puck>structures[0]= ${data?.structures[0]}",settings.trace)    
    
	dataEvents = [
		light: data?.puckReadings[0]?.attributes?.light,
		bluetoothTxPowerMw:data?.pucks[0]?.attributes?."bluetooth-tx-power-mw",
		beaconIntervalMs:data?.pucks[0]?.attributes?."beacon-interval-ms",
		subGhzRadioTxPowerMw:data?.pucks[0]?.attributes?."sub-ghz-radio-tx-power-mw",
		pressure: data?.puckReadings[0]?.attributes?."room-pressure",
		rssi:data?.puckReadings[0]?.attributes?.rssi,
		battery: getBatteryUsage(),		        
		systemVoltage:data?.puckReadings[0]?.attributes?."system-voltage",
		messageVersion: data?.puckReadings[0]?.attributes?."message-version",
		rotaryEncodedClicks:data?.puckReadings[0]?.attributes?."rotary-encoded-clicks",
		buttonPushes:data?.puckReadings[0]?.attributes?."button-pushes",        
		isGateway:data?.puckReadings[0]?.attributes?."is-gateway",				                    
//		irSetup: data?.pucks[0]?.attributes?."ir-setup-enabled",				                    
		irSetupEnabled: data?.pucks[0]?.attributes?."ir-setup-enabled",				                    
		irDispatch: data?.pucks[0]?.attributes?."ir-dispatch",        
		irDownload: data?.pucks[0]?.attributes?."ir-download",                 
		firmwareW: data?.puckReadings[0]?.attributes?."firmware-version-w",                   
		firmwareB: data?.puckReadings[0]?.attributes?."firmware-version-b",                    
		firmwareS: data?.puckReadings[0]?.attributes?."firmware-version-s",                    
		desiredTemperature: data?.puckReadings[0]?.attributes?."desired-temperature-c",
		desiredTemperatureDisplay: data?.puckReadings[0]?.attributes?."desired-temperature-c",
		thermostatSetpoint:data?.puckReadings[0]?.attributes?."desired-temperature-c",
		temperature: data?.puckReadings[0]?.attributes?."room-temperature-c",
		temperatureDisplay: data?.puckReadings[0]?.attributes?."room-temperature-c",
//		temperature: data?.puckReadings[0]?.attributes?."room-temperature-c",
//		temperatureDisplay: data?.puckReadings[0]?.attributes?."room-temperature-c",
		dieTemperature: data?.puckReadings[0]?.attributes?."die-temperature",
		puckText: data?.pucks[0]?.attributes?."display-text",
		puckImage: data?.pucks[0]?.attributes?."display-image",
//		read:data?.puckStates[0]?.attributes?.read,
//		dispTtlMs:data?.puckStates[0]?.attributes?."display-ttl-ms",
//		setBy:data?.puckStates[0]?.attributes?."set-by",
//		changeSet:data?.puckStates[0]?.attributes?."changeset",
		stHomeAwayMode:get_st_corresponding_value(structure?.attributes?."home-away-mode"),        
		stAwayMode:get_st_corresponding_value(structure?.attributes?."structure-away-mode"),
		stSPointMode:get_st_corresponding_value(structure?.attributes?."set-point-mode"),
		stMode:structure?.attributes?."mode",
		stLocation:structure?.attributes?."location",
		stLocationType:structure?.attributes?."location-type",
		stHome:structure?.attributes?."home",
		stName:structure?.attributes?."name",
		stLongitude:structure?.attributes?."longitude",
		stLatitude:structure?.attributes?."latitude",
		stCity:structure?.attributes?."city",
		stState:structure?.attributes?."state",
		stZipCode:structure?.attributes?."zip-code",
		stActive:structure?.attributes?."is-active",
		stSetupComplete:structure?.attributes?."setup-complete",
		stReportingGateway:structure?.attributes?."reporting-gateway",
 		puckScale: structure?.attributes?."temperature-scale",
		roomId:roomId,           
		roomName:room?.attributes?.name,           
		rmSetpoint:room?.attributes?."set-point-c",        
		rmCurrentTemperatureDisplay:room?.attributes?."current-temperature-c",        
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
	if (dataEvents.tempOffset) {
		settings.tempOffset= dataEvents.tempOffset   
	}    
	if (dataEvents.humidityOffset) {
		settings.humOffset= dataEvents.humidityOffset   
	}    
	traceEvent(settings.logFilter,"refresh_puck>dataEvents= ${dataEvents}", settings.trace)
    
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_puck>done for puckId =${puckId}", settings.trace)  
}



def getPressure() {
	traceEvent(settings.logFilter, "getPressure()", settings.trace, get_LOG_TRACE())
	return device.currentValue("pressure")

}

def getTemperature() {
	traceEvent(settings.logFilter, "getTemperature()", settings.trace, get_LOG_TRACE())

	return device.currentValue("temperature")
}

def getBattery() {
	traceEvent(settings.logFilter, "getBattery()", settings.trace, get_LOG_TRACE())

	return device.currentValue("battery")
}

def getHumidity() {
	traceEvent(settings.logFilter, "getHumidity()", settings.trace, get_LOG_TRACE())

	return device.currentValue("humidity")
}

private def getBatteryUsage() {
	def TOTAL_NOMINAL_VOLTAGE=3 
	Double results=0.0

	if (!data?.puckReadings[0]) {
		return
	}


	def systemVoltage=data?.puckReadings[0]?.attributes?."system-voltage"
	if (systemVoltage) {
		results= (systemVoltage/TOTAL_NOMINAL_VOLTAGE)*100
		results=(results >100)?100:results 
	}
	return results as Double
}

//	@refresh() has a different polling interval as it is called by the UI (contrary to poll).
void refresh() {
	def puckId= determine_puck_id("") 	    
	def poll_interval=0.5   // set a 30 sec. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"refresh>puckId = ${puckId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
 			"not refreshing data...", settings.trace)
		return
	}
	state.lastPollTimestamp = now()
	refresh_puck(puckId)
  
}
void poll() {
	String URI_ROOT = "${get_URI_ROOT()}"
    
	def puckId= determine_puck_id("") 	    

	def poll_interval=1   // set a minimum of 1 min. poll interval to avoid unecessary load on Flair servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"poll>puckId = ${puckId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp})," +
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
    
	traceEvent(settings.logFilter,"poll>about to call pollAsyncResponse for puckId = ${puckId}...", settings.trace)

	def params = [
		uri: "${URI_ROOT}/api/pucks/${puckId}",
		headers: [
			'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//			'Content-Type': "text/json",
			'charset': "UTF-8",
			'Accept': "${get_APPLICATION_VERSION()}"

		]
	]
	asynchttp_v1.get('pollAsyncResponsePuckInfo', params)
	params = [
				uri: "${URI_ROOT}/api/pucks/${puckId}/sensor-readings?page[size]=1",
				headers: [
					'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//					'Content-Type': "text/json",
					'charset': "UTF-8",
					'Accept': "${get_APPLICATION_VERSION()}"
				]
		]
	asynchttp_v1.get('pollAsyncResponsePuckReadings', params)

/*  Don't get the puck state anymore
	params = [
				uri: "${URI_ROOT}/api/pucks/${puckId}/current-state?page[size]=1",
				headers: [
					'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//					'Content-Type': "text/json",
					'charset': "UTF-8",
					'Accept': "${get_APPLICATION_VERSION()}"
				]
		]
	asynchttp_v1.get('pollAsyncResponsePuckCurrentState', params)
*/
    
	traceEvent(settings.logFilter,"poll>done for puckId = ${puckId}", settings.trace)
}

def pollAsyncResponsePuckInfo(response, data) {	
	def TOKEN_EXPIRED=401
	String URI_ROOT = "${get_URI_ROOT()}"
 
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponsePuckInfo>Access token has expired, trying to refresh tokens now...", settings.trace,get_LOG_WARN())
			refresh_tokens()
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponsePuckInfo>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponsePuckInfo>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}

	} else {
		def responseValues = null    
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponsPuckInfo>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
			def id =  responseValues.data?.id
			traceEvent(settings.logFilter,"pollAsyncResponsePuckInfo>responseValues=$responseValues", settings.trace, get_LOG_TRACE())
			if (settings.trace) {                
				def name = responseValues.data?.attributes?.name
				def currentTemperature =  responseValues.data?.attributes?."current-temperature-c"
				def currentHumidity =  responseValues.data?.attributes?."current-humidity"						                    
				def beaconSightings =  responseValues.data?.relationships?."beacon-sightings"
				def room =  responseValues.data?.relationships?.room?.data                         
				traceEvent(settings.logFilter, "pollAsyncResponsePuckInfo>puckId=${id}, name= $name," +
					"beacon-sightings=${beaconSightings}, room=${room}," +
					"currentTemperature = $currentTemperature, currentHumidity=$currentHumidity", settings.trace)
			}   
			refresh_puck(id,responseValues)                
			state?.exceptionCount=0                
		}                
                
	}        
}


def pollAsyncResponsePuckCurrentState(response, data) {	
	def TOKEN_EXPIRED=401
  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponsePuckStates>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()      
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponsePuckStates>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponsePuckStates>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues = null
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json    
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponsePuckStates>Flair - error parsing json from response: $e")
		}
            
		if (responseValues) {
			def puckId =  responseValues.data?.relationships?.puck?.data?.id       
			traceEvent(settings.logFilter,"pollAsyncResponsePuckStates>responseValues=$responseValues", settings.trace, get_LOG_TRACE())
			if (settings.trace) {
				def desiredTemperature =  responseValues.data?.attributes?."desired-temperature-c"
				def temperatureScale = responseValues.data?.attributes?."temperature-scale"
				def irSetup =  responseValues.data?.attributes?."ir-setup"				                    
				def irDownload =  responseValues.data?.attributes?."ir-download"                 

				traceEvent(settings.logFilter,"pollAsyncResponsePuckStates>puckId=${puckId}, desiredTemperature= $desiredTemperature," +
					"temperatureScale=$temperatureScale, irSetup=$irSetup, irDownload=$irDownload", settings.trace)

			}
			refresh_puckStates(puckId,responseValues)            
			state?.exceptionCount=0                
		}                
                
	}        
}

// puckId is single puckId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private def refresh_puckStates(puckId, asyncValues=null) {	
    
	puckId=determine_puck_id(puckId)

	if (!data?.puckStates) {    
		data?.puckStates=[]
	}    

	if (!asyncValues) {
		getPuckStates(puckId, 'current-state')
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter,"poll>$exceptionCheck for puckId =${puckId}", settings.trace,get_LOG_ERROR() )
			return    
		}    
	} else {
		if (asyncValues.data instanceof Collection) {
 			data?.puckStates=asyncValues.data
		} else {
 			data?.puckStates[0]=asyncValues.data            
		}            
     
	}    
	traceEvent(settings.logFilter,"refresh_puckStates>puckStates[0]= ${data?.puckStates[0]}",settings.trace)    
    
	def dataEvents = [
		irSetup: data?.puckStates[0]?.attributes?."ir-setup",				                    
		irDownload: data?.puckStates[0]?.attributes?."ir-download",                 
		puckText: data?.puckStates[0]?.attributes?."display-text",
		irDispatch: data?.puckStates[0]?.attributes?."ir-dispatch",        
		puckImage: data?.puckStates[0]?.attributes?."display-image",
		read:data?.puckStates[0]?.attributes?.read,
		dispTtlMs:data?.puckStates[0]?.attributes?."display-ttl-ms",
		setBy:data?.puckStates[0]?.attributes?."set-by",
		changeSet:data?.puckStates[0]?.attributes?."changeset"
	]
          
	traceEvent(settings.logFilter,"refresh_puckStates>dataEvents= ${dataEvents}", settings.trace)
    
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_pucksStates>done for puckId =${puckId}", settings.trace)
}

def pollAsyncResponsePuckReadings(response, data) {	
	def TOKEN_EXPIRED=401
  
  
	if (response.hasError()) {
		if (response?.status == TOKEN_EXPIRED) { // token is expired
			traceEvent(settings.logFilter,"pollAsyncResponsePuckReadings>Flair's Access token has expired, trying to refresh tokens now...", settings.trace, get_LOG_WARN())
			refresh_tokens()      
		} else if ((response?.errorMessage) && (!response.errorMessage.contains("timeout"))) {            
			traceEvent(settings.logFilter,"pollAsyncResponsePuckReadings>Flair response error: $response.errorMessage", true, get_LOG_ERROR())
			state?.exceptionCount=((state?.exceptionCount)?:0) +1        
		} else {
			traceEvent(settings.logFilter,"pollAsyncResponsePuckReadings>Flair response error: $response.errorMessage", true, get_LOG_WARN())
		}
	} else {
		def responseValues = null
		try {
			// json response already parsed into JSONElement object
			responseValues = response.json  
		} catch (e) {
			traceEvent(settings.logFilter,"pollAsyncResponsePuckReadings>Flair - error parsing json from response: $e")
		}
		if (responseValues) {
			traceEvent(settings.logFilter,"pollAsyncResponsePuckReadings>responseValues=$responseValues", settings.trace, get_LOG_TRACE())
			def puckId = responseValues.data[0]?.relationships?.puck?.data?.id   
			if (settings.trace) {
				def createdAt = responseValues.data[0]?.attributes?."created-at"
				def roomTemperature = responseValues.data[0]?.attributes?."room-temperature-c"
				def roomPressure = responseValues.data[0]?.attributes?."room-pressure"
				def humidity = responseValues.data[0]?.attributes?.humidity						                    
				def rssi = responseValues.data[0]?.attributes?.rssi                    
				def firmwareW =  responseValues.data[0]?.attributes?."firmware-version-w"                    
				def firmwareB =  responseValues.data[0]?.attributes?."firmware-version-b"                    
				def firmwareS =  responseValues.data[0]?.attributes?."firmware-version-s"                    
				def systemVoltage = responseValues.data[0]?.attributes?."system-voltage"
				def light=   responseValues.data[0]?.attributes?.light              
				traceEvent(settings.logFilter,"pollAsyncResponsePuckReadings>puckId=${puckId}, createdAt=$createdAt, roomTemperature= $roomTemperature," +
					"roomPressure=$roomPressure, humidity=$humidity, rssi=$rssi,firmwareW=$firmwareW, firmwareB=$firmwareB, firmwareS=$firmwareS," +
					"systemVoltage=$systemVoltage, light=$light",settings.trace)     

			}
			refresh_puckReadings(puckId,responseValues)
			retrieveDataForGraph()            
			state.lastPollTimestamp = now()
			state?.exceptionCount=0
		}                
	}        
}

// puckId is single puckId (not a list)
// @asyncValues is null by default when called in synchrone; otherwise contains the set of values from asynchronous call
private def refresh_puckReadings(puckId, asyncValues=null) {	
	def MAX_INDICE=5
    
	puckId=determine_puck_id(puckId)

	if (!data?.puckReadings) {    
		data?.puckReadings=[]
	}    

	if (!asyncValues) {
		getPuckReadings(puckId, 'current-reading')
		String exceptionCheck = device.currentValue("verboseTrace").toString()
		if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {  
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
			traceEvent(settings.logFilter,"poll>$exceptionCheck for puckId =${puckId}", settings.trace,get_LOG_ERROR() )
			return    
		}    
	} else {
		int max_ind= (asyncValues.data) ? (asyncValues.data.size()-1) :-1               
		max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
		for (i in 0..max_ind) {
			data?.puckReadings[i] =asyncValues.data[i]                    
		}            
	}    
	traceEvent(settings.logFilter,"refresh_puckReadings>data?.puckReadings[0]= ${data?.puckReadings[0]}",settings.trace)    
    
	def dataEvents = [
		pressure: data?.puckReadings[0]?.attributes?."room-pressure",
		isGateway:data?.puckReadings[0]?.attributes?."is-gateway",				                    
		rssi:data?.puckReadings[0]?.attributes?.rssi,
		battery: getBatteryUsage(),	
		systemVoltage: data?.puckReadings[0]?.attributes?."system-voltage",       
		messageVersion: data?.puckReadings[0]?.attributes?."message-version",
		rotaryEncodedClicks:data?.puckReadings[0]?.attributes?."rotary-encoded-clicks",
		buttonPushes:data?.puckReadings[0]?.attributes?."button-pushes",        
		desiredTemperature: data?.puckReadings[0]?.attributes?."desired-temperature-c",
		desiredTemperatureDisplay: data?.puckReadings[0]?.attributes?."desired-temperature-c",
		thermostatSetpoint: data?.puckReadings[0]?.attributes?."desired-temperature-c",
		temperature: data?.puckReadings[0]?.attributes?."room-temperature-c",
		temperatureDisplay: data?.puckReadings[0]?.attributes?."room-temperature-c",
		dieTemperature: data?.puckReadings[0]?.attributes?."die-temperature",
		firmwareW:data?.puckReadings[0]?.attributes?."firmware-version-w",                    
		firmwareB:data?.puckReadings[0]?.attributes?."firmware-version-b",                    
		firmwareS:data?.puckReadings[0]?.attributes?."firmware-version-s"                    
	]
          
	traceEvent(settings.logFilter,"refresh_puckReadings>dataEvents= ${dataEvents}", settings.trace)
    
	generateEvent(dataEvents)
	traceEvent(settings.logFilter,"refresh_puckReadings>done for puckId =${puckId}", settings.trace)
}

private void generateEvent(Map results) {
    
	state?.scale = getTemperatureScale() // make sure to display in the right scale
	def scale = state?.scale
//	log.debug ("generateEvent>scale = $scale")    
	if (results) {
		results.each { name, value ->
			def isDisplayed = true

			String upperFieldName = name.toUpperCase()
// 			Temperature variable names for display contain 'display'            

			if (upperFieldName.contains("DISPLAY")) {  

				String tempValueString 
				double tempValue 
				if (scale == "C") {
					tempValue = getTemperatureValue(value).toDouble().round(1)
					tempValueString = String.format('%2.1f', tempValue)
					if ((upperFieldName.contains("DESIRED") || upperFieldName.contains("SETPOINT"))) { 
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
					tempValue = getTemperatureValue(value).toDouble().round()
					tempValueString = String.format('%2d', tempValue.intValue())            
				}
				def isChange = isStateChange(device, name, tempValue.toString())
                
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed)                                     									 

// 			Temperature variable names contain 'temp' or 'setpoint' (not for display)           

			} else if (upperFieldName.contains("DIETEMPERATURE")) {  
				double tempValue = value?.toDouble().round(1)
				String tempValueString = String.format('%2.1f', tempValue)
				def isChange = isStateChange(device, name, tempValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed)                                     									 
			} else if ((upperFieldName.contains("TEMP")) || (upperFieldName.contains("SETPOINT"))) {  
                                
				double tempValue = getTemperatureValue(value).toDouble().round(1)
				String tempValueString = String.format('%2.1f', tempValue)
				if (scale == "C") {
					if ((upperFieldName.contains("DESIRED") || upperFieldName.contains("SETPOINT"))) { 
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
				}
				if (tempValueString != '0.0') {    // don't post value if all zeros                
					def isChange = isStateChange(device, name, tempValueString)
					isDisplayed = isChange
					sendEvent(name: name, value: tempValueString, unit: scale, displayed: isDisplayed, isStateChange: isChange)
				}
                        
                    
			} else if ((upperFieldName.contains("HUMIDITY")) || (upperFieldName.contains("BATTERY"))) {
				value=(value?:0)
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
				sendEvent(name: name, value: pressureValueString, unit: "Pa", displayed: isDisplayed, isStateChange: isChange)
                sendEvent(name: "flairPressure", value: "${pressureValueString} Pa")
			} else if (upperFieldName.contains("DATA")) { // data variable names contain 'data'

				sendEvent(name: name, value: value, displayed: (settings.trace?:false))                                     									 
			} else if (upperFieldName.contains("DROPRATE")) { 
				sendEvent(name: "flairDropRate", value: "${value} Rate")
            
			} else if (upperFieldName.contains("RSSI")) { 
				sendEvent(name: "flairRSSI", value: "${value} dB")

			} else if (upperFieldName.contains("ROOMNAME")) { 
				sendEvent(name: "flairRoom", value: value)

			} else if (upperFieldName.contains("ZONENAME")) { 
				sendEvent(name: "flairZone", value: value)

			} else if (upperFieldName.contains("UPDATEDAT")) { 
				sendEvent(name: "flairStatus", value: "Update: ${value}")

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

private def getDistanceScale() {
	def scale= state?.scale
	if (scale == 'C') {
		return "kmh"
	}
	return "mph"
}


// @value must be 'auto',  or 'manual' (default) 

void setStructureMode(value) {

	traceEvent(settings.logFilter,"setting StructureMode: ${value}", settings.trace)
	def structureId=device.currentValue("structureId")

	value=( (value.toLowerCase().contains('auto')) ? 'auto': 'manual')
	setStructure(structureId, ['mode':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setStructureMode>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	        
	sendEvent(name: "stMode", value: value)

	traceEvent(settings.logFilter, "setStructureMode>done for structureId=${structureId}", settings.trace)
}

// @value must be 'smart away',  'defer to rooms', or 'off' (default) 

void setStructureAwayMode(value) {
	def FLAIR_SMART_MODE ='Smart Away'
	def FLAIR_DEFER_TO_ROOMS ='Defer To Rooms'
 	def FLAIR_OFF_MODE ='Off Only'

	traceEvent(settings.logFilter,"setting StructureAwayMode: ${value}", settings.trace)
	def structureId=device.currentValue("structureId")

	value=( (value.toLowerCase().contains('smart')) ? FLAIR_SMART_MODE : value.toLowerCase().contains('rooms')? FLAIR_DEFER_TO_ROOMS : FLAIR_OFF_MODE)
	setStructure(structureId, ['structure-away-mode':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setStructureAwayMode>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	def st_value = get_st_corresponding_value(value)    
	        
	sendEvent(name: "stAwayMode", value: st_value)
    sendEvent(name: "flairAwayMode", value: st_value)
    
	traceEvent(settings.logFilter, "setStructureAwayMode>done for structureId=${structureId}", settings.trace)
}

// @value must be 'autohome',  or 'third Party' (default) 

void setStructureHomeAwayMode(value) {
	def FLAIR_AUTO_MODE ='Flair Autohome Autoaway'
	def FLAIR_3RD_PARTY_AUTO_AWAY = 'Third Party Home Away'    
    
	traceEvent(settings.logFilter,"setting StructureHomeAwayMode: ${value}", settings.trace)
	def structureId=device.currentValue("structureId")

	value=( (value.toLowerCase().contains('autohome')) ? FLAIR_AUTO_MODE : FLAIR_3RD_PARTY_AUTO_AWAY)
	setStructure(structureId, ['home-away-mode':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setStructureHomeAwayMode>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	def st_value = get_st_corresponding_value(value) 
	sendEvent(name: "stHomeAwayMode", value: st_value)
	sendEvent(name: "flairHomeAwayMode", value: st_value)

	traceEvent(settings.logFilter, "setStructureHomeAwayMode>done for structureId=${structureId}", settings.trace)
}

// @value must be a 'active rooms', 'evenness', or 'defer to rooms' (default) 

void setStructureSetpointMode(value) {
	def FLAIR_SETPOINT_ACTIVE_ROOMS ='Home Evenness For Active Rooms Flair Setpoint'
	def FLAIR_SETPOINT_EVENNESS ='Home Evenness Flair SetPoint'
	def FLAIR_SETPOINT_DEFER_TO_ROOMS ='Defer To Users and Rooms'
    
	traceEvent(settings.logFilter,"setting StructureSetpointMode: ${value}", settings.trace)
	def structureId=device.currentValue("structureId")

	value=((value.toLowerCase().contains('active rooms')) ? FLAIR_SETPOINT_ACTIVE_ROOMS : value.toLowerCase().contains('evenness')? FLAIR_SETPOINT_EVENNESS:FLAIR_SETPOINT_DEFER_TO_ROOMS)
	setStructure(structureId, ['set-point-mode':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setStructureSetpointMode>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	def st_value  = get_st_corresponding_value(value)
	sendEvent(name: "stSPointMode", value: st_value)
	sendEvent(name: "flairSetpointMode", value: st_value)

	traceEvent(settings.logFilter, "setStructureSetpointMode>done for structureId=${structureId}", settings.trace)
}

private String get_st_corresponding_value(String flair_value) {
	def FLAIR_AUTO_MODE ="Flair Autohome Autoaway"
	def FLAIR_3RD_PARTY_AUTO_AWAY = "Third Party Home Away"
	def ST_AUTO_MODE ="autohome"
	def ST_3RD_PARTY ="thirdParty"
	def FLAIR_SETPOINT_ACTIVE_ROOMS ="Home Evenness For Active Rooms Flair Setpoint"
	def FLAIR_SETPOINT_EVENNESS ="Home Evenness Flair SetPoint"
	def FLAIR_SETPOINT_DEFER_TO_ROOMS ="Defer To Users and Rooms"
	def ST_SETPOINT_ACTIVE_ROOMS ="Evenness Active"
	def ST_SETPOINT_EVENNESS ="Evenness"
	def ST_SETPOINT_DEFER_TO_ROOMS ="Defer Rooms"
	def FLAIR_SMART_MODE ="Smart Away"
	def FLAIR_DEFER_TO_ROOMS ="Defer To Rooms"
 	def FLAIR_OFF_MODE ="Off Only"
	def ST_SMART_MODE ="Smart Away"
	def ST_DEFER_TO_ROOMS ="Rooms"
 	def ST_OFF_MODE ="Off"

	def result

	switch (flair_value) {
		case FLAIR_SMART_MODE:
			result = ST_SMART_MODE
		break       
		case FLAIR_DEFER_TO_ROOMS:
			result = ST_DEFER_TO_ROOMS
		break       
		case FLAIR_SETPOINT_ACTIVE_ROOMS:
			result=ST_SETPOINT_ACTIVE_ROOMS
		break       
		case FLAIR_SETPOINT_EVENNESS:
			result=ST_SETPOINT_EVENNESS
		break       
		case FLAIR_SETPOINT_DEFER_TO_ROOMS:
			result=ST_SETPOINT_DEFER_TO_ROOMS
		break       
		case FLAIR_AUTO_MODE:
			result=ST_AUTO_MODE
		break       
		case FLAIR_3RD_PARTY_AUTO_AWAY:
			result=ST_3RD_PARTY
		break       
		case FLAIR_SMART_MODE:
			result=ST_SMART_MODE
		break       
		case FLAIR_DEFER_TO_ROOMS:
			result=ST_DEFER_TO_ROOMS
		break       
		case FLAIR_OFF_MODE:
			result=ST_OFF_MODE
		break       
	}
	return result
}

//	@structureId		Id of the Structure, by default the current one
//	@attribute			Structure Attribute(s) set to be changed in Map
void setStructure(structureId, attributes=[]) {
	String URI_ROOT = "${get_URI_ROOT()}"

	if (!structureId) {
		structureId=device.currentValue("structureId")
    
	}    


	if (attributes == null || attributes == "" || attributes == [] ) {
		traceEvent(settings.logFilter, "setStructure>attributes set is empty, exiting", settings.trace)
		return        
	}
    
	def currentAttributes =new groovy.json.JsonBuilder(attributes) 
	def bodyReq = '{"data":{"id":' + structureId + ',"type":"structures","attributes":' + currentAttributes +'}}'
	def args = [
			attributes: bodyReq
		]
    
	def params = [
			uri: "${URI_ROOT}/api/structures/${structureId}",
			body: bodyReq,            
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
//				'Content-Type': "text/json",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	traceEvent(settings.logFilter, "setStructure>about to call patchObjectAsync with bodyReq=${bodyReq}", settings.trace)
	asynchttp_v1.patch('patchObjectAsync', params, args)
	runIn(30,"refresh_structure_async")    // refresh the structure values in 30 sec.
	traceEvent(settings.logFilter, "setStructure>done for structureId=${structureId}", settings.trace)
}

void refresh_structure_async() {
	getStructure("",true) 	// force update of the local cache            
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
	sendEvent(name: "thermostatSetpoint", value: value, unit: state?.scale)    

	traceEvent(settings.logFilter, "setRoomSetpoint>done for roomId=${roomId}", settings.trace)
}

// @roomId			Id of the Room, by default the current one
// @attribute			Room Attribute(s) set to be changed in Map
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
//			log.debug("patchObjectAsync> - patch api call with attributes $attributes sent successfully")
			state?.exceptionCount=0
			state.lastPollTimestamp = now()
		}
	}
}


// @puckId		Id of the Puck, by default the current one
// @attribute		Puck Attribute(s) to be changed in a Map
void setPuck(puckId, attributes=[]) {
	String URI_ROOT = "${get_URI_ROOT()}"

	if (!puckId) {
		puckId=device.currentValue("puckId")
	}    

	if (attributes == null || attributes == "" || attributes == [] ) {
		traceEvent(settings.logFilter, "setPuck>attributes set is empty, exiting", settings.trace)
		return        
	}
    
	def currentAttributes =new groovy.json.JsonBuilder(attributes) 
	def bodyReq = '{"data":{"id":"' + puckId + '","type":"pucks","attributes":' + currentAttributes +'}}'
	def args = [
			attributes: bodyReq
		]
    
	def params = [
			uri: "${URI_ROOT}/api/pucks/${puckId}",
			body: bodyReq,            
			headers: [
				'Authorization': "${data.auth.token_type} ${data.auth.access_token}",
				'charset': "UTF-8",
				'Accept': "${get_APPLICATION_VERSION()}"
			]
		]
	traceEvent(settings.logFilter, "setPuck>about to call patchObjectAsync with bodyReq=${bodyReq}", settings.trace)
	asynchttp_v1.patch('patchObjectAsync', params, args)
	traceEvent(settings.logFilter, "setPuck>done for puckId=${puckId}", settings.trace)
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
		'getPuckInfo': 
			[uri:"${URI_ROOT}/api/pucks/${id}", 
				type:'get'],
		'getPuckReadings': 
			[uri:"${URI_ROOT}/api/pucks/${id}/sensor-readings", 
				type:'get'],
		'getBeaconSightings': 
			[uri:"${URI_ROOT}/api/pucks/${id}/beacon-sightings]", 
				type:'get'],
		'getPuckStates': 
			[uri:"${URI_ROOT}/api/pucks/${id}/puck-states", 
				type:'get'],
		'current-state': 
			[uri:"${URI_ROOT}/api/pucks/${id}/current-state", 
				type:'get'],
		'previous-state': 
			[uri:"${URI_ROOT}/api/pucks/${id}/puck-states", 
				type:'get'],
		'setPuckState': [uri: "${URI_ROOT}/api/puck-states", 
				type: 'post']
		]	        
	def request = methods.getAt(method)
	if (request.type=="get" && args) {
//		def args_encoded = java.net.URLEncoder.encode(args.toString(), "UTF-8")
//		request.uri=request.uri + "?${args_encoded}"    
		request.uri=request.uri + "?${args}"    
	}    
	doRequest(request.uri, args, request.type, success)
	if (state.exceptionCount >= MAX_EXCEPTION_COUNT) {
		def exceptionCheck=device.currentValue("verboseTrace")
		traceEvent(settings.logFilter,"api>error: found a high number of exceptions (${state.exceptionCount}), last exceptionCheck=${exceptionCheck}, about to reset counter",true)         
		if (!exceptionCheck.contains("Unauthorized")) {          
			sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
			state.exceptionCount = 0  // reset the counter as long it's not unauthorized exception
		}            
	}        

}

// @doRequest: need to be authenticated in before this is called. So don't call this. Call api.
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
			params?.body=args        
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



void setPuckOrientation(value) {
	traceEvent(settings.logFilter,"setting Orientation: ${value}", settings.trace)
	def puckId = determine_puck_id("")
	setPuck(puckId, ['orientation':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckOrientation>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "orientation", value: value)

	traceEvent(settings.logFilter, "setPuckOrientation>done for puckId=${puckId}", settings.trace)

}

// @value must be a boolean
void setPuckInactive(value) {
	traceEvent(settings.logFilter,"setting PuckInactive: ${value}", settings.trace)
	def puckId = determine_puck_id("")
	boolean valueBoolean =(value==true)?:false    

	setPuck(puckId, ['inactive':valueBoolean])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckInactive>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "inactive", value: valueBoolean)

	traceEvent(settings.logFilter, "setPuckInactive>done for puckId=${puckId}", settings.trace)
}

//	@value must be a integer
void setPuckHumidityOffset(value) {
	traceEvent(settings.logFilter,"setting Humidity Offset: ${value}", settings.trace)
	if (value ==null) {
		return	    
	}
	def puckId = determine_puck_id("")
	setPuck(puckId, ['humidity-offset':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckHumidityOffset>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "humidityOffset", value: value)

	traceEvent(settings.logFilter, "setPuckHumidityOffset>done for puckId=${puckId}", settings.trace)

}

// @value must be a float (ex. 5.0) 
void setPuckTempOffset(value) {
	traceEvent(settings.logFilter,"setting Temperature Offset: ${value}", settings.trace)
	if (value ==null) {
		return	    
	}
	value = value.toFloat()    
	def puckId = determine_puck_id("")
	float tempValue=(state?.scale !='C') ? fToC(value) : value    
	setPuck(puckId, ['temperature-offset-override-c':tempValue])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckTempOffset>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "tempOffset", value: value)

	traceEvent(settings.logFilter, "setPuckTempOffset>done for puckId=${puckId}", settings.trace)

}

void setPuckText(value) {
	traceEvent(settings.logFilter,"setting Display Text: ${value}", settings.trace)
	def puckId = determine_puck_id("")
	setPuck(puckId, ['display-text':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckText>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "puckText", value: value)

	traceEvent(settings.logFilter, "setPuckText>done for puckId=${puckId}", settings.trace)

}

void setPuckImage(value) {
	traceEvent(settings.logFilter,"setting Display Text: ${value}", settings.trace)
	def puckId = determine_puck_id("")
	setPuck(puckId, ['display-image':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckImage>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "puckImage", value: value)

	traceEvent(settings.logFilter, "setPuckImage>done for puckId=${puckId}", settings.trace)

}

void setPuckScale(value) {
	traceEvent(settings.logFilter,"setting Display Scale: ${value}", settings.trace)
	value=(value !='C') ? 'F' : 'C'    
	def structureId = determine_structure_id("")
	setStructure(structureId, ['temperature-scale':value])
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setPuckScale>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "puckScale", value: value)

	traceEvent(settings.logFilter, "setPuckScale>done for puckId=${puckId}", settings.trace)

}

// @value must be a float (ex. 5.0) 
void setThermostatSetpoint(temp) {
	//sendEvent(name: "thermostatSetpoint", value: temp)
    setDesiredTemp(temp)
}

// @value must be a float (ex. 5.0) 
void setDesiredTemp(value) {
	traceEvent(settings.logFilter,"setting Desired temp: ${value}", settings.trace)
	def puckId = determine_puck_id("")
	setRoomSetpoint(value)
	String exceptionCheck = device.currentValue("verboseTrace").toString()
	if ((exceptionCheck.contains("exception")) || (exceptionCheck.contains("error"))) {
		// check if there is any exception or an error reported in the verboseTrace associated to the device 
		traceEvent(settings.logFilter, "setDesiredTemp>$exceptionCheck", settings.trace, get_LOG_ERROR())
		return
	}
	sendEvent(name: "desiredTemperature", value: value)
	sendEvent(name: "desiredTemperatureDisplay", value: value)
    
    

	traceEvent(settings.logFilter, "setDesiredTemp>done for puckId=${puckId}", settings.trace)

}


// @id			Id of the puck 
// @stateAttributes	Map of the puck attributes to be changed
void setPuckState(puckId, stateAttributes=[]) {
	def FLAIR_SUCCESS =200
	def FLAIR_CREATED =201    
	def TOKEN_EXPIRED=401


	puckId = determine_puck_id(puckId)
	if (stateAttributes == null || stateAttributes == "" || stateAttributes == [] ) {
		traceEvent(settings.logFilter, "setPuckState>currentState object is empty, exiting", settings.trace)
		return        
	}
    
	def currentStateAttributes =new groovy.json.JsonBuilder(stateAttributes) 
	def bodyReq = '{"data":{"type":"puck-states","attributes":' + currentStateAttributes +',"relationships":{"puck":{"data":{"type":"pucks","id":"' + puckId + '"}}}}}'
    
	traceEvent(settings.logFilter, "setPuckState>about to call setPuckState api with bodyReq=${bodyReq}", settings.trace)
	int statusCode = 1
	int j = 0
	while ((statusCode != FLAIR_SUCCESS && statusCode != FLAIR_CREATED) && (j++ < 2)) { // retries once if api call fails
		api('setPuckState', puckId, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "setPuckState>puckId=${puckId}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()           
			}
			if (statusCode in [FLAIR_SUCCESS, FLAIR_CREATED]) {
				/* when success, reset the exception counter */
				state.exceptionCount = 0
				traceEvent(settings.logFilter, "setPuckState>done for ${puckId}", settings.trace)
				state.lastPollTimestamp = now()  // To avoid polling right after changing the state
			} else {
				state.exceptionCount = state.exceptionCount + 1
				traceEvent(settings.logFilter, "setPuckState>error=${statusCode.toString()} for ${puckId}", settings.trace)
			} /* end if statusCode */
		} /* end api call */
	} /* end while */
}


// @id		Id of the puck by default, retrieve all pucks under a user 
// @postData	indicates whether the data should be posted for further processing [optional]
void getPuckInfo(id, postData = false) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	def puckData = []
	def puckList = ""
	def bodyReq = ""
	def statusCode = true
	int j = 0

	traceEvent(settings.logFilter, "getPuckInfo>bodyReq=${bodyReq},id=$id", settings.trace)
	if (!data?.pucks) {    
		data?.pucks=[]
	}        
	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getPuckInfo', id, bodyReq) {resp->
			statusCode = resp?.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getPuckInfo>puckId=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getPuckInfo>resp.data=${resp.data}", settings.trace)
				
				if (resp.data.data instanceof Collection) {
					data?.pucks = resp.data.data
				} else {                         
					data?.pucks[0] = resp.data.data
				}                          

				data?.pucks.each {
					def puckId = it?.id
					def name= it.attributes?.name  
					def currentTemperature = it?.attributes?."current-temperature-c"
					def currentHumidity = it?.attributes?."current-humidity"						                    
                    
					def beaconSightings = it?.relationships?."beacon-sightings"
					def room = it.relationships?.room                         
                        
//					log.debug "getPuckInfo>puckId=${puckId}, name= $name," +
//						"sensor-readings=${sensorReadings}, beacon-sightings=${beaconSightings}, room=${room}," +
//						"currentTemperature = $currentTemperature, currentHumidity=$currentHumidity"
					traceEvent(settings.logFilter, "getPuckInfo>puckId=${puckId}, name= $name," +
						"beacon-sightings=${beaconSightings}, room=${room}," +
						"currentTemperature = $currentTemperature, currentHumidity=$currentHumidity", settings.trace)
					if (postData) {
						traceEvent(settings.logFilter, "getPuckInfo>adding ${it} to ventData", settings.trace)
						puckData << it // to be transformed into Json later
					}
					puckList = puckList + puckId + ','
				} /* end for each thermostat */
				traceEvent(settings.logFilter, "getPuckInfo>done for puckId=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getPuckInfo>error=${statusCode.toString()} for puckId=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	def puckDataJson = ""

	if (puckData != []) {
		statDataJson = new groovy.json.JsonBuilder(puckData)
	}
	/*	
	traceEvent(settings.logFilter,"getPuckInfo>puckDataJson=${puckDataJson}", settings.trace)
	*/
	def puckEvents = [
			puckData: "${puckDataJson.toString()}",
			puckList: "${puckList.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"getPuckInfo>ventListEvents to be sent= ${ventListEvents}", settings.trace)
	*/
	generateEvent(puckEvents)
    

}

// @id			Id of the puck, by default the current puck 
// @method		possible values: current-state, previous-state, by default=all states  [optional]
// @postData		Indicates whether the data should be posted for further processing [optional]
// @postTimestamp	timestamp (from) threshold to post states [optional]
void getPuckStates(id, method="",postData=false, postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	def MAX_INDICE=5    
	def FLAIR_CURRENT_STATE='current-state'    
	def FLAIR_PREVIOUS_STATE='previous-state'    
	def FLAIR_ALL_STATES='getPuckStates'    
	def puckData = []
	def statusCode = true
	int j = 0

	if (!id) {
		id = device.currentValue("puckId")
		if (!id) {        
			traceEvent(settings.logFilter, "getPuckStates>id is null, exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	def bodyReq = ""
	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_STATE)?FLAIR_CURRENT_STATE:((method==FLAIR_PREVIOUS_STATE)?FLAIR_PREVIOUS_STATE:FLAIR_ALL_STATES)    
/*
	if (method in [FLAIR_CURRENT_STATE, FLAIR_PREVIOUS_STATE] ) {
		bodyReq=bodyReq + "page[size]=1"    
	}
*/    
	traceEvent(settings.logFilter, "getPuckStates>method=$method,bodyReq=${bodyReq}, id=$id", settings.trace)


	if (!data?.puckStates) {
		data?.puckStates=[]    
	}    
	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api(method, id, bodyReq) {resp->
			statusCode = resp.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getPuckStates>id=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getPuckStates>resp.data=${resp.data}", settings.trace)
				if (resp.data.data instanceof Collection) {
 					data?.puckStates = resp.data.data
				} else {                         
					data?.puckStates[0] = resp.data.data
				}                          
				int max_ind= (data?.puckStates) ? (data?.puckStates.size()-1):-1  
				max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
                
				for (i in 0..max_ind) {
					def desiredTemperature = data?.puckStates[i]?."desired-temperature-c"
					def temperatureScale = data?.puckStates[i]?.attributes?."temperature-scale"
					def irSetup = data?.puckStates[i]?.attributes?."ir-setup"				                    
					def irDownload = data?.puckStates[i]?.attributes?."ir-download"                 
					def createdAt=data?.puckStates[i]?.attributes."created-at"
					def puckId = data?.puckStates[i]?.relationships?.puck?.data?.id
					traceEvent(settings.logFilter,"getPuckStates>puckId=${puckId}, state no ${i}, createdAt=$createdAt, desiredTemperature= $desiredTemperature," +
						"temperatureScale=$temperatureScale, irSetup=$irSetup, irDownload=$irDownload", settings.trace)

					if (method == FLAIR_CURRENT_STATE) {
						if (postData) { 
							puckData << data?.puckStates[i] 
						}                        
						break                    
					} else if ((method == FLAIR_PREVIOUS_STATE) && (((max_ind > 0) && (i==1)) || (max_ind ==0))) {
						if (postData) { 
							puckData =[] // just generate the json for the current row
							puckData << data?.puckStates[i]                         
						}                            
						break                  
					} else if (postData) {
						if (postTimestamp) {
							// Save states greater than timestamp (
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getPuckStates>adding ${data?.puckStates[i]} to puckData, createdDate ($createdDate.getTime()) is greater than timestamp ($postTimestamp)", settings.trace)
								puckData << data?.puckStates[i] // to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getPuckStates>>adding ${data?.puckStates[i]} to puckData", settings.trace)
							puckData << data?.puckStates[i] // to be transformed into Json later
						}                    
                    
                    
					}                        
				} /* end for each state */
				traceEvent(settings.logFilter, "getPuckStates>done for id=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getPuckStates>error=${statusCode.toString()} for id=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	generate_puck_state_json(puckData)
}


private def generate_puck_state_json(puckData) {

	def puckDataJson = ""

	if (puckData != []) {
		puckDataJson = new groovy.json.JsonBuilder(puckData)
	}
	/*	
	traceEvent(settings.logFilter,"generate_puck_data_json>puckDataJson=${puckDataJson}", settings.trace)
	*/
	def puckEvents = [
			puckStatesData: "${puckDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"generate_puck_data_json>puckListEvents to be sent= ${puckListEvents}", settings.trace)
	*/
	generateEvent(puckEvents)
}

// @id			Id of the puck, by default the current puck
// @postData		Indicates whether the data should be posted for further processing  [optional]
// @method		possible values: current-reading, previous-reading, by default=all readings
// @postTimestamp	timestamp (from) threshold to post readings [optional]

void getPuckReadings(id, method="", postData=false, postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	def MAX_INDICE=5   
	String FLAIR_CURRENT_READING="current-reading"   
	String FLAIR_PREVIOUS_READING="previous-reading"    
	String FLAIR_ALL_READINGS="getPuckReadings"    
	def puckData = []
	def statusCode = true
	int j = 0

	if (!id) {
		id = device.currentValue("puckId")
		if (!id) {        
			traceEvent(settings.logFilter, "getPuckReadings>id is null, exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	def bodyReq = ""

	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_READING)?FLAIR_CURRENT_READING:((method==FLAIR_PREVIOUS_READING)?FLAIR_PREVIOUS_READING:FLAIR_ALL_READINGS)    
/*
	if (method in [FLAIR_CURRENT_READING, FLAIR_PREVIOUS_READING] ) {
		bodyReq=bodyReq + "page[size]=1"    
	}
*/    
	traceEvent(settings.logFilter, "getPuckReadings>method=$method, bodyReq=${bodyReq}, id=$id", settings.trace)
	if (!data?.puckReadings) {
		data?.puckReadings=[]    
	}    

	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		traceEvent(settings.logFilter, "getPuckReadings>About to call api with id=$id", settings.trace)
		api('getPuckReadings', id, bodyReq) {resp->
			statusCode = resp.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getPuckReadings>id=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getPuckReadings>resp.data=${resp.data}", settings.trace)
/*
				if (resp.data.data instanceof Collection) {
 					data?.puckReadings = resp.data.data
				} else {                         
					data?.puckReadings[0] = resp.data.data
				}                          
*/                
				int max_ind= (resp.data.data) ? (resp.data.data.size()-1) :-1               
				max_ind=(max_ind> MAX_INDICE)? MAX_INDICE:max_ind                
				for (i in 0..max_ind) {
					data?.puckReadings[i] =resp.data.data[i]                    
					def createdAt = resp.data.data[i]?.attributes?."created-at"
					def roomTemperature = resp.data.data[i]?.attributes?."room-temperature-c"
					def roomPressure = resp.data.data[i]?.attributes?."room-pressure"
					def humidity = resp.data.data[i]?.attributes?.humidity					                    
					def voltage = resp.data.data[i]?.attributes?."system-voltage"						                    
					def rssi = resp.data.data[i]?.attributes?.rssi                   
					def puckId = resp.data.data[i]?.relationships?.puck?.data?.id
					def firmwareW = resp.data.data[i]?.attributes?."firmware-version-w"                    
					def firmwareB = resp.data.data[i]?.attributes?."firmware-version-b"                    
					def firmwareS = resp.data.data[i]?.attributes?."firmware-version-s"                    
					def	light= resp.data.data[i]?.attributes?.light
					traceEvent(settings.logFilter,"getPuckReadings>puckId=${puckId}, reading no ${i}, createdAt=$createdAt,roomTemperature= $roomTemperature," +
						"roomPressure=$roomPressure, humidity=$humidity, rssi=$rssi, voltage=$voltage, firmwareW=$firmwareW, firmwareB=$firmwareB," +
						"firmwareS=firmwareS, light=$light",settings.trace)     

					if (method == FLAIR_CURRENT_READING) {
						if (postData) { 
							puckData << data?.puckReadings[i] 
						}                        
						break                    
					} else if ((method == FLAIR_PREVIOUS_READING) && (((max_ind > 0) && (i==1)) || (max_ind ==0))) {
						if (postData) { 
							puckData =[] // just generate the json for the current row
							puckData << data?.puckReadings[i]                         
						}                            
						break                  
					} else if (postData) {
						if (postTimestamp) {
							// Save readings greater than timestamp (
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getPuckReadings>adding ${data?.puckReadings[i]} to puckData, createdDate ($createdDate.getTime()) is greater than timestamp ($postTimestamp)", settings.trace)
								puckData << data?.puckReadings[i]// to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getPuckReadings>adding ${data?.puckReadings[i]} to puckData", settings.trace)
							puckData << data?.puckReadings[i] // to be transformed into Json later
						}                    
					}                    
				} /* end for each sensor-readings  */
				traceEvent(settings.logFilter, "getPuckReadings>done for id=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getPuckReadings>error=${statusCode.toString()} for id=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	generate_puck_readings_json(puckData) 
}

private def generate_puck_readings_json(puckData) {
	def puckDataJson = ""

	if (puckData != []) {
		puckDataJson = new groovy.json.JsonBuilder(puckData)
	}
	/*	
	traceEvent(settings.logFilter,"generate_puck_readings_json>puckDataJson=${puckDataJson}", settings.trace)
	*/
	def puckEvents = [
			puckReadingsData: "${puckDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"generate_puck_readings_json>puckListEvents to be sent= ${puckListEvents}", settings.trace)
	*/
	generateEvent(puckEvents)

}


// @id			Id of the puck, by default the current puck
// @postData		Indicates whether the data should be posted for further processing  [optional]
// @method		possible values: current-reading, previous-reading, by default=all readings
// @postTimestamp	timestamp (from) threshold to post readings [optional]

void getBeaconSightings(id, method="", postData=false, postTimestamp=null) {
	def FLAIR_SUCCESS = 200
	def TOKEN_EXPIRED = 401
	String FLAIR_CURRENT_SIGHTING="current-sighting"   
	String FLAIR_PREVIOUS_SIGHTING="previous-sighting"    
	String FLAIR_ALL_SIGHTINGS="getBeaconSightings"    
	def puckData = []
	def statusCode = true
	int j = 0

	if (!id) {
		id = device.currentValue("puckId")
		if (!id) {        
			traceEvent(settings.logFilter, "getBeaconSightings>id is null, exiting", settings.trace, get_LOG_ERROR())
			return
		}            
	}
	def bodyReq = ""

	method=method.trim().toLowerCase()
	method=(method==FLAIR_CURRENT_SIGHTING)?FLAIR_CURRENT_SIGHTING:((method==FLAIR_PREVIOUS_SIGHTING)?FLAIR_PREVIOUS_SIGHTING:FLAIR_ALL_SIGHTINGS)    
	traceEvent(settings.logFilter, "getBeaconSightings>method=$method, bodyReq=${bodyReq}, id=$id", settings.trace)

	if (!data?.beaconSightings) {
		data?.beaconSightings=[]    
	}    

	while ((statusCode != FLAIR_SUCCESS) && (j++ < 2)) { // retries once if api call fails
		api('getBeaconSightings', id, bodyReq) {resp->
			statusCode = resp.status
			if (statusCode==TOKEN_EXPIRED) {
				traceEvent(settings.logFilter, "getBeaconSightings>id=${id}, error $statusCode, need to call refresh_tokens()", settings.trace)
				refresh_tokens()
			}
			if (statusCode == FLAIR_SUCCESS) {
				traceEvent(settings.logFilter, "getBeaconSightings>resp.data=${resp.data}", settings.trace)
				if (resp.data.data instanceof Collection) {
 					data?.beaconSightings = resp.data.data
				} else {                         
					data?.beaconSightings[0] = resp.data.data
				}                          
				int max_ind= (data?.beaconSightings) ? (data?.beaconSightings.size()-1) :-1               
				for (i in 0..max_ind) {
					def createdAt = data?.beaconSightings[i]?.attributes?."created-at"
					def observerDeviceUuid = data?.beaconSightings[i]?.attributes?."observer-device-uuid"
					def distance = data?.beaconSightings[i]?.attributes?."distance"
					def rssi = data?.beaconSightings[i]?.attributes?.rssi                    
					def puckId = data?.beaconSightings[i]?.relationships?.puck?.data?.id
					traceEvent(settings.logFilter,"getBeaconSigtings>puckId=${puckId}, sighting no ${i}, createdAt=$createdAt,distance= $distance," +
						"observerDeviceUuid=$observerDeviceUuid,rssi=$rssi",settings.trace)     

					if (method == FLAIR_CURRENT_SIGHTING) {
						if (postData) { 
							puckData << data?.beaconSightings[i] 
						}                        
						break                    
					} else if ((method == FLAIR_PREVIOUS_SIGHTING) && (((max_ind > 0) && (i==1)) || (max_ind ==0))) {
						if (postData) { 
							puckData =[] // just generate the json for the current row
							puckData << data?.beaconSightings[i]                         
						}                            
						break                  
					} else if (postData) {
						if (postTimestamp) {
							// Save sightings greater than timestamp (
							Date createdDate=ISODateFormat(createdAt)
							if ((createdDate) && (createdDate.getTime() > postTimestamp)) { 
								traceEvent(settings.logFilter, "getBeaconSightings>adding ${data?.beaconSightings} to puckData, createdDate ($createdDate.getTime()) is greater than timestamp ($postTimestamp)", settings.trace)
								puckData << data?.beaconSightings[i]// to be transformed into Json later
							}                                                        
						} else {
							traceEvent(settings.logFilter, "getBeaconSightings>adding ${data?.puckReadings[i]} to puckData", settings.trace)
							puckData << data?.beaconSightings[i] // to be transformed into Json later
						}                    
					}                    
				} /* end for each beacon-sightings  */
				traceEvent(settings.logFilter, "getBeaconSightings>done for id=${id}", settings.trace)
			} else {
				traceEvent(settings.logFilter, "getBeaconSightings>error=${statusCode.toString()} for id=${id}", settings.trace)
			}
		} /* end api call */
	} /* end while */
	generate_beacon_sightings_json(puckData) 
}



private def generate_beacon_sightings_json(puckData) {
	def puckDataJson = ""

	if (puckData != []) {
		puckDataJson = new groovy.json.JsonBuilder(puckData)
	}
	/*	
	traceEvent(settings.logFilter,"generate_puck_readings_json>puckDataJson=${puckDataJson}", settings.trace)
	*/
	def puckEvents = [
			beaconSightingsData: "${puckDataJson.toString()}"
		]
	/*    
	traceEvent(settings.logFilter,"generate_puck_readings_json>puckListEvents to be sent= ${puckListEvents}", settings.trace)
	*/
	generateEvent(puckEvents)

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

				data.auth.access_token = resp.data.access_token
				data.auth.refresh_token = resp.data.refresh_token
				data.auth.expires_in = resp.data.expires_in
				data.auth.token_type = resp.data.token_type
				data.auth.scope = resp?.data?.scope
				def authexptime = new Date((now() + (resp?.data?.expires_in  * 1000))).getTime()
				data.auth.authexptime=authexptime 						                        
				traceEvent(settings.logFilter,"refreshLocalAuthToken>new authexptime = ${authexptime}", settings.trace)
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

	if (data?.auth?.puckId) {
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
	traceEvent(settings.logFilter,"refreshChildTokens>begin new token auth= $auth.access_token, authexptime=$auth.authexptime", settings.trace)
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
	if (!data?.auth?.access_token) {
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
	if (data?.auth?.authexptime > time_check_for_exp) {
		traceEvent(settings.logFilter,"isTokenExpired> not expired", settings.trace, get_LOG_INFO())
		return false
	}
	traceEvent(settings.logFilter,"isTokenExpired>expired", settings.trace, get_LOG_INFO())
	return true
}


// Determine id from settings or initalSetup
private def determine_puck_id(puck_id) {
	def puckId=device.currentValue("puckId")
    
	if ((puck_id != null) && (puck_id != "")) {
		puckId = puck_id
		settings?.puckId=puckId        
	} else if ((settings.puckId != null) && (settings.puckId  != "")) {
		puckId = settings.puckId.trim()
		traceEvent(settings.logFilter,"determine_puck_id> puckId from settings = ${settings.puckId}", settings.trace)
	} else if (data?.auth?.puckId) {
		settings?.puckId=data?.auth?.puckId
		traceEvent(settings.logFilter,"determine_puck_id> puckId from data.auth = ${data?.auth?.puckId}", settings.trace)
	} else if ((puckId != null) && (puckId != "")) {
		settings?.puckId=puckId        
		traceEvent(settings.logFilter,"determine_puck_id> puckId from device = ${puckId}", settings.trace)
	}
	if ((puck_id != "") && (puck_id != puckId) && (puckId)) {
		sendEvent(name: "puckId", displayed: (settings.trace?:false),value: puckId)    
	}
	return puckId
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


// @Called by MyFlairServiceMgr for initial creation of a child Device
void initialSetup(device_client_id,device_private_key,access_token,refresh_token,token_type,token_authexptime,device_structure_id,device_puck_id) {
	def varSettings=[:]
	settings?.trace=true
	settings?.logFilter=5    
	if (settings.trace) {
		log.debug "initialSetup>begin"
		log.debug "initialSetup> device_puck_Id = ${device_puck_id}"
		log.debug "initialSetup> device_client_id = ${device_client_id}"
		log.debug "initialSetup> device_private_key = ${device_private_key}"
		log.debug "initialSetup> token_type = ${token_type}"
		log.debug "initialSetup> token_authexptime = ${token_authexptime}"
		log.debug "initialSetup> device_structure_Id = ${device_structure_id}"
	}	

	settings?.appKey= device_client_id
	settings?.privateKey= device_private_key
	settings?.structureId = device_structure_id
	settings?.puckId = device_puck_id
	varSettings=settings	
	data?.auth=[:]
	data?.auth=varSettings
	data?.auth?.access_token= access_token
	data?.auth?.refresh_token = refresh_token
	data?.auth?.token_type = token_type
	data?.auth?.authexptime= token_authexptime

	sendEvent(name: "puckId", displayed: (settings.trace?:false),value: device_puck_id)    
	sendEvent(name: "structureId", displayed: (settings.trace?: false), value: device_structure_id)
    
    //eeddir
    sendEvent(name: "flairHomeAwayMode", value: "Auto Home")
    sendEvent(name: "flairAwayMode", value: "Off")
    sendEvent(name: "flairSetpointMode", value: "Eveness")
	sendEvent(name: "flairDropRate", value: "n/a")    
	sendEvent(name: "flairPressure", value: "n/a")    
    sendEvent(name: "flairRSSI", value: "n/a")
    sendEvent(name: "flairRoom", value: "n/a")    
    sendEvent(name: "flairZone", value: "n/a")    
    sendEvent(name: "flairStatus", value: "n/a")    

	state?.exceptionCount=0    
	state?.scale = getTemperatureScale()
//  refresh_puck(device_puck_id)
	runIn(1*60, "refresh")
	if (settings.trace) {
		log.debug "initialSetup> settings = ${settings}"
		log.debug "initialSetup>end"
		log.debug "initialSetup> data.auth after refresh = ${data.auth}"
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

private def ISODateFormat(dateString) {
 	SimpleDateFormat ISO8601format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date aDate = ISO8601format.parse(dateString.substring(0,19) + 'Z')
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
	def avg_room_temp,avg_room_desired_temp, avg_room_setpoint, min_room_setpoint=200, max_room_setpoint=0, avg_puck_temp,avg_puck_humidity    
	boolean found_values=false
	Date todayDate = new Date()
	Date startOfPeriod = todayDate - pastDaysCount
	long min_room_timestamp,max_room_timestamp

	def rmSetpointData = device.statesSince("rmSetpoint", startOfPeriod, [max:200])
	def rmTemperatureData = device.statesSince("rmCurrentTemperature", startOfPeriod, [max:200])
	def rmUserDesiredTempData = device.statesSince("rmUserDesiredTemperature", startOfPeriod, [max:200])
	def temperatureData = device.statesSince("temperature", startOfPeriod, [max:200])
	def humidityData = device.statesSince("humidity", startOfPeriod, [max:200])

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
    
	if (temperatureData) {    
		avg_puck_temp= (temperatureData.sum{it.floatValue.toFloat()}/ (temperatureData.size())).toFloat().round(1)
		found_values=true        
	}        
	if (humidityData) {    
		avg_puck_humidity = (humidityData.sum{it.value.toInteger()}/ (humidityData.size())).toFloat().round(0)
		found_values=true        
	} 
	if (!found_values) {
		traceEvent(settings.logFilter,"produceSummaryReport>found no values for report,exiting",settings.trace)
		sendEvent(name: "summaryReport", value: "")
		return        
	}    
	String scale=getTemperatureScale(), unitScale='Farenheit',timePeriod="In the past ${pastDaysCount} days"
	def struct_HomeAwayMode= device.currentValue("stHomeAwayMode")
	def struct_setpointMode= device.currentValue("stSPointMode")   
    
	if (scale=='C') { 
		unitScale='Celsius'    
	}    
	if (pastDaysCount <2) {
		timePeriod="In the past day"    
	}    
	String roomName =device.currentValue("roomName")
	String structureMode =device.currentValue("stMode")
	String summary_report = "At your home, your Flair's structure is currently in ${structureMode} mode" 
	summary_report= summary_report + ",the current Flair Away Mode is ${struct_HomeAwayMode}, and its setpoint mode is currently ${struct_setpointMode}." 
	summary_report=summary_report + "${timePeriod}"    
	if (roomName) {	
		",in the room ${roomName} where the puck ${device.displayName} is located"
	}    
    
	if (avg_room_desired_temp) {
		summary_report= summary_report + ",the room's average desired temperature was ${avg_room_desired_temp} degrees ${unitScale}"
	}
      
	if (avg_room_temp) {
		summary_report= summary_report + ",the average room temperature was ${avg_room_temp.toString()} degrees ${unitScale}"
	}
    
	if (avg_room_setpoint) {
		summary_report= summary_report + ",the room's setpoint was ${avg_room_setpoint.toString()} degrees in average" 
	}
	if (min_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(min_room_timestamp)					    
		summary_report= summary_report + ".The room's minimum setpoint was ${min_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
	if (max_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(max_room_timestamp)					    
		summary_report= summary_report + ",and the room's maximum setpoint was ${max_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
    
	if (avg_puck_temp) {
		summary_report= summary_report + ".The puck's average temp collected was ${avg_puck_temp.toString()} degrees ${unitScale}."
	}
    
	if (avg_puck_humidity) {
		summary_report= summary_report + "And finally, the puck's average relative humidity observed was ${avg_puck_humidity.toString()}%."      
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
	Date startOfWeek = startOfToday -7
	def MIN_DEVIATION_TEMP=(scale=='C'?1:2)   
	def MIN_DEVIATION_HUM=3    
    
	traceEvent(settings.logFilter,"retrieveDataForGraph>today at Midnight in local time= ${todayAtMidnight}",settings.trace)
	def rmTemperatureTable = []
	def temperatureTable = []
	def rmSetpointTable = []
	def humidityTable=[]    
	int maxInd    
	def previousValue=null
    
	def rmSetpointData = device.statesSince("rmSetpoint", startOfWeek, [max:20])
	def rmTemperatureData = device.statesSince("rmCurrentTemperature", startOfWeek, [max:50])
	def temperatureData = device.statesSince("temperature", startOfWeek, [max:200])
	def humidityData = device.statesSince("humidity", startOfWeek, [max:200])

    
	if (humdidityData) {    
		for (int i=maxInd; (i>=0);i--) {
			// filter some values        
			if (i !=maxInd) previousValue = humidityData[i+1]?.value.toInteger()
			if ((i==0) || (i==maxInd) || ((humidityData[i]?.value.toInteger() <= (previousValue - MIN_DEVIATION_HUM) || (humidityData[i]?.value.toInteger() >= (previousValue + MIN_DEVIATION_HUM)) ))) {
				humidityTable.add([humidityData[i].date.getTime(),humidityData[i].value])
			}
		} /* end for */            
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
    

	if (rmSetpointTable == []) { // if temperature has not changed for a week
		def currentRmSetpoint=device.currentValue("rmSetpoint")
		if (currentRmSetpoint) {        
			rmSetpointTable.add([startOfWeek.getTime(),currentRmSetpoint])		        
			rmSetpointTable.add([todayDate.getTime(),currentRmSetpoint])		        
		}    
	} else {
		if (currentRmSetpoint) {        
			def currentRmSetpoint=device.currentValue("rmSetpoint")
		}    
		rmSetpointTable.add([todayDate.getTime(),currentRmSetpoint])		        
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

 	if (humidityTable == []) {  // if humidity has not changed for a week
		def currentHumidity=device.latestValue("humidity")
		if (currentHumidity) {        
			humidityTable.add([startOfWeek.getTime(),currentHumidity.toInteger()])		        
			humidityTable.add([todayDate.getTime(),currentHumidity.toInteger()])		        
		}    
	} else {
		def currentHumidity=device.latestValue("humidity")
		if (currentHumidity) {        
			humidityTable.add([todayDate.getTime(),currentHumidity.toInteger()])		        
		}    
	}    
	state?.rmTemperatureTable = rmTemperatureTable
	state?.rmSetpointTable = rmSetpointTable
	state?.temperatureTable = temperatureTable
	state?.humidityTable = humidityTable
//	log.debug "retrieveDataForGraph>rmSetpointTable (size=${rmSetpointTable.size()}) =${rmSetpointTable}"  
//	log.debug "retrieveDataForGraph>rmTemperatureTable (size=${state?.rmTemperatureTable.size()}) =${state?.rmTemperatureTable}"  
//	log.debug "retrieveDataForGraph>temperatureTable (size=${state?.temperatureTable.size()}) =${state?.temperatureTable}"  
//	log.debug "retrieveDataForGraph>humidityTable (size=${state?.humidityTable.size()}) =${state?.humidityTable}"
	traceEvent(settings.logFilter,"retrieveDataForGraph>rmSetpointTable (size=${rmSetpointTable.size()}) =${rmSetpointTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>rmTemperatureTable (size=${state?.rmTemperatureTable.size()}) =${state?.rmTemperatureTable}",settings.trace, get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>temperatureTable (size=${state?.temperatureTable.size()}) =${state?.temperatureTable}",settings.trace,get_LOG_TRACE())  
	traceEvent(settings.logFilter,"retrieveDataForGraph>humidityTable (size=${state?.humidityTable.size()}) =${state?.humidityTable}",settings.trace,get_LOG_TRACE())  
}

def getStartTime() {
	long startTime = new Date().getTime().toLong()
	if ((state?.temperatureTable) && (state?.temperatureTable.size() > 0)) {
		startTime = state?.temperatureTable.min{it[0]}[0].toLong()
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
			dataTable = state?.temperatureTable
			break
		case 2:
			dataTable = state?.rmTemperatureTable
			break
		case 3:
			dataTable = state?.rmSetpointTable
			break
		case 4:
			dataTable = state?.humidityTable
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


def getGraphHTML() {
  
	String dataRows = "${getDataString(1)}" + "${getDataString(2)}"  + "${getDataString(3)}" + "${getDataString(4)}" 
	Date maxDateTime= new Date()
	Date minDateTime= new Date(getStartTime())
	def minDateStr= "new Date(" +  minDateTime.getTime() + ")"
	def maxDateStr= "new Date(" +  maxDateTime.getTime() + ")"

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
						data.addColumn('number', 'PuckTemp');
						data.addColumn('number', 'RmTemp');
						data.addColumn('number', 'RmSetPnt');
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
								0: {targetAxisIndex: 0, color: '#FF0000'},
								1: {targetAxisIndex: 0, color: '#f1d801'},
								2: {targetAxisIndex: 0, color: '#269bd2'},
								3: {targetAxisIndex: 1, color: '#44b621'}
							},
							vAxes: {
								0: {
									title: 'Temperature',
									format: 'decimal',
									textStyle: {color: '#FF0000'},
									titleTextStyle: {color: '#FF0000'}
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