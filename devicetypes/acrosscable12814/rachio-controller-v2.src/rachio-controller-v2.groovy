/**
 *  Rachio Controller Device Handler V2
 *
 *  Copyright 2020 Dieter Rothhardt (based on (c) 2018 Anthony Santilli)
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
 *	Modified: 2020-11-20
 */

import java.text.SimpleDateFormat

String devVer() { return "2.0.0" }
Integer statusRefresh() { return 60 }

metadata {
    definition (name: "Rachio Controller V2", ocfDeviceType:"oic.d.watervalve", namespace: "acrosscable12814", author: "Dieter Rothhardt", vid: "0ff71f1f-0646-3a4a-941a-727e4119bb59", mnmn: "SmartThingsCommunity") {
        capability "Refresh"
        capability "Switch"
        capability "Actuator"
        capability "Valve"
        capability "Sensor"
        capability "Polling"
        capability "Health Check"
//        capability "acrosscable12814.rachioOperatingMode"
        capability "acrosscable12814.rachioWateringStatus"
        capability "acrosscable12814.rachioHardwareModelDescription"
        capability "acrosscable12814.rachioControlRainDelay"
        capability "acrosscable12814.rachioTriggerRainDelay"
        capability "acrosscable12814.rachioSetManualZoneRuntime"
        capability "acrosscable12814.rachioTriggerManualZone"
        capability "acrosscable12814.rachioNumberOfZones"
        capability "acrosscable12814.rachioControllerStandby"
        
        attribute "hardwareModel", "string"
        attribute "hardwareDesc", "string"
        attribute "activeZoneCnt", "number"
        attribute "controllerOn", "string"

        attribute "rainDelay","number"
        attribute "watering", "string"

        //current_schedule data
        attribute "scheduleType", "string"
        attribute "curZoneRunStatus", "string"
        
        attribute "curZoneName", "string"
        attribute "curZoneNumber", "number"
        attribute "curZoneDuration", "number"
        attribute "curZoneStartDate", "string"
        attribute "curZoneIsCycling", "string"
        attribute "curZoneCycleCount", "number"
        attribute "curZoneWaterTime", "number"
        attribute "rainDelayStr", "string"
        attribute "standbyMode", "string"

        attribute "lastUpdatedDt", "string"

        command "stopWatering"
        command "setRainDelay", ["number"]

        command "doSetRainDelay"
        command "decreaseRainDelay"
        command "increaseRainDelay"
        command "setZoneWaterTime", ["number"]
        command "decZoneWaterTime"
        command "incZoneWaterTime"
        command "runAllZones"
        command "standbyOn"
        command "standbyOff"
        //command "pauseScheduleRun"

        command "open"
        command "close"
        //command "pause"
    }

    tiles (scale: 2){
        multiAttributeTile(name: "valveTile", type: "generic", width: 6, height: 4) {
            tileAttribute("device.watering", key: "PRIMARY_CONTROL" ) {
                attributeState "on", label: 'Watering', action: "close", icon: "st.valves.water.open", backgroundColor: "#00A7E1", nextState: "off"
                attributeState "off", label: 'Off', action: "runAllZones", icon: "st.valves.water.closed", backgroundColor: "#7e7d7d", nextState:"on"
                attributeState "offline", label: 'Offline', icon: "st.valves.water.closed", backgroundColor: "#FE2E2E"
                attributeState "standby", label: 'Standby Mode', icon: "st.valves.water.closed", backgroundColor: "#FFAE42"
            }
            tileAttribute("device.curZoneRunStatus", key: "SECONDARY_CONTROL") {
                attributeState("default", label:'${currentValue}')
            }
        }
        standardTile("hardwareModel", "device.hardwareModel", inactiveLabel: false, width: 2, height: 2, decoration: "flat") {
            state "default", icon: ""
            state "8ZoneV1", icon: "https://s3-us-west-2.amazonaws.com/rachio-media/smartthings/8zone_v1.png"
            state "16ZoneV1", icon: "https://s3-us-west-2.amazonaws.com/rachio-media/smartthings/8zone_v1.png"
            state "8ZoneV2", icon: "https://raw.githubusercontent.com/tonesto7/rachio-manager/master/images/rachio_gen2.png"
            state "16ZoneV2", icon: "https://raw.githubusercontent.com/tonesto7/rachio-manager/master/images/rachio_gen2.png"
            state "8ZoneV3", icon: "https://raw.githubusercontent.com/tonesto7/rachio-manager/master/images/rachio_gen3.png"
            state "16ZoneV3", icon: "https://raw.githubusercontent.com/tonesto7/rachio-manager/master/images/rachio_gen3.png"
        }
        valueTile("hardwareDesc", "device.hardwareDesc", inactiveLabel: false, width: 4, height: 1, decoration: "flat") {
            state "default", label: 'Model:\n${currentValue}'
        }
        valueTile("activeZoneCnt", "device.activeZoneCnt", inactiveLabel: true, width: 4, height: 1, decoration: "flat") {
            state "default", label: 'Active Zones:\n${currentValue}'
        }
        valueTile("controllerOn", "device.controllerOn", inactiveLabel: true, width: 2, height: 1, decoration: "flat") {
            state "default", label: 'Online Status:\n${currentValue}'
        }
        valueTile("controllerRunStatus", "device.controllerRunStatus", inactiveLabel: true, width: 4, height: 2, decoration: "flat") {
            state "default", label: '${currentValue}'
        }
        valueTile("blank", "device.blank", width: 2, height: 1, decoration: "flat") {
            state("default", label: '')
        }
        standardTile("switch", "device.switch", inactiveLabel: false, decoration: "flat") {
            state "off", icon: "st.switch.off"
            state "on", action: "stopWatering", icon: "st.switch.on"
        }
        valueTile("pauseScheduleRun", "device.scheduleTypeBtnDesc", inactiveLabel: false, decoration: "flat", width: 2, height: 1) {
            state "default", label: '${currentValue}', action: "pauseScheduleRun"
        }

        // Rain Delay Control
        standardTile("leftButtonControl", "device.rainDelay", inactiveLabel: false, decoration: "flat") {
            state "default", action:"decreaseRainDelay", icon:"st.thermostat.thermostat-left"
        }
        valueTile("rainDelay", "device.rainDelay", width: 2, height: 1, decoration: "flat") {
            state "default", label:'Rain Delay:\n${currentValue} Days'
        }
        standardTile("rightButtonControl", "device.rainDelay", inactiveLabel: false, decoration: "flat") {
            state "default", action:"increaseRainDelay", icon:"st.thermostat.thermostat-right"
        }
        valueTile("applyRainDelay", "device.rainDelayStr", width: 2, height: 1, inactiveLabel: false, decoration: "flat") {
            state "default", label: '${currentValue}', action:'doSetRainDelay'
        }

        //zone Water time control
        valueTile("lastWateredDesc", "device.lastWateredDesc", width: 4, height: 1, decoration: "flat", wordWrap: true) {
            state("default", label: 'Last Watered:\n${currentValue}')
        }
        standardTile("leftZoneTimeButton", "device.curZoneWaterTime", inactiveLabel: false, decoration: "flat") {
            state "default", action:"decZoneWaterTime", icon:"st.thermostat.thermostat-left"
        }
        valueTile("curZoneWaterTime", "device.curZoneWaterTime", width: 2, height: 1, decoration: "flat") {
            state "default", label:'Manual Zone Time:\n${currentValue} Minutes'
        }
        standardTile("rightZoneTimeButton", "device.curZoneWaterTime", inactiveLabel: false, decoration: "flat") {
            state "default", action:"incZoneWaterTime", icon:"st.thermostat.thermostat-right"
        }
        valueTile("runAllZonesTile", "device.curZoneWaterTime", inactiveLabel: false, width: 2 , height: 1, decoration: "flat") {
            state("default", label: 'Run All Zones\n${currentValue} Minutes', action:'runAllZones')
        }
        standardTile("standbyMode", "device.standbyMode", decoration: "flat", wordWrap: true, width: 2, height: 2) {
            state "on", label:'Turn Standby Off', action:"standbyOff", nextState: "false", icon: "http://cdn.device-icons.smartthings.com/sonos/play-icon@2x.png"
            state "off", label:'Turn Standby On', action:"standbyOn", nextState: "true", icon: "http://cdn.device-icons.smartthings.com/sonos/pause-icon@2x.png"
        }
        standardTile("refresh", "device.power", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
        }

    }
    main "valveTile"
    details(["valveTile", "hardwareModel", "hardwareDesc", "activeZoneCnt", "curZoneIsCyclingTile", "leftButtonControl", "rainDelay", "rightButtonControl", "applyRainDelay",
            "leftZoneTimeButton", "curZoneWaterTime", "rightZoneTimeButton", "runAllZonesTile", "lastUpdatedDt", "standbyMode", "refresh"])

}


def setRachioOperatingMode(value) {
	log.debug("setRachioOperatingMode: ${value}")
    def inStandby = device?.currentState("standbyMode")?.value.toString() == "on" ? true : false
    if(device?.currentState("watering")?.value == "offline") {
        sendEvent(name: "rachioOperatingMode", value: "Idle")
    } else if (!inStandby) {
        if (value == "Watering") {
            runAllZones()
        } else {
            close()
        }
    } else { 
        sendEvent(name: "rachioOperatingMode", value: "Idle")
	}
}

def setRachioSetRainDelay(value) {
	log.debug("setRachioSetRainDelay: ${value}")
    updateRainDelay(value)
}

def setTriggerRainDelay(value) {
	log.debug("setTriggerRainDelay: ${value}")
    sendEvent(name: "triggerRainDelay", value: "on")
    doSetRainDelay()
}

def setRachioSetManualZoneRuntime(value) {
	log.debug("setRachioSetManualZoneRuntime: ${value}")
    setZoneWaterTime(value)
}

def setRachioTriggerManualZone(value) {
	log.debug("setRachioTriggerManualZone: ${value}")
    sendEvent(name: "rachioTriggerManualZone", value: "on")
    def curState = device?.currentState("curZoneWaterTime")?.value.toString()
    sendEvent(name: "rachioTriggerManualZone", value: "Run All Zones ${curState} Minute(s)")
    runAllZones()
}

def setRachioStandby(value) {
	log.debug("setRachioStandby: ${value}")
    if(value == "play") {
    	standbyOff()
    } else {
    	standbyOn()
    }
}


def getAppImg(imgName)	{ return "https://raw.githubusercontent.com/tonesto7/rachio-manager/master/images/$imgName" }

// parse events into attributes
def parse(String description) {
    log.debug "Parsing '${description}'"
}

def initialize() {	
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online", displayed: false, isStateChange: true)
    sendEvent(name: "DeviceWatch-Enroll", value: groovy.json.JsonOutput.toJson(["protocol":"cloud", "scheme":"untracked"]), displayed: false)
    
    sendEvent(name: "valve", value: "closed")
    //sendEvent(name: "rachioOperatingMode", value: "Idle")
    sendEvent(name: "rachioWateringStatus", value: "Idle")
    sendEvent(name: "rachioHardwareModelDescription", value: "n/a")
	sendEvent(name: "rachioNumberOfZones", value: "n/a")
    sendEvent(name: "rachioSetRainDelay", value: "0", unit: "day(s)")
	sendEvent(name: "triggerRainDelay", value: "No Rain Delay")
	sendEvent(name: "rachioSetManualZoneRuntime", value: "0", unit: "min")
	sendEvent(name: "rachioTriggerManualZone", value: "No Manual Runtime")
    sendEvent(name: "rachioStandby", value: "Active")
    verifyDataAttr()
}

def verifyDataAttr() {
    updateDataValue("HealthEnrolled", "true")
    updateDataValue("manufacturer", "Rachio")
    def gen = state?.deviceId ? parent?.getDevGeneration(state?.deviceId) : null
    updateDataValue("model", "${device?.name}${gen ? " ($gen)" : ""}")
}

void installed() {
    initialize()
    state.isInstalled = true
}

void updated() {
    initialize()
}

def generateEvent(Map results) {
    if(!state?.swVersion || state?.swVersion != devVer()) {
        initialize()
        state.swVersion = devVer()
    }
    log.warn "---------------START OF API RESULTS DATA----------------"
    if(results) {
        log.debug results
        state?.deviceId = device?.deviceNetworkId.toString()
        state?.pauseInStandby = (results?.pauseInStandby == true)
        hardwareModelEvent(results?.data?.model)
        activeZoneCntEvent(results?.data?.zones)
        controllerOnEvent(results?.data?.on)
        def isOnline = results?.status == "ONLINE" ? true : false
        state?.isOnline = isOnline
        if(!isOnline) {
            markOffLine()
        } else {
            state?.inStandby = results?.standby
            if(isStateChange(device, "standbyMode", results?.standby.toString())) {
                sendEvent(name: 'standbyMode', value: (results?.standby?.toString() == "true" ? "on": "off"), displayed: true, isStateChange: true)
                //eeddir
                sendEvent(name: "rachioStandby", value: (results?.standby?.toString() == "true" ? "Standby" : "Operating"))
            }
            if(results?.standby == true && results?.pauseInStandby == true) {
                markStandby()
            } else { isWateringEvent(results?.schedData?.status, results?.schedData?.zoneId) }
        }
        if(!device?.currentState("curZoneWaterTime")?.value) { setZoneWaterTime(parent?.settings?.defaultZoneTime.toInteger()) }
        scheduleDataEvent(results?.schedData, results?.data.zones, results?.rainDelay)
        rainDelayValEvent(results?.rainDelay)
        if(isOnline) { lastUpdatedEvent() }
    }
    return "Controller"
}

def getDurationDesc(long secondsCnt) {
    int seconds = secondsCnt %60
    secondsCnt -= seconds
    long minutesCnt = secondsCnt / 60
    long minutes = minutesCnt % 60
    minutesCnt -= minutes
    long hoursCnt = minutesCnt / 60
    return "${minutes} min ${(seconds >= 0 && seconds < 10) ? "0${seconds}" : "${seconds}"} sec"
}

def getDurationMinDesc(long secondsCnt) {
    int seconds = secondsCnt %60
    secondsCnt -= seconds
    long minutesCnt = secondsCnt / 60
    long minutes = minutesCnt % 60
    minutesCnt -= minutes
    long hoursCnt = minutesCnt / 60
    return "${minutes}"
}

def lastUpdatedEvent() {
    def lastDt = formatDt(new Date())
    def lastUpd = device?.currentState("lastUpdatedDt")?.stringValue
    state?.lastUpdatedDt = lastDt?.toString()
    if(isStateChange(device, "lastUpdatedDt", lastDt.toString())) {
        // log.info "${device?.displayName} Status: (${state?.isOnline ? "Online and ${state?.inStandby ? "in Standby Mode" : "Ready"}" : "OFFLINE"}) - Last Updated: (${lastDt})"
        sendEvent(name: 'lastUpdatedDt', value: lastDt?.toString(), displayed: false, isStateChange: true)
    }
}

def markOffLine() {
    if(isStateChange(device, "watering", "offline") || isStateChange(device, "curZoneRunStatus", "Device is Offline")) {
        log.debug("UPDATED: Watering is set to (Offline)")
        sendEvent(name: 'watering', value: "offline", displayed: true, isStateChange: true)
        sendEvent(name: 'valve', value: "closed", displayed: false, isStateChange: true)
        sendEvent(name: 'switch', value: "off", displayed: false, isStateChange: true)
        sendEvent(name: "rachioWateringStatus", value: "Offline")
        
    }
	if(isStateChange(device, "curZoneRunStatus", "Device is Offline")) { //eeddir was 'in'
    
		sendEvent(name: 'curZoneRunStatus', value: "Device is Offline", displayed: false, isStateChange: true)
	}
}

def markStandby() {
    if(isStateChange(device, "watering", "standby") || isStateChange(device, "curZoneRunStatus", "Device in Standby Mode")) {
        log.debug("UPDATED: Watering set to (Standby Mode)")
        sendEvent(name: 'watering', value: "standby", displayed: true, isStateChange: true)
        sendEvent(name: 'valve', value: "closed", displayed: false, isStateChange: true)
        sendEvent(name: 'switch', value: "off", displayed: false, isStateChange: true)
        sendEvent(name: "rachioWateringStatus", value: "Standby")
    }
	if(isStateChange(device, "curZoneRunStatus", "Device in Standby Mode")) {
        sendEvent(name: 'curZoneRunStatus', value: "Device in Standby Mode", displayed: false, isStateChange: true)
    }
}

def isWateringEvent(status, zoneId) {
    log.trace "isWateringEvent..."
    def curState = device?.currentState("watering")?.value.toString()
    def isOn = (status == "PROCESSING") ? true : false
    def newState = isOn ? "on" : "off"
    def operatingState = isOn ? "Watering" : "Idle"
    def valveState = isOn ? "open" : "closed"
    log.trace "isWateringEvent... ${operatingState}"
    parent?.setWateringDeviceState(device?.deviceNetworkId, isOn)
    if(isStateChange(device, "watering", newState.toString())) {
        log.debug("UPDATED: Watering (${newState}) | Previous: (${curState})")
        sendEvent(name: 'watering', value: newState, displayed: true, isStateChange: true)
        sendEvent(name: 'valve', value: valveState, displayed: false, isStateChange: true)
        sendEvent(name: 'switch', value: newState, displayed: false, isStateChange: true)
        sendEvent(name: "rachioWateringStatus", value: operatingState)
        //sendEvent(name: "rachioOperatingMode", value: operatingState)
		if(curState != null) { parent?.handleWateringSched(device?.deviceNetworkId, isOn) }
    }
}

def hardwareModelEvent(val) {
    def curModel = device?.currentState("hardwareModel")?.value.toString()
    def curDesc = device?.currentState("hardwareDesc")?.value.toString()
    def newModel = null
    def newDesc = null
    switch(val) {
        case "GENERATION1_8ZONE":
            newModel = "8ZoneV1"
            newDesc = "8-Zone (Gen 1)"
            break
        case "GENERATION1_16ZONE":
            newModel = "16ZoneV1"
            newDesc = "16-Zone (Gen 1)"
            break
        case "GENERATION2_8ZONE":
            newModel = "8ZoneV2"
            newDesc = "8-Zone (Gen 2)"
            break
        case "GENERATION2_16ZONE":
            newModel = "16ZoneV2"
            newDesc = "16-Zone (Gen 2)"
            break
        case "GENERATION3_8ZONE":
            newModel = "8ZoneV3"
            newDesc = "8-Zone (Gen 3)"
            break
        case "GENERATION3_16ZONE":
            newModel = "16ZoneV3"
            newDesc = "16-Zone (Gen 3)"
            break
    }
    if(isStateChange(device, "hardwareModel", newModel.toString())) {
        log.debug "UPDATED: Controller Model (${newModel}) | Previous: (${curModel})"
        sendEvent(name: 'hardwareModel', value: newModel, displayed: true, isStateChange: true)
    }
    if(isStateChange(device, "hardwareDesc", newDesc.toString())) {
        log.debug "UPDATED: Controller Description (${newDesc}) | Previous: (${curDesc})"
        sendEvent(name: 'hardwareDesc', value: newDesc.toString(), displayed: true, isStateChange: true)
        sendEvent(name: "rachioHardwareModelDescription", value: newDesc.toString())
    }
}

def activeZoneCntEvent(zData) {
    def curState = device?.currentValue("activeZoneCnt")?.toString()
    def zoneCnt = 0
    if (zData) {
        zData.each { z -> if(z?.enabled.toString() == "true") { zoneCnt = zoneCnt+1 } }
    }
    if(isStateChange(device, "activeZoneCnt", zoneCnt.toString())) {
        log.debug "UPDATED: Active Zone Count (${zoneCnt}) | Previous: (${curState})"
        sendEvent(name: 'activeZoneCnt', value: zoneCnt?.toInteger(), displayed: true, isStateChange: true)
        sendEvent(name: "rachioNumberOfZones", value: "${zoneCnt?.toInteger()} Zones", unit: "Zones")
    }
}

def controllerOnEvent(val) {
    def curState = device?.currentState("controllerOn")?.value
    def newState = val?.toString()
    if(isStateChange(device, "controllerOn", newState.toString())) {
        log.debug "UPDATED: Controller On Status (${newState}) | Previous: (${curState})"
        sendEvent(name: 'controllerOn', value: newState, displayed: true, isStateChange: true)
        //eeddir
    }
}

def lastWateredDateEvent(val, dur) {
    def newState = "${epochToDt(val)}"
    def newDesc = "${epochToDt(val)}\nDuration: ${getDurationDesc(dur?.toLong())}"
    def curState = device?.currentState("lastWateredDt")?.value
    if(isStateChange(device, "lastWateredDt", newState.toString())) {
        log.debug "UPDATED: Last Watered Date (${newState}) | Previous: (${curState})"
        sendEvent(name: 'lastWateredDt', value: newState, displayed: true, isStateChange: true)
        sendEvent(name: 'lastWateredDesc', value: newDesc, displayed: false, isStateChange: true)
        //eeddir
    }
}

def rainDelayValEvent(val) {
    def curState = device?.currentState("rainDelay")?.value.toString()
    def newState = val ? val : 0
    if(isStateChange(device, "rainDelay", newState.toString())) {
        log.debug("UPDATED: Rain Delay Value (${newState}) | Previous: (${curState})")
        sendEvent(name:'rainDelay', value: newState, displayed: true)        
        sendEvent(name: "rachioSetRainDelay", value: newState, unit: "min")
        setRainDelayString(newState)
    }
}

def setZoneWaterTime(timeVal) {
    def curState = device?.currentState("curZoneWaterTime")?.value.toString()
    def newVal = timeVal ? timeVal.toInteger() : parent?.settings?.defaultZoneTime.toInteger()
    if(isStateChange(device, "curZoneWaterTime", newVal.toString())) {
        log.debug("UPDATED: Manual Zone Water Time (${newVal}) | Previous: (${curState})")
        sendEvent(name: 'curZoneWaterTime', value: newVal, displayed: true)
        sendEvent(name: "rachioSetManualZoneRuntime", value: newVal, unit: "min")
        sendEvent(name: "rachioTriggerManualZone", value: "Run All Zones ${newVal} Minute(s)")
    }
}

def scheduleDataEvent(sData, zData, rainDelay) {
    log.trace "scheduleDataEvent($data)..."
    state?.schedData = sData
    state?.zoneData = zData
    state?.rainData = rainDelay
    def curSchedType = !sData?.type ? "Off" : sData?.type?.toString().capitalize()
    //def curSchedTypeBtnDesc = (!curSchedType || curSchedType in ["off", "manual"]) ? "Pause Disabled" : "Pause Schedule"
    state.curSchedType = curSchedType
    state?.curScheduleId = !sData?.scheduleId ? null : sData?.scheduleId
    state?.curScheduleRuleId = !sData?.scheduleRuleId ? null : sData?.scheduleRuleId
    def zoneData = sData && zData ? getZoneData(zData, sData?.zoneId) : null
    def zoneId = !zoneData ? null : sData?.zoneId
    def zoneName = !zoneData ? null : zoneData?.name
    def zoneNum = !zoneData ? null : zoneData?.zoneNumber

    def zoneStartDate = sData?.zoneStartDate ? sData?.zoneStartDate : null
    def zoneDuration = sData?.zoneDuration ? sData?.zoneDuration : null
    
    def timeDiff = sData?.zoneStartDate ? GetTimeValDiff(sData?.zoneStartDate.toLong()) : 0
    def elapsedDuration = sData?.zoneStartDate ? getDurationMinDesc(Math.round(timeDiff)) : 0
    def wateringDuration = zoneDuration ? getDurationMinDesc(zoneDuration) : 0
    def zoneRunStatus = ((!zoneStartDate && !zoneDuration) || !zoneId ) ? "Status: Idle" : "${zoneName}: (${elapsedDuration} of ${wateringDuration} Minutes)"

    def zoneCycleCount = !sData?.totalCycleCount ? 0 : sData?.totalCycleCount
    def zoneIsCycling =  !sData?.cycling ? false : sData?.cycling
    def wateringVal = device?.currentState("watering")?.value
    if(isStateChange(device, "scheduleType", curSchedType?.toString().capitalize())) {
        log.info("UPDATED: ScheduleType (${curSchedType})")
        sendEvent(name: 'scheduleType', value: curSchedType?.toString().capitalize(), displayed: true, isStateChange: true)
        //eeddir
    }
    if(!state?.inStandby && wateringVal != "offline" && isStateChange(device, "curZoneRunStatus", zoneRunStatus?.toString())) {
        log.info("UPDATED: ZoneRunStatus (${zoneRunStatus})")
        log.info("UPDATED: rachioWateringStatus (${zoneRunStatus?.toString()})")
        sendEvent(name: 'curZoneRunStatus', value: zoneRunStatus?.toString(), displayed: false, isStateChange: true)
        //sendEvent(name: 'rachioWateringStatus', value: zoneRunStatus?.toString())
        //eeddir2
    }
    if(isStateChange(device, "curZoneDuration", zoneDuration?.toString())) {
        log.info("UPDATED: Active Zone Duration (${zoneDuration})")
        sendEvent(name: 'curZoneDuration', value: zoneDuration?.toString(), displayed: true, isStateChange: true)
        //eeddir sendEvent(name: 'rachioWateringStatus', value: zoneDuration?.toString())
    }
    if(isStateChange(device, "curZoneName", zoneName?.toString())) {
        log.info("UPDATED: Current Zone Name (${zoneName})")
        sendEvent(name: 'curZoneName', value: zoneName?.toString(), displayed: true, isStateChange: true)
        //eeddir sendEvent(name: 'rachioWateringStatus', value: zoneName?.toString())
    }
    if(isStateChange(device, "curZoneNumber", zoneNum?.toString())) {
        log.info("UPDATED: Active Zone Number (${zoneNum})")
        sendEvent(name: 'curZoneNumber', value: zoneNum, displayed: true, isStateChange: true)
        //eeddir sendEvent(name: 'rachioWateringStatus', value: zoneNum)
    }
    if(isStateChange(device, "curZoneCycleCount", zoneCycleCount?.toString())) {
        log.info("UPDATED: Zone Cycle Count (${zoneCycleCount})")
        sendEvent(name: 'curZoneCycleCount', value: zoneCycleCount, displayed: true, isStateChange: true)
        //eeddir sendEvent(name: 'rachioWateringStatus', value: zoneCycleCount)
    }
    if(isStateChange(device, "curZoneIsCycling", zoneIsCycling?.toString().capitalize())) {
        sendEvent(name: 'curZoneIsCycling', value: zoneIsCycling?.toString().capitalize(), displayed: true, isStateChange: true)
        //eeddir sendEvent(name: 'rachioWateringStatus', value: zoneIsCycling?.toString().capitalize())
    }
    if(isStateChange(device, "curZoneStartDate", (zoneStartDate ? epochToDt(zoneStartDate).toString() : "Not Active"))) {
        log.info("UPDATED: Zone StartDate (${(zoneStartDate ? epochToDt(zoneStartDate).toString() : "Not Active")})")
        sendEvent(name: 'curZoneStartDate', value: (zoneStartDate ? epochToDt(zoneStartDate).toString() : "Not Active"), displayed: true, isStateChange: true)
        //eeddir sendEvent(name: 'rachioWateringStatus', value: (zoneStartDate ? epochToDt(zoneStartDate).toString() : "Not Active"))
    }
}

def getZoneData(zData, zId) {
    if (zData && zId) {
        return zData.find { it?.id == zId }
    }
}

def incZoneWaterTime() {
    log.debug("Decrease Zone Runtime");
    def value = device.latestValue('curZoneWaterTime')
    setZoneWaterTime(value + 1)
}

def decZoneWaterTime() {
    log.debug("Increase Zone Runtime");
    def value = device.latestValue('curZoneWaterTime')
    setZoneWaterTime(value - 1)
}

def setRainDelayString( rainDelay) {
    def rainDelayStr = "No Rain Delay";
    if( rainDelay > 0) {
        rainDelayStr = "Rain Delayed";
    }
    
    log.debug("setRainDelayString: ${rainDelayStr}")
    
    sendEvent( name: "rainDelayStr", value: rainDelayStr, isStateChange: true)
    sendEvent(name: "triggerRainDelay", value: rainDelayStr)
}

def doSetRainDelay() {
    def value = device.latestValue('rainDelay')
    log.debug("Set Rain Delay ${value}")
    def res = parent?.setRainDelay(this, state?.deviceId, value);
    log.debug("result: ${res}")
    if( !res) {
        markOffLine()
    }
    setRainDelayString(value)
    //parent?.pollChildren()
}

def updateRainDelay(value) {
    sendEvent( name: "rainDelayStr", value: "Set New Rain Delay", isStateChange: true)
    sendEvent(name: "triggerRainDelay", value: "Set New Rain Delay")
    log.debug("Update ${value} ")
    if( value > 7) {
        value = 7;
    } else if ( value < 0) {
        value = 0
    }
    sendEvent(name: 'rainDelay', value: value, displayed: true)
    sendEvent(name: "rachioSetRainDelay", value: value, unit: "days")
}

def increaseRainDelay() {
    log.debug("Increase Rain Delay");
    def value = device.latestValue('rainDelay')
    updateRainDelay(value + 1)
}

def decreaseRainDelay() {
    log.debug("Decrease Rain Delay");
    def value = device.latestValue('rainDelay')
    updateRainDelay(value - 1)
}

def refresh() {
    log.trace "refresh..."
    poll()
}

void poll() {
    log.info("Requested Parent Poll...");
    parent?.poll(this)
}

def isCmdOk2Run() {
    log.trace "isCmdOk2Run..."
    if(state?.isOnline == false) {
        log.warn "Skipping the request... Because the zone is unable to send commands while it's in an Offline State."
        return false
    }
    if(state?.pauseInStandby == true && state?.inStandby == true) {
        log.warn "Skipping the request... Because the controller is unable to send commands while it is in standby mode!!!"
        return false
    } else { return true }
}

def runAllZones() {
    log.trace "runAllZones..."
    if(!isCmdOk2Run()) { return }
    def waterTime = device?.latestValue('curZoneWaterTime')
    log.debug("Sending Run All Zones for (${waterTime} Minutes)")
    def res = parent?.runAllZones(this, state?.deviceId, waterTime)
    if (!res) {
        markOffLine()
    }
}

def pauseScheduleRun() {
    log.trace "pauseScheduleRun... NOT AVAILABLE YET!!!"
    if(state?.curSchedType == "automatic") {
        def res = parent?.pauseScheduleRun(this)
        //if(res) { log.info "Successfully Paused Scheduled Run..." }
    }
}

def standbyOn() {
    log.trace "standbyOn..."
    def inStandby = device?.currentState("standbyMode")?.value.toString() == "on" ? true : false
    if(device?.currentState("watering")?.value == "offline") {
        log.info "Device is currently Offline... Ignoring..."
        sendEvent(name: "rachioStandby", value: "Standby")
    } else if (!inStandby) {
        if(parent?.standbyOn(this, state?.deviceId)) {
            sendEvent(name: 'standbyMode', value: "on", displayed: true, isStateChange: true)
            sendEvent(name: "rachioStandby", value: "Standby")
        }
    } else { 
    	log.info "Device is Already in Standby... Ignoring..."
        sendEvent(name: "rachioStandby", value: "Standby")
	}
}

def standbyOff() {
    log.trace "standbyOff..."
    def inStandby = device?.currentState("standbyMode")?.value.toString() == "on" ? true : false
    if(device?.currentState("watering")?.value == "offline") {
        log.info "Device is currently Offline... Ignoring..."
        sendEvent(name: "rachioStandby", value: "Standby")
    } else if (inStandby) {
        if(parent?.standbyOff(this, state?.deviceId)) {
            sendEvent(name: 'standbyMode', value: "off", displayed: true, isStateChange: true)
            sendEvent(name: "rachioStandby", value: "play")
            sendEvent(name: "rachioStandby", value: "Operating")
        }
    } else { 
    	log.info "Device is Already out of Standby... Ignoring..."
        sendEvent(name: "rachioStandby", value: "play")
        //sendEvent(name: "rachioStandby", value: "Operating")
	}
}

def on() {
    log.trace "on..."
    if(!isCmdOk2Run()) { return }
    def isOn = device?.currentState("switch")?.value.toString() == "on" ? true : false
    if (!isOn) { open() }
    else { log.info "Switch is Already ON... Ignoring..." }
}

def off() {
    log.trace "off..."
    //if(!isCmdOk2Run()) { return }
    def isOff = device?.currentState("switch")?.value.toString() == "off" ? true : false
    if (!isOff) { close() }
    else { log.info "Switch is Already OFF... Ignoring..." }
}

def open() {
    log.trace "open()..."
    sendEvent(name: "valve", value: device?.currentState("valve")?.value.toString())

	def inStandby = device?.currentState("standbyMode")?.value.toString() == "on" ? true : false
    if(device?.currentState("watering")?.value == "offline") {
        sendEvent(name: "valve", value: "closed")
    } else if (!inStandby) {
		runAllZones()
    } else { 
        sendEvent(name: "valve", value: "closed")
	}
    
}

def close() {
    log.trace "close()..."
    //if(!isCmdOk2Run()) { return }
    def isClosed = device?.currentState("valve")?.value.toString() == "closed" ? true : false
    sendEvent(name:'valve', value: "closed")
    if (!isClosed) {	    
        def res = parent?.off(this, state?.deviceId)
        if (res) {
            sendEvent(name:'watering', value: "off", displayed: true, isStateChange: true)
			sendEvent(name:'switch', value: "off", displayed: false, isStateChange: true)
            sendEvent(name:'rachioWateringStatus', value: "Idle")
            
        } else {
            log.trace "close(). marking offline"
            markOffLine();
        }
    }
    else { 
        log.info "Close command Ignored... The Valve is Already Closed" 
    }
}

// To be used directly by smart apps
def stopWatering() {
    log.trace "stopWatering"
    close()
}

def setRainDelay(rainDelay) {
    sendEvent(name:"rainDelay", "value": value)
    sendEvent(name:"rachioSetRainDelay", value: value, unit: "min")
    def res = parent?.setRainDelay(this, value);    
    //if (res) { parent?.pollChildren() }
}

def getDtNow() {
	def now = new Date()
	return formatDt(now, false)
}

def epochToDt(val) {
    return formatDt(new Date(val))
}

def formatDt(dt, mdy = true) {
	log.trace "formatDt($dt, $mdy)..."
	def formatVal = mdy ? "MMM d, yyyy - h:mm:ss a" : "E MMM dd HH:mm:ss z yyyy"
	def tf = new SimpleDateFormat(formatVal)
	if(location?.timeZone) { tf.setTimeZone(location?.timeZone) }
	return tf.format(dt)
}

//Returns time differences is seconds
def GetTimeValDiff(timeVal) {
    try {
        def start = new Date(timeVal).getTime()
        def now = new Date().getTime()
        def diff = (int) (long) (now - start) / 1000
        //log.debug "diff: $diff"
        return diff
    }
    catch (ex) {
        log.error "GetTimeValDiff Exception: ${ex}"
        return 1000
    }
}

def getTimeDiffSeconds(strtDate, stpDate=null) {
	if((strtDate && !stpDate) || (strtDate && stpDate)) {
		def now = new Date()
		def stopVal = stpDate ? stpDate.toString() : formatDt(now, false)
		def start = Date.parse("E MMM dd HH:mm:ss z yyyy", strtDate).getTime()
		def stop = Date.parse("E MMM dd HH:mm:ss z yyyy", stopVal).getTime()
		def diff = (int) (long) (stop - start) / 1000
		return diff
	} else { return null }
}