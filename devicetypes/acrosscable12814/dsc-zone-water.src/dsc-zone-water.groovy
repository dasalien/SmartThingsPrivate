/*
 *  DSC Zone Water Device
 *
 *  Author: Dieter Rothhardt
 *  Orig Author: Ralph Torchia
 *  Originally By: Jordan <jordan@xeron.cc>, Matt Martz <matt.martz@gmail.com>, Kent Holloway <drizit@gmail.com>
 *  Date: 2020-20-18
 */

metadata {
  definition (
  	name: "DSC Zone Water",
    author: "Dieter Rothhardt",
    namespace: 'acrosscable12814',
    mnmn: "SmartThingsCommunity",
    vid: "2e6b1999-334d-3299-adc3-2722059bc4c9"
  )

  {
	capability "Refresh"
	capability "Health Check"
	capability "Water Sensor"
    capability "Sensor"
    capability "Alarm"
    capability "acrosscable12814.zoneBypass"
    capability "acrosscable12814.troubleStatus"

    attribute "bypass", "string"
    attribute "trouble", "string"

    // Add commands as needed
    command "zone"
    command "bypass"
  }

  tiles {}
}

// handle commands
def bypass() {
 log.debug "bypass - not implemented"
}


def setZoneBypass(value) {
  log.debug "setZoneBypass: ${value}"
  def zone = device.deviceNetworkId.minus('dsczone')
  parent.sendUrl("bypass?zone=${zone}")
  sendEvent (name: "zoneBypass", value: value)
}

def zone(String state) {
  // state will be a valid state for a zone (open, closed)
  // zone will be a number for the zone
  log.debug "Zone: ${state}"

  //def troubleList = ['fault','tamper','restore']
  def troubleMap = [
    'restore': 'No Trouble',
    'tamper': 'Tamper',
    'fault': 'Fault'
  ]

  def bypassMap = [
  	'on' : 'Bypassed',
    'off': 'Enabled']

  def alarmMap = [
    'alarm': "both",
    'noalarm': "off"
  ]

  // Since this is a water sensor device we need to convert the values to match the device capabilities
  // Alarming isn't a valid option for this capability, but we map this here anyway, so you can more easily tell which device
  // is alarming from the "things" page.
  def waterMap = [
  	'open':"wet",
    'closed':"dry",
    'noalarm':"dry",
    'alarm':"wet"
  ]

  if (troubleMap.containsKey(state)) {
    sendEvent (name: "trouble", value: "${state}")
    sendEvent (name: "troubleStatus", value: "${troubleMap[state]}")
  } else if (bypassMap.containsKey(state)) {
    sendEvent (name: "bypass", value: "${state}")
    sendEvent (name: "zoneBypass", value: "${bypassMap[state]}")
  } else {
    // Send actual alarm state, if we have one
    if (alarmMap.containsKey(state)) {
      sendEvent (name: "alarm", value: "${alarmMap[state]}")
    } else {
      sendEvent (name: "alarm", value: "off")
    }
    sendEvent (name: "water", value: "${waterMap[state]}")
  }
}

def updated() {
  //do nothing for now

}

def refresh() {
  //do nothing for now
}

def installed() {
  initialize()
}

//just reset if any button is pushed for now
def both() {
  sendEvent (name: "alarm", value: "off")
}
def off() {
  sendEvent (name: "alarm", value: "off")
}
def siren() {
  sendEvent (name: "alarm", value: "off")
}
def strobe() {
  sendEvent (name: "alarm", value: "off")
}

private initialize() {
  log.trace "Executing initialize()"
  //set default values
  sendEvent (name: "trouble", value: "restore")
  sendEvent (name: "bypass", value: "off")
  sendEvent (name: "troubleStatus", value: "No Trouble")
  sendEvent (name: "zoneBypass", value: "Enabled")
  sendEvent (name: "water", value: "dry")
  sendEvent (name: "alarm", value: "off")
}