/**
 *  DSC Away Panel
 *
 *  Author: Dieter Rothhardt
 *  Orig Author: Ralph Torchia
 *  Original Code By: Jordan <jordan@xeron.cc>, Rob Fisher <robfish@att.net>, Carlos Santiago <carloss66@gmail.com>, JTT <aesystems@gmail.com>
 *  Date: 2020-20-18
 */

metadata {
  definition (
    name: "DSC Panel",
    author: "Dieter Rothhardt",
    namespace: 'acrosscable12814',
    ocfDeviceType: "oic.d.securitypanel",
    mnmn: "SmartThingsCommunity",
    vid: "283e2e45-93ef-3b07-bc54-b9dbd46d29de",
    cstHandler: true
  )
  
  {
    capability "Refresh"
    capability "acrosscable12814.dscSecuritySystemMode"
    capability "acrosscable12814.dscZoneBypass"
    capability "acrosscable12814.bypassLed"
    capability "acrosscable12814.readyLed"
    capability "acrosscable12814.armedLed"
    capability "acrosscable12814.memoryLed"
    capability "acrosscable12814.programLed"
    capability "acrosscable12814.fireLed"
    capability "acrosscable12814.dscBacklightLed"
    capability "acrosscable12814.troubleLed"
    capability "acrosscable12814.troubleStatus"
    capability "Tamper Alert"
    capability "acrosscable12814.dscInstant"
    capability "acrosscable12814.dscNight"
    capability "acrosscable12814.dscSensorReset"
    capability "acrosscable12814.dscLockAlarmKeys"
    capability "acrosscable12814.dscAlarmKeys"
    capability "acrosscable12814.chime"
    capability "Switch"
    
	attribute "status", "string"
	attribute "trouble", "string"
	attribute "chime", "string"
	attribute "ledready", "string"
	attribute "ledarmed", "string"
	attribute "ledmemory", "string"
	attribute "ledbypass", "string"
	attribute "ledtrouble", "string"
	attribute "ledprogram", "string"
	attribute "ledfire", "string"
	attribute "ledbacklight", "string"
	command "away"
	command "autobypass"
	command "bypassoff"
	command "disarm"
	command "instant"
	command "night"
	command "nokey"
	command "partition"
	command "key"
	command "keyfire"
	command "keyaux"
	command "keypanic"
	command "reset"
	command "stay"
	command "togglechime"    
  }

  tiles {}

}

def partition(String evt, String partition, Map parameters) {
  // evt will be a valid event for the panel (ready, notready, armed, etc)
  // partition will be a partition number, for most users this will always be 1

  log.debug "Partition: ${evt} for partition: ${partition}"
  log.debug "Parameters: ${parameters}"

  def onList = ['alarm','away','entrydelay','exitdelay','instantaway']

  def chimeList = ['chime','nochime']

  def troubleMap = [
    'trouble':"detected",
    'restore':"clear"
  ]

  def troubleStatusMap = [
    'trouble':"Trouble",
    'restore':"No Trouble"
  ]

  def ledNameMap = [
    'bypass':"dscLedBypass",
    'ready':"dscLedReady",
    'armed':"dscLedArmed",
    'memory':"dscLedMemory",
    'program':"dscLedProgram",
    'fire':"dscLedFire",
    'backlight':"dscLedBacklight",
    'trouble':"dscLedTrouble"
  ]
  
  def ledStatusMap = [
    'on':"On",
    'off':"-"
  ]

  def ledFlashingStatusMap = [
    'on':"Flashing",
    'off':"-"
  ]

  def altState = ""
  altState=getPrettyName().get(evt)
  def switchStatus = device.currentState("switch").value
  
  if (onList.contains(evt)) {
    if (switchStatus == "off") { sendEvent (name: "switch", value: "on") }
  } else if (!(chimeList.contains(evt) || troubleMap[evt] || evt.startsWith('led') || evt.startsWith('key'))) {
    sendEvent (name: "switch", value: "off")
  }

  if (troubleMap[evt]) {
    def troubleState = troubleMap."${evt}"
    def troubleStatusState = troubleStatusMap."${evt}"
    // Send trouble event
    sendEvent (name: "trouble", value: "${troubleState}") //attribute
    sendEvent (name: "troubleStatus", value: "${troubleStatusState}")

  } else if (evt.startsWith('led')) {
    for (p in parameters) {
      def ledStatus = (evt.startsWith('ledflash')) ? ledFlashingStatusMap."${p.value}" : ledStatusMap."${p.value}"
      def ledName = ledNameMap."${p.key}"

	  sendEvent (name: "led${p.key}", value: "${ledStatus}")
      sendEvent (name: "${ledName}", value: "${ledStatus}")
      
      if(p.key == "bypass") {
      	if(p.value == "on") {
	      	state.bypassStatus = "Bypass Active"
        } else {
			state.bypassStatus = "Bypass Off"
        }
        
	    def bypassStatus = "${state.bypassStatus} [${state.bypassZone1}${state.bypassZone2}${state.bypassZone3}${state.bypassZone4}${state.bypassZone5}${state.bypassZone6} ]"
		sendEvent (name: "dscZoneBypass", value: "${bypassStatus}")
	  }
    }

  } else if (chimeList.contains(evt)) {
    // Send chime event
    sendEvent (name: "chime", value: "${evt}")

  } else if (evt.startsWith('key')) {
    def name = evt.minus('alarm').minus('restore')
    def value = evt.replaceAll(/.*(alarm|restore)/, '$1')
      log.debug("Event: ${evt} name: ${name}, value: ${value}")
      //eeddir to do
    //sendEvent (name: "${name}", value: "${value}")
  } else {
    // Send final event
    sendEvent (name: "status", value: "${evt}")
    
    state.status = "${altState}"
    def systemStatus = "${state.status} [${state.zone1}${state.zone2}${state.zone3}${state.zone4}${state.zone5}${state.zone6} ]"
    sendEvent (name: "dscSecuritySystemMode", value: "${systemStatus}")
  }
}


def partitionAddtl(String evt, String zone, Map parameters, String status) {
  // evt will be a valid event for the panel (ready, notready, armed, etc)
  // partition will be a partition number, for most users this will always be 1

  log.debug "Event: ${evt} for zone: ${zone}"
  log.debug "Parameters: ${parameters}, Type: ${status}"

  if (evt.startsWith('zone')) {
	if (zone == "1") {
		state.zone1 = " ${zone}"
    	if (status == "closed") {
    		state.zone1 = ""
    	}
    }
	if (zone == "2") {
		state.zone2 = " ${zone}"
    	if (status == "closed") {
    		state.zone2 = ""
    	}
    }
	if (zone == "3") {
		state.zone3 = " ${zone}"
    	if (status == "closed") {
    		state.zone3 = ""
    	}
    }
	if (zone == "4") {
		state.zone4 = " ${zone}"
    	if (status == "closed") {
    		state.zone4 = ""
    	}
    }
	if (zone == "5") {
		state.zone5 = " ${zone}"
    	if (status == "closed") {
    		state.zone5 = ""
    	}
    }
	if (zone == "6") {
		state.zone6 = " ${zone}"
    	if (status == "closed") {
    		state.zone6 = ""
    	}
    }
    
    def systemStatus = "${state.status} [${state.zone1}${state.zone2}${state.zone3}${state.zone4}${state.zone5}${state.zone6}]"
    sendEvent (name: "dscSecuritySystemMode", value: "${systemStatus}")
  
  } else if (evt.startsWith('bypass')) {
    for (p in parameters) {
        if (p.key == "1") {
            state.bypassZone1 = " ${p.key}"
            if (p.value == "off") {
                state.bypassZone1 = ""
            }
        }
        if (p.key == "2") {
            state.bypassZone2 = " ${p.key}"
            if (p.value == "off") {
                state.bypassZone2 = ""
            }
        }
        if (p.key == "3") {
            state.bypassZone3 = " ${p.key}"
            if (p.value == "off") {
                state.bypassZone3 = ""
            }
        }
        if (p.key == "4") {
            state.bypassZone4 = " ${p.key}"
            if (p.value == "off") {
                state.bypassZone4 = ""
            }
        }
        if (p.key == "5") {
            state.bypassZone5 = " ${p.key}"
            if (p.value == "off") {
                state.bypassZone5 = ""
            }
        }
        if (p.key == "6") {
            state.bypassZone6 = " ${p.key}"
            if (p.value == "off") {
                state.bypassZone6 = ""
            }
        }
	}    
    def bypassStatus = "${state.bypassStatus} [${state.bypassZone1}${state.bypassZone2}${state.bypassZone3}${state.bypassZone4}${state.bypassZone5}${state.bypassZone6} ]"
    sendEvent (name: "dscZoneBypass", value: "${bypassStatus}")
  } else {
      //eeddir to do    
  }
}

def away() {
	armedAway("armedAway")
}
def armedAway(value) {
	log.debug "Executing 'armedAway' val ${value}"
    //sendEvent (name: "dscSecuritySystemMode", value: "armedAway")
    parent.sendUrl("arm?part=${device.deviceNetworkId[-1]}")
}

def stay() {
	armedStay("armedStay")
}
def armedStay(value) {
	log.debug "Executing 'armedStay' val ${value}"
    //sendEvent (name: "dscSecuritySystemMode", value: "armedStay")
    parent.sendUrl("stayarm?part=${device.deviceNetworkId[-1]}")
}

def autobypass() {
	autobypass("autobypass")
}
def autoBypass(value) {
	log.debug "Executing 'autoBypass' val ${value}"
    state.bypassStatus = "Auto-Bypass"
    def bypassStatus = "${state.bypassStatus} [${state.bypassZone1}${state.bypassZone2}${state.bypassZone3}${state.bypassZone4}${state.bypassZone5}${state.bypassZone6} ]"
	sendEvent (name: "dscZoneBypass", value: "${bypassStatus}")
    //sendEvent (name: "dscZoneBypass", value: value)
    parent.autoBypass()
}

def bypassoff() {
	bypassOff("bypassOff")
}
def bypassOff(value) {
	log.debug "Executing 'bypassOff' val ${value}"
    state.bypassStatus = "Bypass Off"
    def bypassStatus = "${state.bypassStatus} [${state.bypassZone1}${state.bypassZone2}${state.bypassZone3}${state.bypassZone4}${state.bypassZone5}${state.bypassZone6} ]"
	sendEvent (name: "dscZoneBypass", value: "${bypassStatus}")
    //sendEvent (name: "dscZoneBypass", value: value)
    parent.sendUrl("bypass?zone=0&part=${device.deviceNetworkId[-1]}")
}

def togglechime() {
	chime()
}
def chime() {
	log.debug "Executing 'chime'"
    parent.sendUrl("togglechime?part=${device.deviceNetworkId[-1]}")
    if (state.chime == "nochime") {
    	sendEvent (name: "chime", value: "chime")
        state.chime = "chime"
    } else {
    	sendEvent (name: "chime", value: "nochime")
        state.chime = "nochime"
    }
}

def disarm() {
	disarmed("disarmed")
}
def disarmed(value) {
	log.debug "Executing 'disarmed' val ${value}"
    //sendEvent (name: "dscSecuritySystemMode", value: "disarmed")
    parent.sendUrl("disarm?part=${device.deviceNetworkId[-1]}")
}

def instant() {
	log.debug "Executing 'instant' "
    //sendEvent (name: "dscInstant", value: "Activated")
    parent.sendUrl("toggleinstant?part=${device.deviceNetworkId[-1]}")
}

def night() {
	log.debug "Executing 'night' "
    //sendEvent (name: "dscNight", value: "Activated")
    parent.sendUrl("togglenight?part=${device.deviceNetworkId[-1]}")
}

def on() {
  def switchStatus = device.currentState("switch").value
  if (switchStatus == "off") {
    sendEvent (name: "switch", value: "on")
    armedAway("armedAway")
  }
}

def off() {
  disarmed("disarmed")
}

def nokey() {
  sendEvent (name: "setAlarmKeysLock", value: "keysLocked")
}

def key() {
  sendEvent (name: "setAlarmKeysLock", value: "keysUnlocked")
}
def setAlarmKeysLock(value) {
	sendEvent (name: "dscLockAlarmKeys", value: value)
          
    if (value == "keysUnlocked") {
	  	sendEvent (name: "dscAlarmKeys", value: "Alarm Keys Active")
        state.keyLock = "unlocked"
    } else {
	  	sendEvent (name: "dscAlarmKeys", value: "Alarm Keys Locked")
        state.keyLock = "locked"
    }
}

def keyfire() {
	keyFire("keyFire")
}
def keyFire(value) {
    if (state.keyLock == "unlocked") {
  	    sendEvent (name: "dscAlarmKeys", value: value)
		//parent.sendUrl('panic?type=1')
    } else {
	  	sendEvent (name: "dscAlarmKeys", value: "Alarm Keys Locked")
	}    
}

def keyaux() {
	keyAux("keyAux")
}
def keyAux(value) {
    if (state.keyLock == "unlocked") {
  	    sendEvent (name: "dscAlarmKeys", value: value)
		//parent.sendUrl('panic?type=2')
    } else {
	  	sendEvent (name: "dscAlarmKeys", value: "Alarm Keys Locked")
	}
}

def keypanic() {
	keyPanic("keyPanic")
}
def keyPanic(value) {
    if (state.keyLock == "unlocked") {
  	    sendEvent (name: "dscAlarmKeys", value: value)
    	//parent.sendUrl('panic?type=3')
    } else {
	  	sendEvent (name: "dscAlarmKeys", value: "Alarm Keys Locked")
	}
}

def refresh() {
  parent.sendUrl('refresh')
}

def reset() {
	sensorReset()
}
def sensorReset() {
    sendEvent (name: "dscSensorReset", value: "Activated")
	parent.sendUrl("reset?part=${device.deviceNetworkId[-1]}")
}


def getPrettyName() {
  return [
    closed: "Closed",
    ready: "Ready",
    forceready: "Force Ready",
    notready: "Not Ready",
    stay: "Armed Stay",
    away: "Armed Away",
    alarmcleared: "Alarm Cleared",
    instant: "Armed Instant",
    night: "Armed Night",
    disarm: "Disarming",
    exitdelay: "Exit Delay",
    entrydelay: "Entry Delay",
    chime: "Toggling Chime",
    bypassoff: "Sending Bypass Off",
    keyfire: "Sending Fire Alert",
    keyaux: "Sending Aux Alert",
    keypanic: "Sending Panic Alert"
  ]
}    

def updated() {
  //do nothing for now
  initialize()
}

def installed() {
  initialize()
}

private initialize() {
  log.trace "Executing initialize()"
  //set default values
  sendEvent (name: "tamper", value: "clear")
  sendEvent (name: "dscZoneBypass", value: "Bypass Off")
  sendEvent (name: "troubleStatus", value: "No Trouble")
  sendEvent (name: "chime", value: "nochime")
  sendEvent (name: "dscSecuritySystemMode", value: "Disarmed")
  sendEvent (name: "alarm", value: "off")
  sendEvent (name: "dscLedBypass", value: "-")
  sendEvent (name: "dscLedReady", value: "-")
  sendEvent (name: "dscLedArmed", value: "-")
  sendEvent (name: "dscLedMemory", value: "-")
  sendEvent (name: "dscLedProgram", value: "-")
  sendEvent (name: "dscLedFire", value: "-")
  sendEvent (name: "dscLedBacklight", value: "-")
  sendEvent (name: "dscLedTrouble", value: "-")
  sendEvent (name: "dscInstant", value: "Not Active")
  sendEvent (name: "dscNight", value: "Not Active")
  sendEvent (name: "dscSensorReset", value: "Not Active")
  sendEvent (name: "dscLockAlarmKeys", value: "Alarm Keys Locked")
  sendEvent (name: "dscAlarmKeys", value: "Alarm Keys Locked")
  sendEvent (name: "switch", value: "off")
  
  state.chime = "nochime"
  state.status = "n/a"
  state.bypassStatus = "n/a"
  state.keyLock = "locked"
  state.zone1 = ""
  state.zone2 = ""
  state.zone3 = ""
  state.zone4 = ""
  state.zone5 = ""
  state.zone6 = ""
  state.bypassZone1 = ""
  state.bypassZone2 = ""
  state.bypassZone3 = ""
  state.bypassZone4 = ""
  state.bypassZone5 = ""
  state.bypassZone6 = ""
}