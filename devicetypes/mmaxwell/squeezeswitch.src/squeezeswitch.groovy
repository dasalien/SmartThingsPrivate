/**
 *  squeezeSwitch
 *
 *  Copyright 2014 Mike Maxwell
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
 */
//player mac adddresses, for each required player
preferences {
	input("confp1", "string", title:"Enter Player 1 MAC",defaultValue:"00:00:00:00:00:00", required:true, displayDuringSetup:true)
    input("confp2", "string", title:"Enter Player 2 MAC",defaultValue:"00:00:00:00:00:00", required:true, displayDuringSetup:true)
    input("confp3", "string", title:"Enter Player 3 MAC",defaultValue:"00:00:00:00:00:00", required:true, displayDuringSetup:true)
}

metadata {
	definition (name: "squeezeSwitch", namespace: "mmaxwell", author: "Mike Maxwell") {
		capability "Switch"
        //custom commands for multiple players
        //use the standard (built in on/off) if you only have one player
        command "p1On"
        command "p1Off"
        command "p2On"
        command "p2Off"
        command "p3On"
        command "p3Off"
        //enable and use to create the hex version of your squeeze servers CLI interface
        //ip address and port, this will need to be assigned to the "Device Network Id" field
        //after the device is added to your system
        //command "getNid" 

	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("switch", "device.switch", width: 2, height: 2, canChangeIcon: true) {
        state "on", label: '${name}', action: "switch.off", icon: "st.switches.switch.on", backgroundColor: "#79b821"
        state "off", label: '${name}', action: "switch.on", icon: "st.switches.switch.off", backgroundColor: "#ffffff"
        }
	}
    main "switch"
    details(["switch"])
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"

}

private String makeNetworkId(ipaddr, port) { 
	 String hexIp = ipaddr.tokenize('.').collect { 
     String.format('%02X', it.toInteger()) 
     }.join() 
     String hexPort = String.format('%04X', port()) 
     return "${hexIp}:${hexPort}" 
}
def getNID() {
	log.debug makeNetworkId("192.168.0.2", 9000) //your squeeze server CLI interface ip address and port
}

// handle commands for multiple players
def p1On()	{
	//log.debug settings.confp1
    def ha = new physicalgraph.device.HubAction("${settings.confp1} play\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}
def p1Off()	{
	//log.debug settings.confp1
    def ha = new physicalgraph.device.HubAction("${settings.confp1} power 0\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}
def p2On()	{
	//log.debug settings.confp2
    def ha = new physicalgraph.device.HubAction("${settings.confp2} play\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}
def p2Off()	{
	//log.debug settings.confp2
    def ha = new physicalgraph.device.HubAction("${settings.confp2} power 0\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}
def p3On()	{
	//log.debug settings.confp3
    def ha = new physicalgraph.device.HubAction("${settings.confp3} play\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}
def p3Off()	{
	//log.debug settings.confp3
    def ha = new physicalgraph.device.HubAction("${settings.confp3} power 0\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}

// command for one player only
def on() {
	//log.debug "Executing 'on'"    
    def ha = new physicalgraph.device.HubAction("${settings.confp1} play\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}
def off() {
	//log.debug "Executing 'off'"
    def ha = new physicalgraph.device.HubAction("${settings.confp1} power 0\r\n\r\n",physicalgraph.device.Protocol.LAN, "${device.deviceNetworkId}")
    return ha
}