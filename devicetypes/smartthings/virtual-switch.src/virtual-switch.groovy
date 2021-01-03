/**
 *  Copyright 2015 SmartThings
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
metadata {
	definition (name: "Virtual Switch", namespace: "smartthings", author: "SmartThings", ocfDeviceType: "oic.d.switch") {
		capability "Actuator"
		capability "Switch"
		capability "Sensor"
		capability "Health Check"
	}
}

def installed() {
	sendEvent(name: "switch", value: "off")
}

def updated(){
}

def getCommandClassVersions() {
}

def parse(String description) {
}

def on() {
	sendEvent(name: "switch", value: "on")
}

def off() {
	sendEvent(name: "switch", value: "off")
}

def refresh() {
}