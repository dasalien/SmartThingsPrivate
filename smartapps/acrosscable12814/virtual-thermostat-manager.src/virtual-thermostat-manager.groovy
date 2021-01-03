definition(
    name: "Virtual Thermostat Manager",
    namespace: "acrosscable12814",
    author: "Dieter Rothhardt",
    description: "Virtual Thermostat to give temperature input. Uses a SmartThings temperature sensor",
    category: "Green Living",
    iconUrl: "https://raw.githubusercontent.com/eliotstocker/SmartThings-VirtualThermostat-WithDTH/master/logo-small.png",
    iconX2Url: "https://raw.githubusercontent.com/eliotstocker/SmartThings-VirtualThermostat-WithDTH/master/logo.png",
	singleInstance: true
)

preferences {
    page(name: "Install", title: "Virtual Thermostat Manager", install: true, uninstall: true) {
        section("Devices") {
        }
        section {
            app(name: "thermostats", appName: "Virtual Thermostat", namespace: "acrosscable12814", title: "New Virtual Thermostat", multiple: true)
        }
    }
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
}