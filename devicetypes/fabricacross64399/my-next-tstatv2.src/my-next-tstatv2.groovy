/***
 *  My Next Tstat
 *  Copyright 2018-2020 Yves Racine
 *  LinkedIn profile: ca.linkedin.com/pub/yves-racine-m-sc-a/0/406/4b/
 *  Version 3.1.1
 *  Refer to readme file for installation instructions.
 *
 *  Developer retains all right, title, copyright, and interest, including all copyright, patent rights,
 *  trade secret in the Background technology. May be subject to consulting fees under an Agreement 
 *  between the Developer and the Customer. Developer grants a non exclusive perpetual license to use
 *  the Background technology in the Softwre developed for and delivered to Customer under this
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

//include 'asynchttp_v1'

// for the UI

preferences {

//	Preferences are no longer required when created with the Service Manager (MyNextServiceMgr).

	input("thermostatId", "text", title: "internal Id", description:
		"internal thermostatID\n(not needed when using MyNextServiceMgr, leave it blank)")
	input("zipcode", "text", title: "ZipCode for setting outdoor Temp", description: "by default,use current hub location")
	input("trace", "bool", title: "trace", description:
		"Set it to true to enable tracing (no spaces) or leave it empty (no tracing)")
	input("logFilter", "number",title: "(1=ERROR only,2=<1+WARNING>,3=<2+INFO>,4=<3+DEBUG>,5=<4+TRACE>)",  range: "1..5",
 		description: "optional" )        
}
metadata {
	// Automatically generated. Make future change here.
	definition(name: "My Next TstatV2", author: "Yves Racine",mnmn: "SmartThingsCommunity",ocfDeviceType:"oic.d.thermostat", vid: "52034de3-05d9-35f3-84f3-0b8f01b9234f",namespace: "fabricacross64399") {
		// vid: 0b86aa41-4a44-3ad2-9910-a0a3260f582a
		capability "Sensor"
		capability "Actuator"
		capability "Thermostat"
		capability "thermostatSetpoint"
		capability "thermostatMode"
		capability "thermostatFanMode"
		capability "thermostatOperatingState"        
		capability "Relative Humidity Measurement"
		capability "Temperature Measurement"
		capability "Polling"
		capability "Refresh"
		capability "Presence Sensor"
		capability "Health Check"
		capability "Motion Sensor"  // for better motion detection, change the polling interval to 1 minute in MyNextManagerV2.
		capability "fabricacross64399.sethomeoraway"
		capability "fabricacross64399.setTargetHumidityLevel"      
nes
/* Not supported in V2
		command "setETA"		// call sample: hvac.setETA("","sample-trip-id","2018-01-30 23:00:00","2018-01-31 23:59:59")   
		command "cancelETA"		// call sample: hvac.cancelETA("","sample-trip-id")         
*/        

		command "getStructure"        
/*
		setStructure(String attributes) 
		parameters: attributes must be a valid groovy map or json structure
 		see list of structure attributes at https://thingsthataresmart.wiki/index.php?title=My_Next_Tstat#Information 
*/        
		command "setStructure"  
		command "setStructureHome"
		command "setStructureAway"
		command "levelUp"
		command "levelDown"
		command "setTemperature", ["number"]
		command "switchMode"        
        
//		command "getThermostatList"
		command "getThermostatInfo"
/*
		setThermostatSettings(thermostatId,String thermostatSettings) 
		parameters: thermostatId (By defaut current tstat), thermostatSettings must be a valid groovy map or json structure
		ex. setThermostatSettings("", "{target_humidity:55, target_humidity_enabled: true}")
 		see list of attributes at https://thingsthataresmart.wiki/index.php?title=My_Next_Tstat#Information                                       
*/
		command "setThermostatSettings" 
		command "setThermostatSharedSettings"
/*
		// setHold("", 75, 77) // parameters: thermostat_sn, coolingSetpoint, heatingSetpoint)
*/
		command "setHold"				
        
		command "heatLevelUp"
		command "heatLevelDown"
		command "coolLevelUp"
		command "coolLevelDown"
		command "setThermostatFanMode"
		command "setThermostatFanTimer", ["number"]
		command "setThermostatFanSchedule" 
/*
		// setThermostatFanSchedule("", "15", "18") // parameters: thermostat_sn, start_time in hour, end_time in hour)
		// will trigger the fan between 3 pm and 6 pm every day        
*/
        
		command "setTemperatureScale"
//		command "setTargetHumidity",["number"]
		command "setHotWaterBoost",["number"]        
        
//		Not supported in V2     
/*
		command "setThermostatLabel"
*/        
		command "away"
		command "present"
		command "home"
		command "eco"
		command "setEcoMode"
       
/* 		Thermostat ranges are supported in the Nest Web APIs in write mode 
*/
		command "setHeatingSetpointRangeHigh" 
		command "setHeatingSetpointRangeLow"
		command "setCoolingSetpointRangeHigh"
		command "setCoolingSetpointRangeLow"
		command "setHeatingSetpointRange"
		command "setCoolingSetpointRange"
		command "getTips"  
		command "resetTips"     
		command "fanOff"  
		command "produceSummaryReport"
		command "save_data_auth"        
        

//		command "generateRTEvents" // Not supported yet


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

		attribute "thermostatId", "string"
		attribute "thermostatName", "string"
		attribute "thermostatsList", "string"
		attribute "thermostatOperatingState", "string"        
		attribute "programScheduleName", "string"
 		attribute "coolingSetpointDisplay", "string"
		attribute "heatingSetpointDisplay", "string"
      
		attribute "heatingSetpointRangeHigh", "string"
		attribute "heatingSetpointRangeLow", "string"
		attribute "coolingSetpointRangeHigh", "string"
		attribute "coolingSetpointRangeLow", "string"
		attribute "coolingSetpointRange", "VECTOR3"
		attribute "heatingSetpointRange", "VECTOR3"
        
		attribute "thermostatId","string"
		attribute "locale", "string"
		attribute "tstat_scale","string"
		attribute "is_using_emergency_heat","string"
		attribute "has_fan","string"
		attribute "software_version","string"
		attribute "has_leaf","string"
		attribute "where_id","string"
		attribute "can_heat","string"
		attribute "can_cool","string"
		attribute "target_temperature_c","string"
		attribute "target_temperature_f","string"
		attribute "target_temperature_high_c","string"
		attribute "target_temperature_high_f","string"
		attribute "target_temperature_low_c","string"
		attribute "target_temperature_low_f","string"
		attribute "ambient_temperature_c","string"
		attribute "ambient_temperature_f","string"
		attribute "eco_temperature_high_c","string"
		attribute "eco_temperature_high_f","string"
		attribute "eco_temperature_low_c","string"
		attribute "eco_temperature_low_f","string"
		attribute "is_locked","string"
		attribute "locked_temp_min_c","string"
		attribute "locked_temp_min_f","string"
		attribute "locked_temp_max_c","string"
		attribute "locked_temp_max_f","string"
		attribute "sunlight_correction_active","string"
//		attribute "sunlight_correction_enabled","string"
		attribute "sunlight_correction_ready","string"
        

		attribute "fan_timer_active","string"
		attribute "fan_timer_timeout","string"
		attribute "fan_timer_duration","number"			
		attribute "fan_timer_duration_string","string"	
		attribute "fan_duty_start_time","string"	
		attribute "fan_duty_end_time","string"	
		attribute "fan_duty_cycle","string"	
		attribute "previous_hvac_mode","string"
		attribute "hvac_mode","string"
		attribute "time_to_target","string"
		attribute "time_to_target_training","string"
		attribute "where_name","string"
		attribute "label","string"
		attribute "battery_health","string"
		attribute "is_manual_test_active","string"
		attribute "last_manual_test_time","string"
		attribute "name_long","string"
		attribute "is_online","string"
		attribute "last_connection","string"
		attribute "last_api_check","string"
		attribute "hvac_state","string"
		attribute "verboseTrace", "string"
        

//		new attributes in V2

		attribute "supportedStructures", "string"	
		attribute "homeOrAway", "string"	
		attribute "operating_state", "string"	
		attribute "current_temperature", "string"	
		attribute "multiroom_temperature_c", "string"
		attribute "multiroom_temperature_f", "string"
		attribute "target_temperature_type", "string"
		attribute "target_temperature", "string" 
		attribute "target_temperature_high", "string"
		attribute "target_temperature_low", "string"
		attribute "eco_temperature_high", "string"
		attribute "eco_temperature_low", "string"
		attribute "temperature_lock_low_temp", "string"
		attribute "temperature_lock_high_temp", "string"
 
		attribute "away_temperature_high_c", "string"
		attribute "away_temperature_high_f", "string"
		attribute "away_temperature_low_c", "string"
		attribute "away_temperature_low_f", "string"
		attribute "away_temperature_enabled", "string"
 		attribute "eco_mode", "string"
		attribute "hvac_ac_state", "string"
		attribute "hvac_heater_state", "string"
 		attribute "auto_away", "string"
 		attribute "auto_away_enabled", "string"
 		attribute "auto_away_reset_enabled", "string"
 		attribute "auto_away_learning", "string"
		attribute "hvac_cool_x2_state", "string"
		attribute "hvac_cool_x2_state", "string"
		attribute "hvac_heat_x2_state", "string"
		attribute "hvac_heat_x3_state", "string"
		attribute "has_aux_heat", "string"
		attribute "hvac_aux_heater_state", "string"
		attribute "hvac_aux_heat_state", "string"
		attribute "hvac_emer_heat_state", "string"
		attribute "backplate_temperature", "string"
		attribute "battery_level", "string"
		attribute "has_humidifier", "string"
		attribute "has_dehumidifier", "string"
		attribute "emer_heat_delivery", "string"
		attribute "emer_heat_source", "string"
		attribute "emer_heat_enabled", "string"
		attribute "cooling_source", "string"
		attribute "cooling_x2_source", "string"
		attribute "cooling_x3_source", "string"
		attribute "heater_delivery", "string"
		attribute "cooling_delivery", "string"
		attribute "alt_heat_delivery", "string"
		attribute "aux_heat_delivery", "string"
		attribute "heat_x2_delivery", "string"
		attribute "cooling_x2_delivery", "string"

		attribute "leaf_threshold_cool_c", "string"
		attribute "leaf_threshold_cool_f", "string"
		attribute "leaf_threshold_heat_c", "string"
		attribute "leaf_threshold_heat_f", "string"
 		attribute "error_code", "string"
	 	attribute "fan_current_modulation", "string"
 		attribute "fan_mode", "string"
 		attribute "fan_capabilities", "string"
	 	attribute "equipment_type", "string"
 		attribute "target_humidity", "string"
 		attribute "target_hum_enabled", "string"
	 	attribute "auto_dehum_enabled", "string"
 		attribute "auto_dehum_state", "string"
		attribute "dehumidifier_fan_activation_enabled","string"        
 		attribute "humidifier_state", "string"
	 	attribute "humidifier_type", "string"
		attribute "humidifier_fan_activation_enabled","string"        
		attribute "has_hot_water_control", "string"
	 	attribute "target_change_pending", "string"
		attribute "alt_heat_source", "string"
		attribute "aux_heat_source", "string"
		attribute "emer_heat_source", "string"
		attribute "emer_heat_delivery", "string"
		attribute "compressor_lockout_enabled", "string"        
		attribute "compressor_lockout_leaf_c", "string"        
		attribute "compressor_lockout_leaf_f", "string"        
		attribute "has_x2_heat", "string"
		attribute "has_x3_heat", "string"
		attribute "has_x2_cool", "string"
		attribute "has_x3_cool", "string"
		attribute "has_water_temperature", "string"
		attribute "has_dual_fuel", "string"
		attribute "heat_pump_aux_threshold_c", "string"
		attribute "heat_pump_aux_threshold_f", "string"
		attribute "heat_pump_comp_threshold_enabled", "string"
		attribute "heat_pump_aux_threshold_enabled", "string"
		attribute "heat_pump_comp_threshold_f", "string"
		attribute "heat_pump_comp_threshold_c", "string"
		attribute "heat_pump_savings", "string"
		attribute "heatpump_setback_active", "string"
		attribute "hot_water_active", "string"
		attribute "hot_water_away_active", "string"
		attribute "preconditioning_active", "string"
		attribute "preconditioning_enabled", "string"
		attribute "hot_water_boiling_state", "string"
		attribute "hot_water_boost_time_to_end","string"
		attribute "hot_water_boost_time_to_end_s","string"
		attribute "heater_delivery", "string"
		attribute "learning_state", "string"
		attribute "hvac_smoke_safety_shutoff_active", "string"
		attribute "rssi", "string"
		attribute "upper_safety_temp_enabled", "string"
		attribute "upper_safety_temp_c", "string"
		attribute "upper_safety_temp_f", "string"
		attribute "lower_safety_temp_enabled", "string"
		attribute "lower_safety_temp_c", "string"
		attribute "lower_safety_temp_f", "string"
		attribute "radiant_control_enabled","string"
		attribute "learning_days_completed_cool","string"
		attribute "learning_days_completed_heat","string"
		attribute "learning_days_completed_range","string"
		attribute "filter_changed_date","string"
		attribute "dual_fuel_breakpoint_f","string"        
		attribute "dual_fuel_breakpoint_c","string"        
		attribute "dual_fuel_override","string"
		attribute "multiroom_active","string"
		attribute "associated_rcs_sensors","string"
		attribute "active_rcs_sensors","string"
		attribute "rcs_control_setting","string"
		attribute "rcs_capable","string"
		attribute "thermostat_alert","string"
		attribute "should_wake_on_approach","string"
		attribute "user_brightness","string"
		attribute "smoke_shutoff_supported","string"
		attribute "hvac_fan_state","string"


		attribute "summaryReport", "string"

		attribute "weatherDateTime", "string"
		attribute "weatherStation", "string"
		attribute "weatherCondition", "string"
		attribute "weatherTemperature", "string"
		attribute "weatherPressure", "string"
		attribute "weatherRelativeHumidity", "number"
		attribute "weatherWindSpeed", "string"
		attribute "weatherWindDirection", "string"
		attribute "weatherPop", "string"

		// Recommendations 
        
		attribute "tip1Text", "string"
		attribute "tip1Level", "string"
		attribute "tip1Solution", "string"
		attribute "tip2Text", "string"
		attribute "tip2Level", "string"
		attribute "tip2Solution", "string"
		attribute "tip3Text", "string"
		attribute "tip3Level", "string"
		attribute "tip3Solution", "string"
		attribute "tip4Text", "string"
		attribute "tip4Level", "string"
		attribute "tip4Solution", "string"
		attribute "tip5Text", "string"
		attribute "tip5Level", "string"
		attribute "tip5Solution", "string"
        
       

	}        
	simulator {
		// TODO: define status and reply messages here
	}

	tiles(scale: 2) {
    
		multiAttributeTile(name:"thermostatMulti", type:"thermostat", width:6, height:4, canChangeIcon: true) {
			tileAttribute("device.temperature", key: "PRIMARY_CONTROL") {
				attributeState("default", label:'${currentValue}', unit:"dF", backgroundColor:"#269bd2") 
			}
			tileAttribute("device.thermostatSetpoint", key: "VALUE_CONTROL") {
				attributeState("default", action: "setTemperature")
				attributeState("VALUE_UP", action: "levelUp")
				attributeState("VALUE_DOWN", action: "levelDown")
			}
			tileAttribute("device.humidity", key: "SECONDARY_CONTROL") {
				attributeState("default", label:'${currentValue}%', unit:"%")
			}
			tileAttribute("device.thermostatOperatingState", key: "OPERATING_STATE") {
				attributeState("idle", backgroundColor:"#44b621")
				attributeState("heating", backgroundColor:"#ffa81e")
				attributeState("cooling", backgroundColor:"#269bd2")
			}
			tileAttribute("device.thermostatMode", key: "THERMOSTAT_MODE") {
				attributeState("off", label:'${name}')
				attributeState("heat", label:'${name}')
				attributeState("cool", label:'${name}')
				attributeState("auto", label:'${name}')
			}
			tileAttribute("device.heatingSetpointDisplay", key: "HEATING_SETPOINT") {
				attributeState("default", label:'${currentValue}', unit:"dF")
			}
			tileAttribute("device.coolingSetpointDisplay", key: "COOLING_SETPOINT") {
				attributeState("default", label:'${currentValue}', unit:"dF")
			}
		}
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}', unit:"dF",
			backgroundColors: getBackgroundColors())
		}
		valueTile("name", "device.thermostatName", inactiveLabel: false, width: 2,
			height: 2) {
			state "default", label: '${currentValue}', 
			backgroundColor: "#ffffff"
		}
      
		standardTile("heatLevelDown", "device.heatingSetpoint", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "heatLevelDown", label:'Heat', action:"heatLevelDown", icon: "${getCustomImagePath()}heatDown.png", backgroundColor: "#ffffff"

		}
		standardTile("heatLevelUp", "device.heatingSetpoint", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "heatLevelUp", label:'Heat', action:"heatLevelUp", icon: "${getCustomImagePath()}heatUp.png", backgroundColor: "#ffffff"
		}

		standardTile("heatingSetpoint", "device.heatingSetpointDisplay", width: 2, height: 2, inactiveLabel: false, decoration: "flat") {
			state "heat", label:'${currentValue} heat',unit:"dF", backgroundColor:"#d04e00"
		}
		standardTile("coolLevelDown", "device.coolingSetpoint", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "coolLevelDown", label:'Cool', action:"coolLevelDown", icon: "${getCustomImagePath()}coolDown.png",backgroundColor: "#ffffff"
		}

		standardTile("coolLevelUp", "device.coolingSetpoint", width: 2, height: 2, canChangeIcon: false, inactiveLabel: false, decoration: "flat") {
			state "coolLevelUp", label:'Cool', action:"coolLevelUp", icon: "${getCustomImagePath()}coolUp.png",  backgroundColor: "#ffffff"
		}

		standardTile("coolingSetpoint", "device.coolingSetpointDisplay", height: 2, width: 2,inactiveLabel: false, decoration: "flat") {
			state "cool", label:'${currentValue} cool', unit:"dF", backgroundColor:"#153591"
		}
		standardTile("mode", "device.thermostatMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "off", label: ' ',action: "switchMode",
				icon: "st.thermostat.heating-cooling-off", backgroundColor: "#ffffff"
			state "eco", label: '${name}', action: "switchMode", nextState: "updating",
				icon: "st.nest.nest-leaf", backgroundColor: "#ffffff"
			state "cool", label: '${name}', action: "switchMode",nextState: "updating",
				icon: "${getCustomImagePath()}coolMode.png", backgroundColor: "#ffffff"
			state "heat", label: '${name}', action: "switchMode",nextState: "updating",
				icon: "${getCustomImagePath()}heatMode.png", backgroundColor: "#ffffff"
			state "auto", label: '${name}', action: "switchMode",nextState: "updating",
				icon: "${getCustomImagePath()}autoMode.png", backgroundColor: "#ffffff"
			state "updating",label:"Working",icon: "st.motion.motion.inactive"		
		}
             
		standardTile("fanMode", "device.thermostatFanMode", inactiveLabel: false,
			decoration: "flat", width: 2, height: 2) {
			state "on", label: '${name}', action: "fanOff", 
				icon: "${getCustomImagePath()}Fan.png",backgroundColor: "#ffffff",
				nextState: "updating"
			state "off", label: '${name}', action: "thermostat.fanOn", 
				icon: "${getCustomImagePath()}Fan.png",backgroundColor: "#ffffff",
				nextState: "updating"
			state "auto", label: '${name}', action: "fanOff", 
				icon: "${getCustomImagePath()}Fan.png",backgroundColor: "#ffffff",
				nextState: "updating"
			state "updating",label:"Working",icon: "st.motion.motion.inactive"		
		}
		standardTile("operatingState", "device.thermostatOperatingState", width: 2, height: 2) {
			state "idle", label:'${name}', backgroundColor:"#ffffff"
			state "heating", label:'${name}', backgroundColor:"#e86d13"
			state "cooling", label:'${name}', backgroundColor:"#00A0DC"
		}
		        
		valueTile("weatherDateTime", "device.weatherDateTime", inactiveLabel: false,
			width: 3, height: 2, decoration: "flat", canChangeIcon: false) {
			state "default", label: '${currentValue}', 
			icon: "${getCustomImagePath()}weather11.png",
 			backgroundColor: "#ffffff"
		}
		valueTile("weatherConditions", "device.weatherCondition", 
			inactiveLabel: false, width: 3, height: 2, decoration: "flat", canChangeIcon: false) {
			state "default", label: 'Forecast ${currentValue}',
			icon: "${getCustomImagePath()}weather11.png",
 			backgroundColor: "#ffffff"
		}
		standardTile("weatherTemperature", "device.weatherTemperature", inactiveLabel:false, width: 2, height: 2, 
			decoration: "flat", canChangeIcon: false) {
			state "default", label: 'OutdoorTemp ${currentValue}', unit:"dF",
			icon: "${getCustomImagePath()}weather2.png",
			backgroundColor: "#ffffff"
		}
		standardTile("weatherRelativeHumidity", "device.weatherRelativeHumidity",
			inactiveLabel: false, width: 2, height: 2,decoration: "flat") {
			state "default", label: 'OutdoorHum ${currentValue}%', unit: "%",
			icon: "${getCustomImagePath()}weather2.png",
 			backgroundColor: "#ffffff"
		}
		valueTile("weatherTempHigh", "device.weatherTempHigh", inactiveLabel: false,
			width: 2, height: 2, decoration: "flat") {
			state "default", label: 'ForecastHigh ${currentValue}',unit:"dF",
 			backgroundColor: "#ffffff"
		}
		valueTile("weatherTempLow", "device.weatherTempLow", inactiveLabel: false,
			width: 2, height: 2, decoration: "flat") {
			state "default", label: 'ForecastLow ${currentValue}', unit:"dF",
 			backgroundColor: "#ffffff"
		}
		standardTile("weatherPop", "device.weatherPop", inactiveLabel: false, 
 			width: 2,height: 2,  decoration: "flat", canChangeIcon: false) {
			state "default", label: 'Precip ${currentValue}', unit: "",
			icon: "${getCustomImagePath()}weather2.png",
 			backgroundColor: "#ffffff"
		}
		standardTile("weatherWindSpeed", "device.weatherWindSpeed", inactiveLabel: false,
			width: 2, height: 2, decoration: "flat") {
			state "default", label: 'WindSpeed ${currentValue}',
			icon: "${getCustomImagePath()}weather2.png",
 			backgroundColor: "#ffffff"
		}
		standardTile("hotWaterBoost", "device.has_hot_water_control", width: 2, height: 2, decoration: "flat") {
			state "true",label: "HotWaterBoost",action:"setHotWaterBoost",nextState:"updating",icon: "${getCustomImagePath()}HotWaterBoost.png"
			state "false",label: "NoHotWaterBoost",action:"doNothing",nextState:"NotHotWaterBoost", icon:"${getCustomImagePath()}NotHotWaterBoost.png"
			state "updating",label:"Working",icon: "st.motion.motion.inactive"
		}
        
		standardTile("refresh", "device.temperature", inactiveLabel: false, canChangeIcon: false,
			decoration: "flat",width: 2, height: 2) {
			state "default", label: 'Refresh',action: "refresh", icon:"${getCustomImagePath()}refresh.png", 			
			backgroundColor: "#ffffff"
		}

      
		standardTile("present", "device.presence", inactiveLabel: false, height:2, width:2, canChangeIcon: false) {
			state "not present", label:'${name}', backgroundColor: "#ffffff", icon:"st.presence.tile.presence-default" 
			state "present", label:'${name}', backgroundColor: "#ffffff", icon:"st.presence.tile.presence-default" 
		}
		valueTile(	"lastConnection", "device.last_connection",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
			state("default",
				label:'LastConnect ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
		standardTile ( "isLocked", "device.is_locked", width: 2, height: 2,  decoration: "flat", inactiveLabel: false) {
			state "false",	label:' unlocked', icon:"st.presence.house.unlocked", defaultState: true
			state "true", 	label:' locked', icon:"st.presence.house.secured"
			backgroundColor: "#ffffff"
		}
		valueTile(	"swVersion", "device.software_version",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
			state("default",
				label:'swVersion ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
		valueTile("timeToTarget", "device.time_to_target", width: 2, height: 2, inactiveLabel: false) {
			state "default", label:'Time To Target ${currentValue}', 
			backgroundColor: "#ffffff"
		}		
		valueTile("isOnline", "device.is_online", width: 2, height: 2,  decoration: "flat", inactiveLabel: false) {
			state "default", label:'Online ${currentValue}',
			backgroundColor: "#ffffff"
		}
 		valueTile(	"lastAPICheck", "device.last_api_check",width: 2, height: 2,canChangeIcon: false,decoration: "flat") {
 			state("default",
				label:'LastAPICheck ${currentValue}',
				backgroundColor: "#ffffff"
			)
		}
		valueTile("fanTimeout", "device.fan_timer_timeout_string", width: 2, height: 2,  decoration: "flat", inactiveLabel: false) {
			state "default", label:'Fan Timeout ${currentValue}',
			backgroundColor: "#ffffff"
		}
		standardTile("motion", "device.motion", width: 2, height: 2) {
			state("active", label:'motion', icon:"st.motion.motion.active", backgroundColor:"#00a0dc")
			state("inactive", label:'no motion', icon:"st.motion.motion.inactive", backgroundColor:"#ffffff", defaultState:true)
		}
        
		htmlTile(name:"graphHTML", action: "getGraphHTML", width: 6, height: 8,  whitelist: ["www.gstatic.com"])
       
       
		main("thermostatMulti")
		details(["thermostatMulti",
			"name",	"mode",	"fanMode",
 			"heatingSetpoint", "heatLevelUp", "heatLevelDown",
 			"coolingSetpoint","coolLevelUp", "coolLevelDown",  
			"motion",
			"present",            
			"hotWaterBoost",
			"timeToTarget",
	         "isLocked",
			"swVersion",
			"lastConnection",
			"lastAPICheck",            
			"refresh",
			"weatherDateTime", "weatherConditions",
			"weatherTemperature", 
			"weatherRelativeHumidity","weatherWindSpeed",
			"graphHTML"            
		])

	}
    
}


def getBackgroundColors() {
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
				[value: 37, color: "#bc2323"]
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
	return results    
}

mappings {
	path("/getGraphHTML") {action: [GET: "getGraphHTML"]}
}

void installed() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "cloud", scheme:"untracked"]), displayed: false)
	state?.scale=getTemperatureScale() 
//	setTemperatureScale("$state?.scale")    
	if (settings.trace) { 
		log.debug("installed>$device.displayName installed with settings: ${settings.inspect()} and state variables= ${state.inspect()}")
	}
	state?.redirectURL=null
	state?.retriesCounter=0        
	state?.redirectURLcount=0            
    
}  
void doNothing(arg=null) {
	if (settings.trace) { 
		log.debug("doNothing>$device.displayName installed with settings: ${settings.inspect()} and state variables= ${state.inspect()}")
	}
		
}

/* Ping is used by Device-Watch in attempt to reach the device
*/
def ping() {
	poll()
}

void poll() {
   
	def thermostatId= determine_tstat_id("") 	    

	def poll_interval=1  // set a minimum of 1min. poll interval to avoid unecessary load on Nest servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"poll>thermostatId = ${thermostatId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), not refreshing data...",
			settings.trace, GLOBAL_LOG_INFO)            
		return
	}
	getThermostatInfo(thermostatId, true)  
	refresh_thermostat(thermostatId)    
	traceEvent(settings.logFilter,"poll>done for thermostatId =${thermostatId}", settings.trace)

}

def updated() {
	def HEALTH_TIMEOUT= (60 * 60)
	sendEvent(name: "checkInterval", value: HEALTH_TIMEOUT, data: [protocol: "cloud", displayed:(settings.trace?:false)])
	sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: JsonOutput.toJson([protocol: "cloud", scheme:"untracked"]), displayed: false)
    
//	state?.scale=getTemperatureScale() 
//	setTemperatureScale(state?.scale)    
	state?.retriesCounter=0       
	state?.retriesSettingsCounter=0       
	state?.retriesSharedSettingsCounter=0       
	state?.retriesHoldCounter=0       
	state?.redirectURLcount=0            
	state?.redirectURL=null
	state?.scale=getTemperatureScale() 
	traceEvent(settings.logFilter,"updated>$device.displayName updated with settings: ${settings.inspect()} and state variables= ${state.inspect()}", settings.trace)
	retrieveDataForGraph()        
}

//remove from the selected devices list in Service Manager
void uninstalled() {
	traceEvent(settings.logFilter, "executing uninstalled for ${this.device.displayName}", settings.trace)
	parent.purgeChildDevice(this)    
}

private def evaluate(temp, heatingSetpoint, coolingSetpoint, nosetpoint=false) {
	def oldState = device.currentValue("thermostatOperatingState")
	traceEvent(settings.logFilter,"evaluate($temp, $heatingSetpoint, $coolingSetpoint), old State=$oldState", settings.trace)
	def threshold = (state?.scale == 'C') ? 0.5 : 1
	def mode = device.currentValue("thermostatMode")
	def heating = false
	def cooling = false
	def idle = true
 	def dataEvents=[:]
    
	if (mode in ["heat","emergency heat","auto"]) {
		if ((temp - heatingSetpoint) >= threshold) {
			heating = true
			if (!nosetpoint) {            
				setHeatingSetpoint(temp)
				traceEvent(settings.logFilter,"increasing heat to $temp", settings.trace)
			}                
			dataEvents= ['thermostatOperatingState':"heating"] 
		}
		else if (((heatingSetpoint - temp) >= threshold)) {
			if (!nosetpoint) {            
				setHeatingSetpoint(temp)
				traceEvent(settings.logFilter,"decreasing heat to $temp", settings.trace)
			}	        
		}
	}
	if (mode in ["cool","auto"]) {
		if ((coolingSetpoint - temp) >= threshold) {
			cooling = true
			if (!nosetpoint) {            
				setCoolingSetpoint(temp)
				traceEvent(settings.logFilter,"increasing cool to $temp", settings.trace)
			}	        
			dataEvents= ['thermostatOperatingState':"cooling"] 
		} else if (((temp - coolingSetpoint) >= threshold) && (!heating)) {
			if (!nosetpoint) {            
				setCoolingSetpoint(temp)
				traceEvent(settings.logFilter,"decreasing cool to $temp", settings.trace)
			}	        
		}
	}
	if (idle && !heating && !cooling) {
			dataEvents=  ['thermostatOperatingState':"idle"] 
				traceEvent(settings.logFilter,"idle state", settings.trace)
            
	}
   
	if (mode in ["auto"]) {
			dataEvents=  ['thermostatOperatingState':"Automatic at $coolingSetpoint,$heatingSetpoint"]  
	}            
	generateEvent(dataEvents)
	
}

def switchMode() {
	traceEvent(settings.logFilter, "swithMode>begin", settings.trace)
	def currentMode = device.currentState("thermostatMode")?.value
	def modeOrder =  getSupportedThermostatModes().minus('emergency heat').minus('auxHeatOnly')
	def index = modeOrder.indexOf(currentMode)
	def next = index >= 0 && index < modeOrder.size() - 1 ? modeOrder[index + 1] : modeOrder[0]
	traceEvent(settings.logFilter, "swithMode>switching thermostatMode to $next", settings.trace)
	"$next" ()
}

void setTemperature(value) {
	traceEvent(settings.logFilter,"setTemperature>initial value= $value")
	def mode = device.currentValue("thermostatMode")
	def heatTemp = device.currentValue("heatingSetpoint")
	def coolTemp = device.currentValue("coolingSetpoint")
	def curTemp = device.currentValue("temperature")
	switch(mode) {
		case 'heat':
			state?.currentTileValue= (!state?.currentTileValue)? heatTemp: state?.currentTileValue 
		break
		case 'cool':
			state?.currentTileValue= (!state?.currentTileValue)? coolTemp: state?.currentTileValue 
		break
		case 'eco':
		case 'auto':
		case 'off':        
		default:
			state?.currentTileValue= (!state?.currentTileValue)? curTemp: state?.currentTileValue 
		break
	}            

	if (state?.scale== 'C') { 		
    
		double temp  
		if (value==-1 || value == 0) {
			temp = state?.currentTileValue - 0.5           
		} else if (value==1) {
			temp = state?.currentTileValue + 0.5            
		} else {        
			temp = (value <= state?.currentTileValue)? (state?.currentTileValue - 0.5) : ( state?.currentTileValue + 0.5)   
		}   
		      
		traceEvent(settings.logFilter,"setTemperature in Celsius>after temp correction= $temp vs. state.currentTileValue=$state.currentTileValue",settings.trace)
		evaluate(temp, heatTemp, coolTemp)
		state?.currentTileValue=temp	                
			        
	} else {
		int temp        
		if (value==-1 || value == 0) {
			temp = state?.currentTileValue - 1           
		} else if (value==1) {
			temp = state?.currentTileValue + 1            
		} else {        
			temp = (value <= state?.currentTileValue)? state?.currentTileValue - 1 : state?.currentTileValue + 1   
		}        
		traceEvent(settings.logFilter,"setTemperature in Fahrenheit>after temp= $temp vs. state.currentTileValue=$state.currentTileValue",settings.trace)
		evaluate(temp, heatTemp, coolTemp)
		state?.currentTileValue=temp	                
			        
	}        
        
}		

private void updateCurrentTileValue() {
	def heatTemp = device.currentValue("heatingSetpoint")
	def coolTemp = device.currentValue("coolingSetpoint")
	def curTemp = device.currentValue("temperature")
	def mode = device.currentValue("thermostatMode")
    
	switch(mode) {
		case 'heat':
			state?.currentTileValue=  heatTemp.toDouble() 
		break
		case 'cool':
			state?.currentTileValue= coolTemp.toDouble()
		break
		case 'eco':
		case 'auto':
		case 'off':        
		default:
			state?.currentTileValue= (coolTemp.toDouble() + heatTemp.toDouble())/2
		break
	}
   
	String tempValueString 
	if (state?.scale =='C') {    
		tempValueString = String.format('%2.1f', state?.currentTileValue)                    
		if (tempValueString.matches(".*([.,][3456])")) {                
			tempValueString=String.format('%2d.5',state?.currentTileValue.intValue())                
			traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
		} else if (tempValueString.matches(".*([.,][789])")) {  
			traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
			state?.currentTileValue=state?.currentTileValue.intValue() + 1                        
			tempValueString=String.format('%2d.0', state?.currentTileValue.intValue())               
		} else {
			traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
			tempValueString=String.format('%2d.0', state?.currentTileValue.intValue())               
		}
		state?.currentTileValue= tempValueString.toDouble().round(1)                        
	} else {
		tempValueString = String.format('%2d', state?.currentTileValue.intValue())
		state?.currentTileValue= tempValueString.toDouble()        
	}    
}

void levelUp() {
	setTemperature(1)
	sendEvent(name:"programScheduleName",value:"hold_${now()}", isStateChange:true, displayed:false)    
}

void levelDown() {
	setTemperature(-1)
	sendEvent(name:"programScheduleName",value:"hold_${now()}", isStateChange:true, displayed:false)    
}
void coolLevelUp() {
	def coolAble=canCool()    
	def scale = state?.scale
	def coolingSetpointRangeHigh
	double nextLevel
	def currentMode=device.currentValue("thermostatMode")    
	if ((currentMode == 'auto' && (!coolAble)) || 
		((currentMode== 'cool' && (!coolAble))  ||
 		((currentMode in ['heat', 'off'])))) {
		traceEvent(settings.logFilter, "coolLevelUp>not able to increase coolSP as the thermostat is not in cool mode or does not have cool capability ", 
			settings.trace, GLOBAL_LOG_WARN)
		return            
	}

	try  {   
		coolingSetpointRangeHigh=device.currentValue("coolingSetpointRangeHigh")
	} catch (e) {
		traceEvent(settings.logFilter,"coolLevelUp>$e, not able to get coolingSetpointRangeHigh ($coolingSetpointRangeHigh), using default value",
			true, GLOBAL_LOG_INFO)        
	}    
	coolingSetpointRangeHigh= (coolingSetpointRangeHigh) ? coolingSetpointRangeHigh.toDouble() : (scale == 'C')?30.0:99   
	def coolingSetpoint = device.currentValue("coolingSetpoint")
	nextLevel = (coolingSetpoint) ? coolingSetpoint.toDouble():(scale == 'C') ? 20.0 : 72     
	if (scale == 'C') {
		nextLevel = (nextLevel + 0.5).round(1)        
		traceEvent(settings.logFilter, "coolLevelUp>coolingSetpoint =$coolingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel > coolingSetpointRangeHigh.toDouble().round(0)) {
			nextLevel = coolingSetpointRangeHigh.toDouble().round(0)
		}
		setCoolingSetpoint(nextLevel)
	} else {
		nextLevel = nextLevel + 1    
		traceEvent(settings.logFilter, "coolLevelUp>coolingSetpoint =$coolingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel > coolingSetpointRangeHigh.toDouble()) {
			nextLevel = coolingSetpointRangeHigh.toDouble()
		}
		setCoolingSetpoint(nextLevel.intValue())
	}
	sendEvent(name:"programScheduleName",value:"hold_${now()}", isStateChange:true, displayed:false)    
	updateCurrentTileValue()    
}

void coolLevelDown() {
	def coolAble=canCool()    
	def scale = state?.scale
	def coolingSetpointRangeLow 
	double nextLevel
	def currentMode=device.currentValue("thermostatMode")    
	if ((currentMode == 'auto' && (!coolAble)) || 
		((currentMode== 'cool' && (!coolAble))  ||
 		((currentMode in ['heat', 'off'])))) {
		traceEvent(settings.logFilter, "coolLevelDown>not able to decrease coolSP as the thermostat is not in cool mode or does not have cool capability ", 
			settings.trace, GLOBAL_LOG_WARN)
		return            
	}

	try  {   
		coolingSetpointRangeLow=device.currentValue("coolingSetpointRangeLow")
	} catch (e) {
		traceEvent(settings.logFilter,"coolLevelDown>$e, not able to get coolingSetpointRangeLow ($coolingSetpointRangeLow), using default value",
			true, GLOBAL_LOG_INFO, true)        
	}    
	coolingSetpointRangeLow= (coolingSetpointRangeLow!=null)?coolingSetpointRangeLow.toDouble(): (scale == 'C')?10.0:50   
 
	def coolingSetpoint = device.currentValue("coolingSetpoint")
	nextLevel = (coolingSetpoint) ? coolingSetpoint.toDouble():(scale == 'C') ? 20.0 : 72     
	if (scale == 'C') {
		nextLevel = (nextLevel - 0.5).round(1)        
		traceEvent(settings.logFilter, "coolLevelUp>coolingSetpoint =$coolingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel < coolingSetpointRangeLow.toDouble().round(0)) {
			nextLevel = coolingSetpointRangeLow.toDouble().round(0)
		}
		setCoolingSetpoint(nextLevel)
	} else {
		nextLevel = (nextLevel - 1)
		traceEvent(settings.logFilter, "coolLevelUp>coolingSetpoint =$coolingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel < coolingSetpointRangeLow.toDouble()) {
			nextLevel = coolingSetpointRangeLow.toDouble()
		}
		setCoolingSetpoint(nextLevel.intValue())
	}
	sendEvent(name:"programScheduleName",value:"hold_${now()}", isStateChange:true, displayed:false)    
	updateCurrentTileValue()    
}
void heatLevelUp() {
	def heatAble=canHeat()    
	def scale = state?.scale
	def heatingSetpointRangeHigh
	double nextLevel
	def currentMode=device.currentValue("thermostatMode")
	if ((currentMode == 'auto' && (!heatAble)) || 
		((currentMode== 'heat' && (!heatAble))  ||
 		((currentMode in ['cool', 'off'])))) {
		traceEvent(settings.logFilter, "heatLevelUp>not able to increase heatSP as the thermostat is not in heat mode or does not have heat capability ", 
			settings.trace, GLOBAL_LOG_WARN)
		return            
	}
	try {
		heatingSetpointRangeHigh = device.currentValue("heatingSetpointRangeHigh")
	} catch (e) {
		traceEvent(settings.logFilter, "heatLevelUp>$e, not able to get heatingSetpointRangeHigh ($heatingSetpointRangeHigh),using default value", 
			settings.trace, GLOBAL_LOG_WARN)
	}
	heatingSetpointRangeHigh = (heatingSetpointRangeHigh) ?heatingSetpointRangeHigh.toDouble(): (scale == 'C') ? 10.0 : 50
	traceEvent(settings.logFilter, "heatLevelUp>heatingSetpointRangeHigh =$heatingSetpointRangeHigh", settings.trace)
 
	def heatingSetpoint = device.currentValue("heatingSetpoint")
	nextLevel = (heatingSetpoint) ? heatingSetpoint.toDouble():(scale == 'C') ? 20.0 : 72     
	if (scale == 'C') {
		nextLevel = (nextLevel + 0.5).round(1)
		traceEvent(settings.logFilter, "heatLevelUp>heatingSetpoint =$heatingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel > heatingSetpointRangeHigh.toDouble().round(1)) {
			nextLevel = heatingSetpointRangeHigh.toDouble().round(1)
		}
		setHeatingSetpoint(nextLevel)
	} else {
		nextLevel = (nextLevel + 1)
		traceEvent(settings.logFilter, "heatLevelUp>heatingSetpoint =$heatingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel > heatingSetpointRangeHigh.toDouble()) {
			nextLevel = heatingSetpointRangeHigh.toDouble()
		}
		setHeatingSetpoint(nextLevel.intValue())
	}
	sendEvent(name:"programScheduleName",value:"hold_${now()}", isStateChange:true, displayed:false)    
	updateCurrentTileValue()    
}
void heatLevelDown() {
	def heatAble=canHeat()    

	if (((currentMode == 'auto' && (!heatAble)) || 
		((currentMode== 'heat' && (!heatAble)) ||
 		((currentMode in ['cool', 'off']))))) {
		traceEvent(settings.logFilter, "heatLevelUp>not able to decrease heatSP as the thermostat is not in heat mode or does not have heat capability ", 
			settings.trace, GLOBAL_LOG_WARN)
		return            
	}
	def scale = state?.scale
	def heatingSetpointRangeLow
	double nextLevel

	try {
		heatingSetpointRangeLow = device.currentValue("heatingSetpointRangeLow")
	} catch (e) {
		traceEvent(settings.logFilter,"heatLevelDown>$e, not able to get heatingSetpointRangeLow ($heatingSetpointRangeLow),using default value",
			settings.trace, GLOBAL_LOG_WARN)
	}
	heatingSetpointRangeLow = (heatingSetpointRangeLow!=null) ?heatingSetpointRangeLow.toDouble(): (scale == 'C') ? 10.0 : 50
	traceEvent(settings.logFilter, "heatLevelUp>heatingSetpointRangeLow =$heatingSetpointRangeLow", settings.trace)
	def heatingSetpoint = device.currentValue("heatingSetpoint")
	nextLevel = (heatingSetpoint) ? heatingSetpoint.toDouble():(scale == 'C') ? 20.0 : 72     
	if (scale == 'C') {
		nextLevel = (nextLevel - 0.5).round(1)
		traceEvent(settings.logFilter, "heatLevelDown>heatingSetpoint =$heatingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel < heatingSetpointRangeLow.toDouble().round(1)) {
			nextLevel = heatingSetpointRangeLow.toDouble().round(1)
		}
		setHeatingSetpoint(nextLevel)
	} else {
		nextLevel = (nextLevel - 1)
		traceEvent(settings.logFilter, "heatLevelDown>heatingSetpoint =$heatingSetpoint, nextLevel=$nextLevel", settings.trace)
		if (nextLevel < heatingSetpointRangeLow.toDouble()) {
			nextLevel = heatingSetpointRangeLow.toDouble()
		}
		setHeatingSetpoint(nextLevel.intValue())
	}
	sendEvent(name:"programScheduleName",value:"hold_${now()}", isStateChange:true, displayed:false)    
	updateCurrentTileValue()    
}

private def canCool() {
	def result=(device.currentValue("can_cool") =='true'?true :false)      
	return result
}

private def canHeat() {
	def result=(device.currentValue("can_heat") =='true'? true :false)       
	return result
}
private def isRangeHighLocked() {
	def result=(device.currentValue("upper_safety_temp_enabled") =='true'? true :false)       
	return result
}
private def isRangeLowLocked() {
	def result=(device.currentValue("lower_safety_temp_enabled") =='true'? true :false)       
	return result
}


private def fanActive() {
	def timeout=device.currentValue("fan_timer_timeout")
	def fan_state=device.currentValue("hvac_fan_state")    
	traceEvent(settings.logFilter, "fanActive>timeout=$timeout, hvac_fan_state=$fan_state", settings.trace)
	def result=(fan_state=='true') ? true : false
	if ((timeout.toLong()!=0) && (fan_state=='false')) {
		traceEvent(settings.logFilter, "fanActive>timeout $timeout is inconsistent with hvac_fan_state, forcing true to result", settings.trace)
		result=true    
	}    
	traceEvent(settings.logFilter, "fanActive>result=$result", settings.trace)
	return result
}
private def hotWaterActive() {
	def result=(device.currentValue("hot_water_active")=='true'? true :false)    
	return result
}

private def getPreviousHvacMode() {
	def result= (device.currentValue("previous_hvac_mode")) ?:  device.currentValue("target_temperature_type")   
	return result
}

private def hasFan() {
	def result=(device.currentValue("has_fan") =='true' ? true :false)       
	return result
}

private def isOnline() {
	def result=(device.currentValue("is_online")=='true'? true :false)        
	return result
}

private def isUsingEmergencyHeat() {
	def result=(device.currentValue("is_using_emergency_heat") =='true'? true :false)       
	return result
}

private def get_fan_timer_duration() {
	def result=0
    
    def timer_duration= device.currentValue("fan_timer_duration")
	if (timer_duration) {
		result=timer_duration/60 // in minutes    
	}    
	return result
}



void setHeatingSetpoint(temp) {	
	def heatAble=canHeat()    
	def currentMode=device.currentValue("thermostatMode")
	if (((currentMode == 'auto' && (!heatAble)) || 
		((currentMode== 'heat' && (!heatAble))  ||
 		((currentMode in ['cool', 'off']))))) {
		traceEvent(settings.logFilter, "setHeatingSetpoint>not able to change heatingSetpoint as the thermostat is not in heat mode or does not have heat capability ", 
			settings.trace, GLOBAL_LOG_WARN)
		return            
	}
	def scale = state?.scale
	def heatingSetpointRangeLow,heatingSetpointRangeHigh

	try  {   
		heatingSetpointRangeLow= device.currentValue("heatingSetpointRangeLow")
		heatingSetpointRangeHigh=device.currentValue("heatingSetpointRangeHigh")
	} catch (e) {
		traceEvent(settings.logFilter,"setHeatingSetpoint>$e, not able to get heatingSetpointRangeLow ($heatingSetpointRangeLow) or heatingSetpointRangeHigh ($heatingSetpointRangeHigh), using default values",
			true, GLOBAL_LOG_INFO, true)        
	}    
	heatingSetpointRangeLow= (heatingSetpointRangeLow)?: (scale == 'C')?10.0:50       
	heatingSetpointRangeHigh= (heatingSetpointRangeHigh)?: (scale == 'C')?30.0:99  
	
	if ((temp < heatingSetpointRangeLow.toDouble()) || (temp > heatingSetpointRangeHigh.toDouble())) {
		traceEvent(settings.logFilter,"setHeatingSetpoint>$temp is not within range [$heatingSetpointRangeLow...$heatingSetpointRangeHigh]",
			true, GLOBAL_LOG_WARN, true)        
	}    
	double tempValue 
	String tempValueString    
	if (scale =='C') {    
		tempValue =temp.toDouble().round(1)   
		tempValueString = String.format('%2.1f', tempValue)                    
		if (tempValueString.matches(".*([.,][3456])")) {                
			tempValueString=String.format('%2d.5',tempValue.intValue())                
			traceEvent(settings.logFilter,"setHeatingSetpoint>value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
		} else if (tempValueString.matches(".*([.,][789])")) {  
			traceEvent(settings.logFilter,"setHeatingSetpoint>value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
			tempValue=tempValue.intValue() + 1                        
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		} else {
			traceEvent(settings.logFilter,"setHeatingSetpoint>value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		}
		tempValue= tempValueString.toDouble().round(1)                        
	} else {
		tempValue= temp.toDouble().round()        
		tempValueString = String.format('%2d', tempValue.intValue())
		tempValue= tempValueString.toDouble()        
	}    
	def coolSP=device.currentValue("coolingSetpoint") 
	if ((scale!='C') && ((coolSP && (coolSP.toDouble()  < heatingSetpointRangeLow.toDouble())))) {
		coolSP = cToF_Rounded(coolSP)			    
	}    
	setHold("",coolSP, tempValue)
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		traceEvent(settings.logFilter,"setHeatingSetpoint>$temp was not set, exception $exceptionCheck",
			true, GLOBAL_LOG_WARN, true)        
		return		    
	}   
	sendEvent(name: 'heatingSetpoint', value: tempValueString,unit: scale,isStateChange:true)
	sendEvent(name: 'heatingSetpointDisplay', value: tempValueString,unit: scale,isStateChange:true)
	if (currentMode=='heat')  {
		sendEvent(name:'thermostatSetpoint', value: tempValueString, unit: scale,isStateChange:true)     
	}
    
	if (currentMode=='auto') {  
		double medianPoint= ((tempValue + coolSP)/2).toDouble()
		if (scale == "C") {
			tempValue = medianPoint.round(1)
			tempValueString = String.format('%2.1f', medianPoint)
		} else {
			tempValue = medianPoint.toDouble().round()
			tempValueString= String.format('%2d', medianPoint.intValue())            
		}
		sendEvent(name:'thermostatSetpoint', value: tempValueString, unit: scale,isStateChange:true)     
  	}      
	def currentProgName=device.currentValue("programScheduleName")
	if (currentProgName && currentProgName?.contains("hold_")) {
		def newHoldTime=currentProgName.minus('hold_')
		def timestamp=newHoldTime.substring(0,(newHoldTime.length()-1)).toLong()        
		long nowMinus1Min= now() - (1*60 *1000)
		if (timestamp && timestamp > nowMinus1Min) { // setCoolingSetpoint was called by an external app, not within this device
			sendEvent(name:"programScheduleName",value:"auto", displayed:(settings.trace?:0))			        
		} else {
			sendEvent(name:"programScheduleName",value:"hold", displayed:(settings.trace?:0)) // just a regular hold event within the device.			        
		}        
	}    
    
}


void setCoolingSetpoint(temp) {
	def currentMode = device.currentValue("thermostatMode")
	def coolAble=canCool()    
    
	if ((currentMode == 'auto' && (!coolAble)) || 
		((currentMode== 'cool' && (!coolAble))  ||
 		((currentMode in ['heat', 'off'])))) {
		traceEvent(settings.logFilter, "setCoolingSetpoint>not able to change coolingSetpoint as the thermostat is not in cool mode or does not have cool capability ", 
			settings.trace, GLOBAL_LOG_WARN)
		return            
	}
	def scale = state?.scale
	def coolingSetpointRangeLow,coolingSetpointRangeHigh
	    
	try  {   
		coolingSetpointRangeLow= device.currentValue("coolingSetpointRangeLow")
		coolingSetpointRangeHigh=device.currentValue("coolingSetpointRangeHigh")
	} catch (e) {
		traceEvent(settings.logFilter,"setCoolingSetpoint>$e, not able to get coolingSetpointRangeLow ($coolingSetpointRangeLow) or coolingSetpointRangeHigh ($coolingSetpointRangeHigh), using default values",
			true, GLOBAL_LOG_INFO, true)        
	}    
	coolingSetpointRangeLow= (coolingSetpointRangeLow) ?: (scale == 'C')?10.0:50       
	coolingSetpointRangeHigh= (coolingSetpointRangeHigh) ?:  (scale == 'C')?30.0:99  
    
	if ((temp < coolingSetpointRangeLow.toDouble()) || (temp > coolingSetpointRangeHigh.toDouble())) {
		traceEvent(settings.logFilter,"setCoolingSetpoint>$temp is not within range [$coolingSetpointRangeLow...$coolingSetpointRangeHigh]",
			true, GLOBAL_LOG_WARN, true)        
	}    
	String tempValueString    
	double tempValue 
	if (scale =='C') {    
		tempValue =temp.toDouble().round(1)   
		tempValueString = String.format('%2.1f', tempValue)                    
		if (tempValueString.matches(".*([.,][3456])")) {                
			tempValueString=String.format('%2d.5',tempValue.intValue())                
			traceEvent(settings.logFilter,"setCoolingSetpoint>value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
		} else if (tempValueString.matches(".*([.,][789])")) {  
			traceEvent(settings.logFilter,"setCoolingSetpoint>value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
			tempValue=tempValue.intValue() + 1                        
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		} else {
			traceEvent(settings.logFilter,"setCoolingSetpoint>value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
			tempValueString=String.format('%2d.0', tempValue.intValue())               
		}
		tempValue= tempValueString.toDouble().round(1)                        
	} else {
		tempValue =temp.toDouble().round()   
		tempValueString = String.format('%2d', tempValue.intValue())
		tempValue= tempValueString.toDouble()        
	}    
	def heatSP=device.currentValue("heatingSetpoint") 
	if ((scale!='C') && ((heatSP && ((heatSP.toDouble()  < coolingSetpointRangeLow.toDouble()))))) {
		heatSP = cToF_Rounded(heatSP)			    
	}    
 	setHold("", tempValue, heatSP)
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		traceEvent(settings.logFilter,"setCoolingSetpoint>$temp was not set, exception $exceptionCheck",
			true, GLOBAL_LOG_WARN, true)        
		return		    
	}    
	sendEvent(name: 'coolingSetpoint', value: tempValueString, unit: scale, isStateChange:true)
	sendEvent(name: 'coolingSetpointDisplay', value: tempValueString, unit: scale, isStateChange:true)
	if (currentMode=='cool') {
		sendEvent(name:'thermostatSetpoint', value: tempValueString, unit: scale,isStateChange:true)     
	}
	if (currentMode=='auto') {  
	 	double medianPoint= ((tempValue + heatSP)/2).toDouble()
		if (scale == "C") {
			tempValue = medianPoint.round(1)
			tempValueString = String.format('%2.1f', medianPoint)
		} else {
			tempValue = medianPoint.toDouble().round()
			tempValueString= String.format('%2d', medianPoint.intValue())            
		}
		sendEvent(name:'thermostatSetpoint', value: tempValueString, unit: scale,isStateChange:true)     
        
  	}      
	def currentProgName=device.currentValue("programScheduleName")
	if (currentProgName && currentProgName?.contains("hold_")) {
		def newHoldTime=currentProgName.minus('hold_')
		def timestamp=newHoldTime.substring(0,(newHoldTime.length()-1)).toLong()        
		long nowMinus1Min= now() - (1*60 *1000)
		if (timestamp && timestamp > nowMinus1Min) { // setCoolingSetpoint was called by an external app, not within this device
			sendEvent(name:"programScheduleName",value:"auto", displayed:(settings.trace?:0))			        
		} else {
			sendEvent(name:"programScheduleName",value:"hold", displayed:(settings.trace?:0)) // just a regular hold event within the device.			        
		}        
	}    
    
}
void eco() {
	setThermostatMode('eco')
}
void off() {
	setThermostatMode('off')
}
void auto() {
	setThermostatMode('auto')
}
void heat() {
	setThermostatMode('heat')
}
void emergencyHeat() {
	setThermostatMode('emergency heat')
}
void cool() {
	setThermostatMode('cool')
}

void fanOn() {
	setThermostatFanMode('on')
}
void fanAuto() {
	setThermostatFanMode('auto')
}
void fanOff() { 
	setThermostatFanMode('off')
}
def fanCirculate() {
	fanOn()
}

def getSupportedFanModes() {

	if (!state?.supportedThermostatFanModes) {	
		state?.supportedThermostatFanModes= (device.currentValue("supportedThermostatFanModes"))? 
			device.currentValue("supportedThermostatFanModes")?.toString()?.minus('[')?.minus(']')?.tokenize(',') : ['off','on','auto','circulate']
  
	}
    
	return state?.supportedThermostatFanModes
}    

def getSupportedThermostatModes() {

	if (!state?.supportedThermostatModes) {	
		state?.supportedThermostatModes = (device.currentValue("supportedThermostatModes")) ?
			device.currentValue("supportedThermostatModes")?.toString()?.minus('[')?.minus(']')?.tokenize(',') : ['off','heat', 'cool', 'auto']
	}
    
	return state?.supportedThermostatModes
}


void setThermostatFanMode(mode) {
	mode=mode?.toLowerCase()
	mode = (mode == 'auto') ? 'off' : mode
	def supportedThermostatFanModes = getSupportedFanModes()
    
	if (supportedThermostatFanModes.toString()?.contains(mode)) {
		if (mode != 'off') { 
			setThermostatFanTimer()  // 15 minutes by default
		} else {
			setThermostatFanTimer(0)
		}        
	} else {
		traceEvent(settings.logFilter,"setThermostatFanMode>fan $mode mode is not supported in $supportedThermostatFanModes",
			true, GLOBAL_LOG_WARN, true)
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		return    
	}    
	
	sendEvent(name: 'thermostatFanMode', value: mode,isStateChange:true)
}

void setThermostatFanTimer(timerInMinutes=15)  {
	int fan_timer_duration=0
	boolean fanTimerActive= fanActive()
	if (fanTimerActive && timerInMinutes !=0) {
		traceEvent(settings.logFilter,"setThermostatFanTimer>fanTimer is already active",
			true, GLOBAL_LOG_WARN, true)
		return
	}    
	long fanTimeout =0,fan_timer_timeout_for_nest=0
	if (timerInMinutes) {    
		fanTimeout = now() + (timerInMinutes * 60 * 1000)
		fan_timer_timeout_for_nest = fanTimeout / 1000 //(epoch in secs)        
		fan_timer_duration = (timerInMinutes * 60 )         
		fanTimerActive=true		
		traceEvent(settings.logFilter,"setThermostatFanTimer>fan_timer_duration = ${fan_timer_duration} secs, fanTimeout in epoch millisec=$fanTimeout, fan_timer_timeout_for_nest in epoch secs: ${fan_timer_timeout_for_nest}")
		setThermostatSettings("", ["fan_timer_timeout": fan_timer_timeout_for_nest,"fan_timer_duration": fan_timer_duration])
        
	} else {
		setThermostatSettings("", ["fan_timer_timeout":0])
		fanTimerActive=false		    
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		return    
	} 
	sendEvent(name: "fan_timer_active", value: "${fanTimerActive}",isStateChange:true)
	sendEvent(name: "fan_timer_timeout", value: fan_timer_timeout_for_nest.toString(), isStateChange:true)
	if (timerInMinutes) {    
		sendEvent(name: "fan_timer_duration", value: "${timerInMinutes *60}",isStateChange:true)
	}  
	if ( fan_timer_timeout_for_nest) {
    		def fan_timeout_string=formatTimeInTimezone((fan_timer_timeout_for_nest*1000))?.substring(0,16)
			sendEvent(name: 'fan_timer_timeout_string', value:fan_timeout_string, isStateChange:true)    
	} else {
 			sendEvent(name: 'fan_timer_timeout_string', value:'None', isStateChange:true)    
   
	}    
	    
}

// startTime and endTime must be a number between 0 and 24

void setThermostatFanSchedule(startTime, endTime)  {

	if ((!startTime.isNumber()) || (!endTime.isNumber())) {    
		traceEvent(settings.logFilter,"setThermostatFanSchedule>startTime ${startTime} or endTime ${endTime} not a number",true, GLOBAL_LOG_WARN)
		return        
	}
	if (startTime <0  || startTime > 24) {
		traceEvent(settings.logFilter,"setThermostatFanSchedule>startTime ${startTime} is not between 0 and 24 hours",true, GLOBAL_LOG_WARN)
		return    
	}   	 
	if (endTime <0  || endTime > 24) {
		traceEvent(settings.logFilter,"setThermostatFanSchedule>end ${endTime} is not between 0 and 24 hours",true, GLOBAL_LOG_WARN)
		return    
	}   	 
		        
	setThermostatSettings("", ["fan_duty_start_time": (startTime*3600),"fan_duty_end_time": (endTime*3600)])
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		traceEvent(settings.logFilter,"setThermostatFanSchedule>error while trying to call setThermostatFanSchedule",true, GLOBAL_LOG_WARN )
		return    
	} 
	traceEvent(settings.logFilter,"setThermostatFanSchedule>done")
    
}


void setThermostatMode(mode) {	
	mode=mode?.toLowerCase()
	mode=(mode=='range')? 'auto' :mode    
	def nestMode=(mode=='auto')?'range':mode
	def supportedThermostatModes = getSupportedThermostatModes()
    
	if (supportedThermostatModes.toString()?.contains(mode)) {
		if (mode != 'eco') {    
			setEcoMode('schedule')  // to ensure eco mode is reverted.      
			setThermostatSharedSettings("", ["target_temperature_type": "${nestMode}"])
			            
		} else {
			setEcoMode()        
		}        
		def exceptionCheck=device.currentValue("verboseTrace")
		if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
			return    
		}
		refresh_thermostat()        
		sendEvent(name: 'thermostatMode', value: mode,isStateChange:true)
	} else {   
		traceEvent(settings.logFilter,"setThermostatMode>cannot change tstat mode as $mode is not supported in $supportedThermostatModes",
			true, GLOBAL_LOG_WARN, true)
	}
}

void setEcoMode(type='manual-eco') {

	def previous_mode= device.currentValue("thermostatMode")
	def ecoMode=["eco":[mode:type]]
	setThermostatSettings("", ecoMode )
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		traceEvent(settings.logFilter,"eco>eco mode was not set, exception $exceptionCheck",
			true, GLOBAL_LOG_WARN, true)        
		return		    
	}    
	sendEvent(name: "hvac_previous_mode", value: previous_mode,displayed:(settings.trace?:false))
	if (type in ['manual-eco', 'auto-eco']) {    
		sendEvent(name: "thermostatMode", value: 'eco',displayed:(settings.trace?:false))
	}        
}



void setTargetHumidity(humidityLevel) {	
	if (!humidityLevel.isNumber()) {
    
		traceEvent(settings.logFilter,"setTargetHumidity>humidityLevel supplied is not a number, exiting...",
			true, GLOBAL_LOG_WARN, true) 
		return            
	}    
	setThermostatSettings("", ["target_humidity": humidityLevel, "target_humidity_enabled": true] )
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		traceEvent(settings.logFilter,"setTargetHumidity>target_humidity ($humidityLevel) was not set, exception $exceptionCheck",
			true, GLOBAL_LOG_WARN, true)        
		return		    
	}    
	
	sendEvent(name: 'targetHumidity', value: humidityLevel,isStateChange:true, unit: '%')
	sendEvent(name: 'target_humidity', value: humidityLevel,isStateChange:true, unit: '%')
	sendEvent(name: 'target_humidity_enabled', value: 'true',isStateChange:true)
}

// timerInMinutes, if zero then stop the boost.
void setHotWaterBoost(timerInMinutes=15) {	
	def thermostatId= determine_tstat_id()
	if (!timerInMinutes.isNumber()) {
    
		traceEvent(settings.logFilter,"setHotWaterBoost>timer in minutes ($timerInMinutes) supplied is not a number, exiting...",
			true, GLOBAL_LOG_WARN, true) 
		return            
	}   
	def has_control =device.currentValue("has_hot_water_control")
	if (has_control.toString()=='false') {
    
		traceEvent(settings.logFilter,"setHotWaterBoost>thermostat $thermostatId doesn't seem to have hot water control, exiting...",
			true, GLOBAL_LOG_WARN, true) 
		return            
    
	}    
	boolean hotWaterActive= hotWaterActive()
	if (hotWaterActive && timerInMinutes !=0) {
		traceEvent(settings.logFilter,"setHotWaterBoost>hot water boost is already active",
			true, GLOBAL_LOG_WARN, true)
		return
	}    
	long timeout =0, timer_timeout_for_nest=0
	if (timerInMinutes) {    
		timeout = now() + (timerInMinutes * 60 * 1000)
		timer_timeout_for_nest= timeout / 1000 //(epoch in secs)        
		hotWaterActive=true		
		traceEvent(settings.logFilter,"setHotWaterBoost>fan_timer_duration = ${timeout} in epoch millisec=$timeout, timer_timeout_for_nest in epoch secs: ${timer_timeout_for_nest}")
		setThermostatSettings("", ["hot_water_boost_time_to_end": timer_timeout_for_nest])
        
	} else {
		setThermostatSettings("", ["hot_water_boost_time_to_end":0])
		hotWaterActive=false		    
	}    
    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		traceEvent(settings.logFilter,"setHotWaterBoost>hot water boost for $timerInMinutes minutes is not set, exception $exceptionCheck",
			true, GLOBAL_LOG_WARN, true)        
		return		    
	}    
	sendEvent(name: 'hot_water_boost_time_to_end', value:timer_timeout_for_nest.toString(), isStateChange:true)
	if ( timer_timeout_for_nest) {
			def hot_water_timeout_string=formatTimeInTimezone(timeout)?.substring(0,16)
			sendEvent(name: 'hot_water_boost_time_to_end_s', value:hot_water_timeout_string, isStateChange:true)    
	} else {
 			sendEvent(name: 'hot_water_boost_time_to_end_s', value:'None', isStateChange:true)    
   
	}    
	sendEvent(name: 'hot_water_active', value: hotWaterActive.toString(),isStateChange:true)
}

void present() {
	setStructureHome()
	home()    
}
void away() {
	setStructureAway()
	eco()    
}
void home() {
	setEcoMode('schedule')    
	def previousMode=getPreviousHvacMode()
	if (previousMode) {    
		setThermostatMode(previousMode)
		def exceptionCheck=device.currentValue("verboseTrace")
		if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
			traceEvent(settings.logFilter,"home>cannot change tstat mode to $previousMode",
				true, GLOBAL_LOG_ERROR, true)
		}
	}        
    
}
void setStructureAway() {

	double away_timestamp=(now() / 1000) // epoch in seconds
	long timestamp= (double)away_timestamp.intValue()    
	traceEvent(settings.logFilter,"setStructureAway>away_timestamp=$away_timestamp, timestamp=$timestamp", settings.trace)
	setStructure([away: true, away_setter:0, away_timestamp: away_timestamp.intValue()])
	sendEvent(name: 'homeOrAway', value: 'Away',isStateChange:true)
	    

}

void setHomeOrAway(String awayOrHome) {
	if (awayOrHome.toUpperCase() == 'AWAY') {
		away()    
	} else {
		present()    
	}    
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

void setHeatingSetpointRange(rangeArray=[]) {

	if ((!rangeArray) || (rangeArray.size()!=2)) {
		traceEvent(settings.logFilter,"setHeatingSetpointRange>cannot change the thermostat Range, value ($rangeArray) is null or invalid",settings.trace, GLOBAL_LOG_WARN,true)
		return    
	}    
	def temp_min=rangeArray[0]
	def temp_max=rangeArray[1]	
	setHeatingSetpointRangeLow(temp_min)    
	setHeatingSetpointRangeHigh(temp_max)    
}

void setCoolingSetpointRange(rangeArray=[]) {

	if ((!rangeArray) || (rangeArray.size()!=2)) {
		traceEvent(settings.logFilter,"setHeatingSetpointRange>cannot change the thermostat Range, value ($rangeArray) is null or invalid",settings.trace,GLOBAL_LOG_WARN)
		return    
	}    

	def temp_min=rangeArray[0]	
	def temp_max=rangeArray[1]	
	setCoolingSetpointRangeLow(temp_min)    
	setCoolingSetpointRangeHigh(temp_max)    
}


void setHeatingSetpointRangeLow(temp) {
	def scale = state?.scale
	double targetTemp    
	boolean isLocked= isRangeLowLocked()   
	def currentMode=device.currentValue("thermostatMode")
	def attributes=[]
    
	if (scale == 'C') {
		targetTemp = temp.toDouble().round(1)  
		if (currentMode != 'eco') {    
			 attributes=["lower_safety_temp": targetTemp,"lower_safety_temp_enabled": true]
		} else {
			 attributes=["away_temperature_low": targetTemp,"away_temperature_low_enabled": true]
		}        
		setThermostatSettings("", attributes)
	} else {
		targetTemp = fToC(temp)
		if (currentMode != 'eco') {    
			 attributes=["lower_safety_temp": targetTemp,"lower_safety_temp_enabled": true]
		} else {
			 attributes=["away_temperature_low": targetTemp,"away_temperature_low_enabled": true]
		}        
		setThermostatSettings("", attributes)
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		return    
	}    
	sendEvent(name: 'heatingSetpointRangeLow', value: temp.toString(), isStateChange:true,unit:scale)
	def setpointRangeHigh=device.currentValue('heatingSetpointRangeHigh')
//	String heatingSetpointRange= "$temp,$setpointRangeHigh"
	def heatingSetpointRange= [temp,setpointRangeHigh]
	sendEvent(name: "heatingSetpointRange", value: heatingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)

	sendEvent(name: 'coolingSetpointRangeLow', value: temp.toString(), isStateChange:true,unit:scale)
	setpointRangeHigh=device.currentValue('coolingSetpointRangeHigh')
//	String heatingSetpointRange= "$temp,$setpointRangeHigh"
	def coolingSetpointRange= [temp,setpointRangeHigh]
	sendEvent(name: "coolingSetpointRange", value: coolingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)
}

void setHeatingSetpointRangeHigh(temp) {
	def scale = state?.scale
	double targetTemp    
	boolean isLocked= isRangeHighLocked()   
	def currentMode=device.currentValue("thermostatMode")
	def attributes=[]
    
    
	if (scale == 'C') {
		targetTemp = temp.toDouble().round(1)  
		if (currentMode != 'eco') {  
			attributes= ["upper_safety_temp": targetTemp,"upper_safety_temp_enabled": true]       
		} else {
			attributes= ["away_temperature_high": targetTemp, "away_temperature_high_enabled": true]       
		}        
		setThermostatSettings("", attributes)
	} else {
		targetTemp = fToC(temp)
		if (currentMode != 'eco') {  
			attributes= ["upper_safety_temp": targetTemp,"upper_safety_temp_enabled": true]       
		} else {
			attributes= ["away_temperature_high": targetTemp, "away_temperature_high_enabled": true]       
		}        
		setThermostatSettings("", attributes)
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		return    
	}    
    
	sendEvent(name: 'heatingSetpointRangeHigh', value: temp.toString(), isStateChange:true,unit:scale)
	def setpointRangeLow=device.currentValue('heatingSetpointRangeLow')
	def heatingSetpointRange= [setpointRangeLow,temp]
	sendEvent(name: "heatingSetpointRange", value: heatingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)

	sendEvent(name: 'coolingSetpointRangeHigh', value: temp.toString(), isStateChange:true,unit:scale)
	setpointRangeLow=device.currentValue('coolingSetpointRangeLow')
	def coolingSetpointRange= [setpointRangeLow,temp]
	sendEvent(name: "coolingSetpointRange", value: coolingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)
}


void setCoolingSetpointRangeLow(temp) {
	def scale = state?.scale
	double targetTemp    
	boolean isLocked= isRangeLowLocked()   
	def currentMode=device.currentValue("thermostatMode")
	def attributes=[]

  
	if (scale == 'C') {
		targetTemp = temp.toDouble().round(1)  
		if (currentMode != 'eco') {    
			attributes =["lower_safety_temp": targetTemp,"lower_safety_temp_enabled": true]
		} else {
			attributes= ["away_temperature_low": targetTemp, "away_temperature_low_enabled": true]
		}        
		setThermostatSettings("", attributes)
	} else {
		targetTemp = fToC(temp)
		if (currentMode != 'eco') {    
			attributes=["lower_safety_temp": targetTemp,"lower_safety_temp_enabled": true]
		} else {
			attributes=["away_temperature_low": targetTemp, "away_temperature_low_enabled":true]
		}  
		setThermostatSettings("", attributes)
        
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		return    
	}    
    
	sendEvent(name: 'coolingSetpointRangeLow', value: temp.toString(), isStateChange:true,unit:scale)
	def setpointRangeHigh=device.currentValue('coolingSetpointRangeHigh')
//	String coolingSetpointRange= "$temp,$setpointRangeHigh"
	def coolingSetpointRange= [temp,setpointRangeHigh]
	sendEvent(name: "coolingSetpointRange", value: coolingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)
    
	sendEvent(name: 'heatingSetpointRangeLow', value: temp.toString(), isStateChange:true,unit:scale)
	setpointRangeHigh=device.currentValue('heatingSetpointRangeHigh')
//	String coolingSetpointRange= "$temp,$setpointRangeHigh"
	def heatingSetpointRange= [temp,setpointRangeHigh]
	sendEvent(name: "heatingSetpointRange", value: heatingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)

}

void setCoolingSetpointRangeHigh(temp) {
	def scale = state?.scale
	double targetTemp    
	boolean isLocked= isRangeHighLocked()   
	def currentMode=device.currentValue("thermostatMode")
	def attributes=[]

	if (scale == 'C') {
		targetTemp = temp.toDouble().round(1)  
		if (currentMode != 'eco') {    
			attributes= ["upper_safety_temp": targetTemp, "upper_safety_temp_enabled": true]
		} else {
			attributes= ["away_temperature_high": targetTemp, "away_temperature_high_enabled":true]
		}        
		setThermostatSettings("", attributes)
	} else {
		targetTemp = fToC(temp)
		if (currentMode != 'eco') {    
			attributes=["upper_safety_temp": targetTemp,"upper_safety_temp_enabled": true]
		} else {
			attributes= ["away_temperature_high": targetTemp, "away_temperature_high_enabled":true]
		}        
		setThermostatSettings("", attributes)
	}    
	def exceptionCheck=device.currentValue("verboseTrace")
	if ((exceptionCheck) && (!exceptionCheck?.contains("done"))) {
		return    
	}       
	sendEvent(name: 'coolingSetpointRangeHigh', value: temp.toString(), isStateChange:true,unit:scale)
	def setpointRangeLow=device.currentValue('coolingSetpointRangeLow')
//	String coolingSetpointRange= "$setpointRangeLow,$temp"
	def coolingSetpointRange= [setpointRangeLow,temp]
	sendEvent(name: "coolingSetpointRange", value: coolingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)

	sendEvent(name: 'heatingSetpointRangeHigh', value: temp.toString(), isStateChange:true,unit:scale)
	setpointRangeLow=device.currentValue('heatingSetpointRangeLow')
//	String coolingSetpointRange= "$setpointRangeLow,$temp"
	def heatingSetpointRange= [setpointRangeLow,temp]
	sendEvent(name: "heatingSetpointRange", value: heatingSetpointRange, isStateChange: true, displayed: (settings.trace?:false),unit:scale)
    
}

void setThermostatLabel(value) {
	setThermostatSettings("", ["label": value])

	sendEvent(name: 'label', value: value, isStateChange:true)
    
    
}


void setTemperatureScale(value) {
	value=value.toUpperCase()
	value=(value=='C')? 'C':'F'    
	def scale = state?.scale
	if (scale != value) {
		traceEvent(settings.logFilter,"setTemperatureScale>cannot change the thermostat scale to $value as the SmartThings scale is different ($scale), please change your ST scale first",
			settings.trace,GLOBAL_LOG_ERROR,true)
		return    
	}    
	setThermostatSettings("", ["temperature_scale": value])
	sendEvent(name: 'tstat_scale', value: value, isStateChange:true)
}

// parse events into attributes
def parse(String description) {

}

// thermostatId		single thermostatId 

private def refresh_thermostat(thermostatId="", initial_call=false) {
	def structure
	thermostatId=determine_tstat_id(thermostatId) 
    
	def scale = getTemperatureScale()
	def tstat_scale = data?.thermostats[0]?.temperature_scale
	state?.scale= tstat_scale
	if (tstat_scale != scale) {
    
		traceEvent(settings.logFilter,"refresh_thermostat>your SmartThings scale ($scale) is different from your Nest Tstat's scale ($tstat_scale), please correct it",settings.trace, GLOBAL_LOG_WARN)
		scale=tstat_scale        
    
	}    
	def todayDay = new Date().format("dd",location.timeZone)
	def timeToday= new Date().format("HH:mm",location.timeZone)
    
  	if ((!state?.today) || (state?.today != todayDay))  {
		traceEvent(settings.logFilter,"refresh_thermostat>about to call parent.getStructures(), type=where", detailedNotif)
		parent.getStructures(false, cache_timeout,'["where"]')
		traceEvent(settings.logFilter,"refresh_thermostat>about to call parent.getStructures(), type=buckets", detailedNotif)
		parent.getStructures(false, cache_timeout,'["buckets"]')
		state?.today=todayDay        
	}  
	if (!data?.thermostats) {
		data?.thermostats=[]
	}        

	String currentMode= data?.thermostats[0]?.target_temperature_type

	if (data?.thermostats[0]?.eco_mode in ['manual-eco','auto-eco']) {
		currentMode= 'eco'
	}

    
	traceEvent(settings.logFilter,"refresh_thermostat>thermostatMode == $currentMode", detailedNotif)
	currentMode = (currentMode)? currentMode.toLowerCase(): 'off'  
	def heatingSetpointRangeHigh= (scale=='C') ? 30.0 :99  
	def heatingSetpointRangeLow= (scale=='C') ? 10.0 : 50
	def coolingSetpointRangeHigh=(scale=='C') ? 30.0 :99  
	def coolingSetpointRangeLow=(scale=='C') ? 10.0 : 50
	double coolingSetpoint, heatingSetpoint,temperature

	if (currentMode in 'range') { 
		heatingSetpointRangeHigh= (scale=='C') ? 30.0:99  
		heatingSetpointRangeLow= (scale=='C') ? 10.0 : 50
		coolingSetpointRangeHigh= (scale=='C') ? 30.0 :99  
		coolingSetpointRangeLow= (scale=='C') ? 10.0 : 50
		heatingSetpoint=(scale=='C')?(data?.thermostats[0]?.target_temperature_low):(cToF(data?.thermostats[0]?.target_temperature_low))
		coolingSetpoint=(scale=='C')?(data?.thermostats[0]?.target_temperature_high):(cToF(data?.thermostats[0]?.target_temperature_high))      
	}  
	if (currentMode in ['heat', 'cool', 'off']) { 
		heatingSetpointRangeHigh= (scale=='C') ? 30.0 :99  
		heatingSetpointRangeLow= (scale=='C') ? 10.0 : 50
		coolingSetpointRangeHigh= (scale=='C') ? 30.0 :99  
		coolingSetpointRangeLow= (scale=='C') ? 10.0 : 50
		heatingSetpoint=(scale=='C')?(data?.thermostats[0]?.target_temperature):(cToF(data?.thermostats[0]?.target_temperature))
		coolingSetpoint=(scale=='C')?(data?.thermostats[0]?.target_temperature):(cToF(data?.thermostats[0]?.target_temperature))       
	}  
	if (currentMode in 'eco') { 
		heatingSetpointRangeHigh= (scale=='C') ? 30.0 :99  
		heatingSetpointRangeLow= (scale=='C') ? 10.0 : 50
		coolingSetpointRangeHigh= (scale=='C') ? 30.0 :99  
		coolingSetpointRangeLow= (scale=='C') ? 10.0 : 50
		heatingSetpoint=(scale=='C')?(data?.thermostats[0]?.eco_temperature_low):(cToF(data?.thermostats[0]?.eco_temperature_low))
		coolingSetpoint=(scale=='C')?(data?.thermostats[0]?.eco_temperature_high):(cToF(data?.thermostats[0]?.eco_temperature_high))       
	}    


	if (data?.thermostats[0]?.upper_safety_temp_enabled) {
		heatingSetpointRangeHigh= (scale=='C') ? data?.thermostats[0]?.upper_safety_temp : cToF_Rounded(data?.thermostats[0]?.upper_safety_temp )
		coolingSetpointRangeHigh= (scale=='C') ? data?.thermostats[0]?.upper_safety_temp : cToF_Rounded(data?.thermostats[0]?.upper_safety_temp )
 	}    

	if (data?.thermostats[0]?.lower_safety_temp_enabled) {
		heatingSetpointRangeLow= (scale=='C') ? data?.thermostats[0]?.lower_safety_temp : cToF_Rounded(data?.thermostats[0]?.lower_safety_temp )
		coolingSetpointRangeLow= (scale=='C') ? data?.thermostats[0]?.lower_safety_temp : cToF_Rounded(data?.thermostats[0]?.lower_safety_temp )
 	}    

	if ((data?.thermostats[0]?.away_temperature_enabled) && (currentMode=='eco')) {
		heatingSetpointRangeLow= (scale=='C') ? data?.thermostats[0]?.away_temperature_low : cToF_Rounded(data?.thermostats[0]?.away_temperature_low )
		coolingSetpointRangeLow= (scale=='C') ? data?.thermostats[0]?.away_temperature_low : cToF_Rounded(data?.thermostats[0]?.away_temperature_low )
		heatingSetpointRangeHigh= (scale=='C') ? data?.thermostats[0]?.away_temperature_high : cToF_Rounded(data?.thermostats[0]?.away_temperature_high )
		coolingSetpointRangeHigh= (scale=='C') ? data?.thermostats[0]?.away_temperature_high : cToF_Rounded(data?.thermostats[0]?.away_temperature_high)

	}
	if (data?.thermostats[0]?.temperature_lock) { 
		heatingSetpointRangeHigh= (scale=='C')? (data?.thermostats[0]?.temperature_lock_high_temp):(cToF_Rounded(data?.thermostats[0]?.temperature_lock_high_temp))    
		heatingSetpointRangeLow= (scale=='C')? (data?.thermostats[0]?.temperature_lock_low_temp):(cToF_Rounded(data?.thermostats[0]?.temperature_lock_low_temp))
		coolingSetpointRangeHigh= (scale=='C')? (data?.thermostats[0]?.temperature_lock_high_temp):(cToF_Rounded(data?.thermostats[0]?.temperature_lock_high_temp))    
		coolingSetpointRangeLow= (scale=='C')? (data?.thermostats[0]?.temperature_lock_low_temp):(cToF_Rounded(data?.thermostats[0]?.temperature_lock_low_temp))
		heatingSetpoint=(scale=='C')? (data?.thermostats[0]?.temperature_lock_low_temp):(cToF(data?.thermostats[0]?.temperature_lock_low_temp))
		coolingSetpoint=(scale=='C')? (data?.thermostats[0]?.temperature_lock_high_temp):(cToF(data?.thermostats[0]?.temperature_lock_high_temp))       
	}    
	traceEvent(settings.logFilter,"refresh_thermostat>heatingSetpointRangeHigh=$heatingSetpointRangeHigh",settings.trace)
	traceEvent(settings.logFilter,"refresh_thermostat>heatingSetpointRangeLow=$heatingSetpointRangeLow",settings.trace)
	traceEvent(settings.logFilter,"refresh_thermostat>coolingSetpointRangeHigh=$coolingSetpointRangeHigh",settings.trace)
	traceEvent(settings.logFilter,"refresh_thermostat>coolingSetpointRangeLow=$coolingSetpointRangeLow",settings.trace)

	def currentProgramScheduleName=device.currentValue("programScheduleName")
   
	def currentHeatSP// =device.currentValue("heatingSetpoint")
	def currentCoolSP// = device.currentValue("coolingSetpoint") 
	if (!initial_call) {
		currentHeatSP = device.currentValue("heatingSetpoint")
		currentCoolSP =device.currentValue("coolingSetpoint") 
    
	}    
	String thermostatSetpointString, heatingSetpointString,coolingSetpointString
   
	heatingSetpoint = (heatingSetpoint) ? ((scale=='C') ? heatingSetpoint?.toDouble().round(1) : heatingSetpoint?.toDouble().round())  : (scale=='C') ? 21.0:72 
	coolingSetpoint = (coolingSetpoint) ? ((scale=='C') ? coolingSetpoint?.toDouble().round(1) : coolingSetpoint?.toDouble().round()) : (scale=='C') ? 21.0:72
	currentHeatSP = (currentHeatSP!= null)? currentHeatSP.toFloat() : heatingSetpoint
	currentCoolSP = (currentCoolSP!= null)? currentCoolSP.toFloat() : coolingSetpoint
    
	traceEvent(settings.logFilter,"refresh_thermostat>currentHeatSP=$currentHeatSP,newHeatSP=$heatingSetpoint",settings.trace)
	traceEvent(settings.logFilter,"refresh_thermostat>currentCoolSP=$currentCoolSP,newCoolSP=$coolingSetpoint",settings.trace)
	if (scale == "C") {
		coolingSetpointString = String.format('%2.1f',coolingSetpoint)
		heatingSetpointString = String.format('%2.1f',heatingSetpoint)
		temperature=data?.thermostats[0]?.current_temperature        
	} else {
		coolingSetpointString= String.format('%2d', coolingSetpoint.intValue())            
		heatingSetpointString= String.format('%2d', heatingSetpoint.intValue())            
		temperature= cToF_Rounded(data?.thermostats[0]?.current_temperature)       
	}
	long fan_timeout=data?.thermostats[0]?.fan_timer_timeout
	def dataEvents = [
		"structure_id": data?.thermostats[0]?.structure_id,
		thermostatMode:(currentMode != 'range') ? currentMode : 'auto',
		temperature: temperature,
		coolingSetpoint:  coolingSetpoint,
		coolingSetpointDisplay: coolingSetpointString, 
		heatingSetpoint: heatingSetpoint, 
		heatingSetpointDisplay: heatingSetpointString, 
		heatingSetpointRangeHigh: heatingSetpointRangeHigh,       
		heatingSetpointRangeLow:  heatingSetpointRangeLow,        
		coolingSetpointRangeHigh: coolingSetpointRangeHigh,        
		coolingSetpointRangeLow:  coolingSetpointRangeLow,
		"fan_timer_timeout":  fan_timeout.toString(),
		"has_fan":  data?.thermostats[0]?.has_fan.toString(),
		"hvac_fan_state":  data?.thermostats[0]?.hvac_fan_state.toString(),       
		"fan_duty_start_time":  data?.thermostats[0]?.fan_duty_start_time, 
		"fan_duty_end_time":  data?.thermostats[0]?.fan_duty_end_time,
	]
	generateEvent(dataEvents)   
    
	if ((timeToday.endsWith("00") || timeToday.endsWith("15")  || timeToday.endsWith("30") || timeToday.endsWith("45")) &&
		((heatingSetpoint && currentHeatSP != heatingSetpoint) || (coolingSetpoint && currentCoolSP != coolingSetpoint))) {
		    
		sendEvent(name:"programScheduleName", value: 'schedule', displayed:((settings.trace) ?:false), isStateChange:true)
		traceEvent(settings.logFilter,"refresh_thermostat>schedule change detected, currentHeatSP=$currentHeatSP,newHeatSP=$heatingSetpoint,currentCoolSP=$currentCoolSP,newCoolSP=$coolingSetpoint",
			settings.trace)
	} else {   
		if ((currentMode =='cool') && 
			(coolingSetpoint && currentCoolSP != coolingSetpoint)) { // force the hold or auto event when in 'cool' mode and coolSP has changed manually
			sendEvent(name:"programScheduleName", value: 'hold', displayed:((settings.trace) ?:false), isStateChange:true)
			traceEvent(settings.logFilter,"refresh_thermostat>hold detected, currentCoolSP=$currentCoolSP,newCoolSP=$coolingSetpoint",settings.trace)
		}	
		if ((currentMode == 'heat') && 
			(heatingSetpoint && currentHeatSP != heatingSetpoint )) { // force the hold or auto event when in 'heat' mode and heatSP has changed manually
			sendEvent(name:"programScheduleName", value: 'hold', displayed:((settings.trace) ?:false), isStateChange:true)
			traceEvent(settings.logFilter,"refresh_thermostat>hold detected, currentHeatSP=$currentHeatSP,newHeatSP=$heatingSetpoint",settings.trace)
		}	
		if ((currentMode == 'range') && 
			((heatingSetpoint && currentHeatSP != heatingSetpoint) || (coolingSetpoint && currentCoolSP != coolingSetpoint))) { // force the 'hold' or 'auto' event in 'auto' mode when heatSP or coolSP has changed manually
			sendEvent(name:"programScheduleName", value: 'hold', displayed:((settings.trace) ?:false), isStateChange:true)
			traceEvent(settings.logFilter,"refresh_thermostat>hold detected, currentHeatSP=$currentHeatSP,newHeatSP=$heatingSetpoint,currentCoolSP=$currentCoolSP,newCoolSP=$coolingSetpoint",
				settings.trace)
		}	
	}	
        
    
	if (currentMode in ['range', 'eco', 'off']) {  
		double medianPoint= ((heatingSetpoint + coolingSetpoint)/2).toDouble()
		if (scale == "C") {
			thermostatSetpointString = String.format('%2.1f', medianPoint)
		} else {
			thermostatSetpointString= String.format('%2d', medianPoint.intValue())            
		}
	}        
	if (currentMode in ['heat']) {  
		if (scale == "C") {
			thermostatSetpointString = String.format('%2.1f',heatingSetpoint)
		} else {
			thermostatSetpointString= String.format('%2d', heatingSetpoint.intValue())            
		}
	}    
	if (currentMode=='cool') {  
		if (scale == "C") {
			thermostatSetpointString = String.format('%2.1f',coolingSetpoint)
		} else {
			thermostatSetpointString= String.format('%2d', coolingSetpoint.intValue())            
		}
	}    
    
	def supportedThermostatModes=['off', 'eco']
	if (data?.thermostats[0]?.can_cool?.toString()=='true') { 
		supportedThermostatModes << 'cool'
	}    

	if (data?.thermostats[0]?.can_heat?.toString()=='true') { 
		supportedThermostatModes << 'heat' 
	}    
	if (data?.thermostats[0]?.emer_heat_enabled?.toString()=='true') { 
		supportedThermostatModes <<  'emergency heat' 
	}    
	if (data?.thermostats[0]?.has_aux_heat?.toString()=='true') { 
		supportedThermostatModes << 'auxHeatOnly' 
	}    
   
  
	if ((data?.thermostats[0]?.can_heat?.toString()=='true') && (data?.thermostats[0]?.can_cool?.toString()=='true')) {
		supportedThermostatModes << 'auto' 
	}        
	def supportedThermostatFanModes=[]
	if (hasFan()) {
		supportedThermostatFanModes << 'auto' << 'circulate' << 'off' << 'on'    
	}        
    
	def name = (data?.thermostats[0]?.name) ? data?.thermostats[0]?.name.toString() : data?.thermostats[0]?.long_name.toString()
 	def fan_mode=fanActive() ? 'on': 'off' 
	dataEvents = [
		thermostatId:  data?.thermostats[0]?.id.toString(),
 		thermostatName:name,
		thermostatOperatingState: getThermostatOperatingState(),
		humidity:data?.thermostats[0]?.humidity,
		thermostatSetpoint:thermostatSetpointString,
		thermostatFanMode:fan_mode,        
		"locale": data?.thermostats[0]?.device_locale,
		"tstat_scale": data?.thermostats[0]?.temperature_scale,
		"is_using_emergency_heat": data?.thermostats[0]?.hvac_emer_heat_state,
		"software_version": data?.thermostats[0]?.current_version,
		"has_leaf":data?.thermostats[0]?.leaf.toString(),
		"where_id": data?.thermostats[0]?.where_id,
		"can_heat": data?.thermostats[0]?.can_heat.toString(),
		"can_cool":data?.thermostats[0]?.can_cool.toString(),
		"target_temperature_f": cToF_Rounded(data?.thermostats[0]?.target_temperature),
		"target_temperature_c": data?.thermostats[0]?.target_temperature,
		"target_temperature_high_f":  cToF_Rounded(data?.thermostats[0]?.target_temperature_high),
		"target_temperature_high_c": data?.thermostats[0]?.target_temperature_high,
		"target_temperature_low_f": cToF_Rounded(data?.thermostats[0]?.target_temperature_low),
		"target_temperature_low_c": data?.thermostats[0]?.target_temperature_low,
		"multiroom_active": data?.thermostats[0]?.multiroom_active,
		"multiroom_temperature_c": data?.thermostats[0]?.multiroom_temperature,
		"multiroom_temperature_f": cToF_Rounded(data?.thermostats[0]?.multiroom_temperature),
//		"ambient_temperature_c":  data?.thermostats[0]?.temperature,
//		"ambient_temperature_f":  cToF_Rounded(data?.thermostats[0]?.temperature),
		"eco_temperature_high_f": cToF_Rounded( data?.thermostats[0]?.eco_temperature_high),
		"eco_temperature_high_c":  data?.thermostats[0]?.eco_temperature_high,
		"eco_temperature_low_f":  cToF_Rounded(data?.thermostats[0]?.eco_temperature_low),
		"eco_temperature_low_c": data?.thermostats[0]?.eco_temperature_low,
		"is_locked": data?.thermostats[0]?.temperature_lock.toString(),
		"locked_temp_max_f": cToF_Rounded(data?.thermostats[0]?.temperature_lock_high_temp),
		"locked_temp_max_c": data?.thermostats[0]?.temperature_lock_high_temp,
		"locked_temp_min_f": cToF_Rounded(data?.thermostats[0]?.temperature_lock_low_temp),
		"locked_temp_min_c":  data?.thermostats[0]?.temperature_lock_low_temp,
		"sunlight_correction_active": data?.thermostats[0]?.sunlight_correction_active,
		"sunlight_correction_ready":  data?.thermostats[0]?.sunlight_correction_ready,
		"fan_duty_cycle":  data?.thermostats[0]?.fan_duty_cycle, 
		"fan_timer_timeout_string":  (data?.thermostats[0]?.fan_timer_timeout) ? 
			formatTimeInTimezone(data?.thermostats[0]?.fan_timer_timeout*1000)?.substring(0,16) : "None",
		"previous_hvac_mode": data?.thermostats[0]?.target_temperature_type,
		"hvac_mode":  data?.thermostats[0]?.mode,
		"time_to_target":  data?.thermostats[0]?.time_to_target,
		"time_to_target_training": data?.thermostats[0]?.time_to_target_training,
		"where_name": data?.thermostats[0]?.where_name,
//		"label": data?.thermostats[0]?.label,
		"name_long":data?.thermostats[0]?.long_name,
		"is_online": data?.thermostats[0]?.is_online.toString(),
		"last_connection": (data?.thermostats[0]?.last_connection)? formatTimeInTimezone((data?.thermostats[0]?.last_connection))?.substring(0,16):"",
		"last_api_check": formatTimeInTimezone(now())?.substring(0,16),
//		"hvac_state": data?.thermostats[0]?.hvac_state,
		"away_temperature_enabled": data?.thermostats[0]?.away_temperature_enabled,
		"away_temperature_high_c":  data?.thermostats[0]?.away_temperature_high,
		"away_temperature_high_f": cToF_Rounded(data?.thermostats[0]?.away_temperature_high), 
 		"away_temperature_low_c": data?.thermostats[0]?.away_temperature_low, 
		"away_temperature_low_f": cToF_Rounded(data?.thermostats[0]?.away_temperature_low), 
		"eco_mode": data?.thermostats[0]?.eco_mode,
		"hvac_ac_state": data?.thermostats[0]?.hvac_ac_state,
		"hvac_heater_state": data?.thermostats[0]?.hvac_heater_state,
 		"auto_away": (data?.thermostats[0]?.auto_away>0)? 'true': 'false',
 		"auto_away_enabled": data?.thermostats[0]?.auto_away_enabled,
 		"auto_away_reset_enabled": data?.thermostats[0]?.auto_away_reset_enabled,
 		"auto_away_learning": data?.thermostats[0]?.auto_away_learning,
		"hvac_cool_x2_state": data?.thermostats[0]?.hvac_cool_x2_state,
		"hvac_cool_x3_state": data?.thermostats[0]?.hvac_cool_x3_state,
		"hvac_heat_x2_state": data?.thermostats[0]?.hvac_heat_x2_state,
		"hvac_heat_x3_state": data?.thermostats[0]?.hvac_heat_x3_state,
		"hvac_aux_heater_state": data?.thermostats[0]?.hvac_aux_heater_state,
		"hvac_aux_heat_state": data?.thermostats[0]?.hvac_aux_heat_state, 
		"hvac_emer_heat_state": data?.thermostats[0]?.hvac_emer_heat_state,
//		"backplate_temperature": data?.thermostats[0]?.backplate_temperature,
		"battery_level": data?.thermostats[0]?.battery_level,
		"has_humidifier": data?.thermostats[0]?.has_humidifier,
		"has_dehumidifier": data?.thermostats[0]?.has_dehumidifier,
		"emer_heat_delivery":data?.thermostats[0]?.emer_heat_delivery,
		"emer_heat_source": data?.thermostats[0]?.emer_heat_source,
		"emer_heat_enabled": data?.thermostats[0]?.emer_heat_enabled,
		"cooling_source": data?.thermostats[0]?.cooling_source,
		"cooling_x2_source": data?.thermostats[0]?.cooling_x2_source,
		"cooling_x3_source": data?.thermostats[0]?.cooling_x3_source,
		"heater_delivery": data?.thermostats[0]?.heater_delivery,
		"cooling_delivery:": data?.thermostats[0]?.cooling_delivery,
		"alt_heat_delivery": data?.thermostats[0]?.alt_heat_delivery,
		"aux_heat_delivery": data?.thermostats[0]?.aux_heat_delivery,
		"heat_x2_delivery": data?.thermostats[0]?.heat_x2_delivery,
		"cooling_x2_delivery": data?.thermostats[0]?.cooling_x2_delivery,
		"leaf_threshold_cool_f":  cToF_Rounded(data?.thermostats[0]?.leaf_threshold_cool), 
		"leaf_threshold_cool_c": data?.thermostats[0]?.leaf_threshold_cool, 
		"leaf_threshold_heat_f":  cToF_Rounded(data?.thermostats[0]?.leaf_threshold_heat), 
		"leaf_threshold_heat_c":  data?.thermostats[0]?.leaf_threshold_heat, 
		"heat_pump_comp_threshold_enabled": data?.thermostats[0]?.heat_pump_comp_threshold_enabled,
		"heat_pump_aux_threshold_enabled": data?.thermostats[0]?.heat_pump_aux_threshold_enabled,
		"heat_pump_aux_threshold_c":  cToF_Rounded(data?.thermostats[0]?.heat_pump_aux_threshold),
		"heat_pump_aux_threshold_f": data?.thermostats[0]?.heat_pump_aux_threshold,
		"heat_pump_comp_threshold_f": cToF_Rounded(data?.thermostats[0]?.heat_pump_comp_threshold),
		"heat_pump_comp_threshold_c": data?.thermostats[0]?.heat_pump_comp_threshold,
		"compressor_lockout_enabled":  data?.thermostats[0]?.compressor_lockout_enabled, 
		"compressor_lockout_leaf_f":  cToF_Rounded(data?.thermostats[0]?.compressor_lockout_leaf), 
		"compressor_lockout_leaf_c":  data?.thermostats[0]?.compressor_lockout_leaf, 
		"has_dual_fuel":  data?.thermostats[0]?.has_dual_fuel, 
		"dual_fuel_breakpoint_f":  cToF_Rounded(data?.thermostats[0]?.dual_fuel_breakpoint), 
		"dual_fuel_breakpoint_c":  data?.thermostats[0]?.dual_fuel_breakpoint, 
		"dual_fuel_override":data?.thermostats[0]?.dual_fuel_breakpoint_override,
		"error_code": data?.thermostats[0]?.error_code,
	 	"fan_current_modulation": data?.thermostats[0]?.fan_current_speed,
 		"fan_mode": data?.thermostats[0]?.fan_mode,
 		"fan_capabilities": data?.thermostats[0]?.fan_capabilities,
	 	"equipment_type": data?.thermostats[0]?.equipment_type,
 		"target_humidity": data?.thermostats[0]?.target_humidity,
 		"targetHumidity": data?.thermostats[0]?.target_humidity,
 		"target_hum_enabled": data?.thermostats[0]?.target_humidity_enabled,
	 	"auto_dehum_enabled":data?.thermostats[0]?.auto_dehum_enabled,
 		"auto_dehum_state": data?.thermostats[0]?.auto_dehum_state,
		"dehumidifier_fan_activation_enabled":data?.thermostats[0]?.dehumidifier_fan_activation,
 		"humidifier_state": data?.thermostats[0]?.humidifier_state,
	 	"humidifier_type": data?.thermostats[0]?.humidifier_type,
		"humidifier_fan_activation_enabled":data?.thermostats[0]?.humidifier_fan_activation,
		"has_hot_water_control": data?.thermostats[0]?.has_hot_water_control,
	 	"target_change_pending": data?.thermostats[0]?.target_change_pending,
		"alt_heat_source": data?.thermostats[0]?.alt_heat_source,
		"has_aux_heat": data?.thermostats[0]?.has_aux_heat,
		"aux_heat_source": data?.thermostats[0]?.aux_heat_source,
		"emer_heat_source": data?.thermostats[0]?.emer_heat_source,
		"emer_heat_delivery": data?.thermostats[0]?.emer_heat_delivery,
		"heat_pump_aux_threshold": data?.thermostats[0]?.heat_pump_aux_threshold,
		"has_x2_heat": data?.thermostats[0]?.has_x2_heat,
		"has_x3_heat": data?.thermostats[0]?.has_x3_heat, 
		"has_x2_cool": data?.thermostats[0]?.has_x2_cool,
		"has_x3_cool": data?.thermostats[0]?.has_x3_cool,
		"has_water_temperature_enabled": data?.thermostats[0]?.has_water_temperature,
		"heat_pump_comp_threshold": data?.thermostats[0]?.heat_pump_comp_threshold,
		"heat_pump_savings":data?.thermostats[0]?.heat_pump_savings, 
		"heatpump_setback_active": data?.thermostats[0]?.heatpump_setback_active,
		"hot_water_active": data?.thermostats[0]?.hot_water_active,
		"hot_water_away_active": data?.thermostats[0]?.hot_water_away_active,
		"preconditioning_active": data?.thermostats[0]?.preconditioning_active,
		"preconditioning_enabled": data?.thermostats[0]?.preconditioning_enabled,
		"hot_water_boiling_state": data?.thermostats[0]?.hot_water_boiling_state,
		"heater_delivery": data?.thermostats[0]?.heater_delivery,
		"learning_state": data?.thermostats[0]?.learning_state,
		"hvac_smoke_safety_shutoff_active": data?.thermostats[0]?.hvac_smoke_safety_shutoff_active,
		"lower_safety_temp_enabled": data?.thermostats[0]?.lower_safety_temp_enabled,
		"lower_safety_temp_c": (data?.thermostats[0]?.lower_safety_temp_enabled) ? data?.thermostats[0]?.lower_safety_temp :10,
		"lower_safety_temp_f": (data?.thermostats[0]?.lower_safety_temp_enabled) ? cToF_Rounded(data?.thermostats[0]?.lower_safety_temp) :50,
		"upper_safety_temp_enabled": data?.thermostats[0]?.upper_safety_temp_enabled,
		"upper_safety_temp_c": (data?.thermostats[0]?.upper_safety_temp_enabled) ? data?.thermostats[0]?.upper_safety_temp :30,
		"upper_safety_temp_f": (data?.thermostats[0]?.upper_safety_temp_enabled) ? cToF_Rounded(data?.thermostats[0]?.upper_safety_temp) :99,
		"rssi": data?.thermostats[0]?.rssi,
 		"hot_water_boost_time_to_end": (data?.thermostats[0]?.hot_water_boost_time_to_end?.toString()),
		"hot_water_boost_time_to_end_s":(data?.thermostats[0]?.hot_water_boost_time_to_end) ? 
			formatTimeInTimezone((data?.thermostats[0]?.hot_water_boost_time_to_end*1000))?.substring(0,16) : "None",
		"radiant_control_enabled": data?.thermostats[0]?.radiant_control_enabled,
		"learning_days_completed_cool":data?.thermostats[0]?.learning_days_completed_cool,
		"learning_days_completed_heat":data?.thermostats[0]?.learning_days_completed_heat,
		"learning_days_completed_range":data?.thermostats[0]?.learning_days_completed_range,
		"filter_changed_date": (data?.thermostats[0]?.filter_changed_date) ? 
			formatTimeInTimezone((data?.thermostats[0]?.filter_changed_date*1000))?.substring(0,16) : "None",
 		"associated_rcs_sensors": data?.thermostats[0]?.associated_rcs_sensors,
		"active_rcs_sensors": data?.thermostats[0]?.active_rcs_sensors,
		"rcs_control_setting":data?.thermostats[0]?.rcs_control_setting,
		"rcs_control_capable":data?.thermostats[0]?.rcs_capable,
		"motion": ((data?.thermostats[0]?.auto_away_enabled.toString()=='true') && (data?.thermostats[0]?.auto_away>0)) ? 'inactive' : 'active',
		"thermostat_alert": data?.thermostats[0]?.thermostat_alert,     
		"should_wake_on_approach": data?.thermostats[0]?.should_wake_on_approach,
		"user_brightness": data?.thermostats[0]?.user_brightness,
		"smoke_shutoff_supported": data?.thermostats[0]?.smoke_shutoff_supported,	
		"supportedStructures": "[Away,Home]"
 	]
	
							  
																																			  
											  
																																																																								
					   
								  
								  
		
	state?.supportedThermostatFanModes= supportedThermostatFanModes
	state?.supportedThermostatModes= supportedThermostatModes
	if (dataEvents?.has_hot_water_control=='false') {
		traceEvent(settings.logFilter,"refresh_thermostat>thermostat $thermostatId doesn't seem to have hot water control, displaying correct icon...",
			true, GLOBAL_LOG_INFO, true) 
	}    
	generateEvent(dataEvents)

	updateCurrentTileValue() 
    
	traceEvent(settings.logFilter, "refresh_thermostat>about to call getStructure()") 
	structure=getStructure(true) 
	if (structure) {
		traceEvent(settings.logFilter, "refresh_thermostat>structure name= ${structure?.name}")
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

	def isChanged= isStateChange(device, "supportedThermostatModes", supportedThermostatModes?.toString())    
																	 
	sendEvent(name: "supportedThermostatModes", value: supportedThermostatModes, isStateChange: isChanged, displayed: (settings.trace?:false))	
	isChanged= isStateChange(device, "supportedThermostatFanModes", supportedThermostatFanModes?.toString())    
																		   
	sendEvent(name: "supportedThermostatFanModes", value: supportedThermostatFanModes, isStateChange: isChanged, displayed: (settings.trace?:false))	

	def low_heating=device.currentValue("heatingSetpointRangeLow")
	def high_heating = device.currentValue("heatingSetpointRangeHigh")  
	def heatingSetpointRange= [low_heating,high_heating]
	isChanged= isStateChange(device, "heatingSetpointRange", heatingSetpointRange?.toString())    
	sendEvent(name: "heatingSetpointRange", value: heatingSetpointRange, isStateChange: isChanged, displayed: (settings.trace?:false))
	def low_cooling= device.currentValue("coolingSetpointRangeLow")
	def high_cooling = device.currentValue("coolingSetpointRangeHigh")   
	def coolingSetpointRange= [low_cooling,high_cooling]
	isChanged= isStateChange(device, "coolingSetpointRange", coolingSetpointRange?.toString())    
	sendEvent(name: "coolingSetpointRange", value: coolingSetpointRange, isStateChange: isChanged, displayed: (settings.trace?:false))	
	def thermostatSetpointRange= [low_heating,high_cooling]
	isChanged= isStateChange(device, "thermostatSetpointRange", thermostatSetpointRange?.toString())    
	sendEvent(name: "thermostatSetpointRange", value: thermostatSetpointRange?.toString(), isStateChange: isChanged, displayed: (settings.trace?:false))	
//	sendEvent(name: "schedule",value: JsonOutput.toJson(["None"]), isStateChange: isChanged, displayed: (settings.trace?:false))   
	getWeatherAttributes()    
	traceEvent(settings.logFilter,"refresh_thermostat>done for thermostatId =${thermostatId}", settings.trace)
	    
}

void getWeatherAttributes()  {
	def weather =  getTwcConditions(settings.zipcode)
	def scale=state?.scale    
	traceEvent(settings.logFilter,"getWeatherAttributes>begin, scale=$scale, settings.zipcode=${settings.zipcode}, weather=$weather", settings.trace)
	if (!weather) {
		traceEvent(settings.logFilter,"getWeatherAttributes>not able to get weather", settings.trace, GLOBAL_LOG_WARN)
		return        
	}    
	String outdoorTempString
	if (scale == "C") {
		double outdoorTemp = weather.temperature.toDouble().round(1)
		outdoorTempString = String.format('%2.1f', outdoorTemp)
	        
	} else {
		double outdoorTemp = weather.temperature.toDouble().round()
		outdoorTempString = String.format('%2d', outdoorTemp.intValue())
	}
	def isChange = isStateChange(device,"weatherTemperature", outdoorTempString)
	def isDisplayed = isChange        
	sendEvent( name: "weatherTemperature", value: outdoorTempString, unit: scale, displayed: isDisplayed)
	def hum= weather.relativeHumidity
	def weatherCondition=weather.wxPhraseShort
	def windSpeed=weather.windSpeed
	def windDirection=weather.windDirectionCardinal 
	def weatherDateTime=formatDateInLocalTime(weather.validTimeLocal)?.substring(0,16) 
	def weatherPressure=weather.pressureAltimeter    
	def weatherPop=weather.precip24Hour
	def dataEvents=[
		"weatherRelativeHumidity":hum,
		"weatherCondition": weatherCondition,
		"weatherWindSpeed": windSpeed,
		"weatherWindDirection":windDirection,
		"weatherDateTime":weatherDateTime,
		"weatherPressure":weatherPressure,
		"weatherPop":weatherPop        
	]
	generateEvent(dataEvents)    
}

// refresh() has a different polling interval as it is called by the UI (contrary to poll).
void refresh() {
	def thermostatId= determine_tstat_id("") 	    
	def poll_interval=0.2  // set a 20 sec. poll interval to avoid unecessary load on Nest servers
	def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
	if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
		traceEvent(settings.logFilter,"refresh>thermostatId = ${thermostatId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), not refreshing data...",
			settings.trace)	            
		return
	}
	state.lastPollTimestamp = now()
	getThermostatInfo(thermostatId,false) 
	refresh_thermostat(thermostatId)	    	
  
}


void initialRefresh() {
	state.lastPollTimestamp = now()
	getThermostatInfo(thermostatId,false) 
	refresh_thermostat(thermostatId, true)	    	
}




private void generateEvent(Map results) {
	def scale =state?.scale
	traceEvent(settings.logFilter,"generateEvent>parsing data $results, scale=$scale", settings.trace)
    
	if (results) {
		results.each { name, value ->
			def isDisplayed = true
			String upperFieldName=name.toUpperCase()    

// 			Temperature variable names for display contain 'display'            

			if (upperFieldName?.contains("DISPLAY")) {  

				String tempValueString 
				double tempValue 
				if (scale == "C") {
					tempValue = value.toDouble()
					tempValueString = String.format('%2.1f', tempValue)
				                    
				} else {
					tempValue = value.toDouble().round()
					tempValueString = String.format('%2d', tempValue.intValue())            
				}
                
				def isChange = isStateChange(device, name, tempValue.toString())
                
				isDisplayed = isChange
				sendEvent(name: name, value: tempValueString, displayed: isDisplayed, unit: scale)                                     									 

			} else if (upperFieldName == "TARGET_TEMPERATURE_TYPE") { 
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), isStateChange: isChange, displayed: isDisplayed)       
			} else if (upperFieldName?.contains("THERMOSTATSETPOINT")) {    // make sure that the thermostat setpoint is ending with .0 or .5
				String tempValueString 
				double tempValue=value.toDouble().round(1)                
				if (state?.scale =='C') {    
					tempValueString = String.format('%2.1f', tempValue )                    
					if (tempValueString.matches(".*([.,][3456])")) {                
						tempValueString=String.format('%2d.5',tempValue.intValue())                
						traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 3456=> rounded to .5", settings.trace)	
					} else if (tempValueString.matches(".*([.,][789])")) {  
						traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 789=> rounded to next .0", settings.trace)	
						tempValue=tempValue.intValue() + 1                        
						tempValueString=String.format('%2d.0',tempValue.intValue())               
					} else {
						traceEvent(settings.logFilter,"updateCurrentTileValue>value $tempValueString which ends with 012=> rounded to previous .0", settings.trace)	
						tempValueString=String.format('%2d.0', tempValue.intValue())               
					}
				} else {
					tempValueString = String.format('%2d', tempValue.intValue())
				}    			
				def isChange = isStateChange(device, name, tempValueString)		// take value as is -don't transform it as it's been calculated already
				isDisplayed = isChange

				sendEvent(name: name, value: value, isStateChange: isChange, displayed: isDisplayed,unit: scale)       

			} else if ((upperFieldName?.contains("_STATE")) || (upperFieldName?.contains("_ACTIVE")) || 
				(upperFieldName?.contains("_ENABLED")) || (upperFieldName?.contains("HAS_"))) { 
				String valueString=value.toString()                
				value= (value && valueString != 'null') ? value : false            
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), displayed: isDisplayed)                                     									 
                
// 			Temperature variable names contain 'temp' or 'setpoint' (not for display)           

			} else if (upperFieldName?.endsWith("_C")) {               
				if (value != null) {            
					String tempValueString 
					double tempValue = value?.toDouble()
					tempValueString = String.format('%2.1f', tempValue)
					def isChange = isStateChange(device, name,  tempValueString)
					isDisplayed = isChange
					sendEvent(name: name, value: tempValueString, displayed: isDisplayed, unit: "C")
				}                    
			} else if (upperFieldName?.endsWith("_F")) {
				if (value != null) {            
					String tempValueString 
					double tempValue = value?.toDouble()?.round()
					tempValueString = String.format('%2d', tempValue.intValue())            
					def isChange = isStateChange(device, name,  tempValueString)
					isDisplayed = isChange
					sendEvent(name: name, value: tempValueString, displayed: isDisplayed, unit:"F")
				}                    

			} else if ( ((upperFieldName?.contains("TEMP")) || (upperFieldName?.contains("SETPOINT")) || (upperFieldName?.contains("TRESHOLD")))) {
				if (value != null) {            
					String tempValueString 
					double tempValue 
					if (scale == "C") {
						tempValue = value.toDouble()
						tempValueString = String.format('%2.1f', tempValue)
				                    
					} else {
						tempValue = value.toDouble().round()
						tempValueString = String.format('%2d', tempValue.intValue())            
					}
                    
					def isChange = isStateChange(device, name,  tempValueString)
					isDisplayed = isChange
					sendEvent(name: name, value: tempValueString, displayed: isDisplayed, unit:scale)
				}                    
			} else if (upperFieldName?.contains("SPEED")) {

// 			Speed variable names contain 'speed'

 				def speedValue = getSpeed(value).toFloat().round(1)
				def isChange = isStateChange(device, name, speedValue.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: speedValue.toString(), unit: getDistanceScale(), displayed: isDisplayed)                                     									 
			} else if (upperFieldName?.contains("HUMIDITY")) {
 				double humValue = value.toDouble().round(0)
				String humValueString = String.format('%2d', humValue.intValue())
				def isChange = isStateChange(device, name, humValueString)
				isDisplayed = isChange
				sendEvent(name: name, value: humValueString, unit: "%", displayed: isDisplayed)                                     									 
			} else if (upperFieldName?.contains("DATA")) { // data variable names contain 'data'

				sendEvent(name: name, value: value, displayed: (settings.trace?:false))                                     									 

			} else if (value != null && value.toString() != 'null' && value.toString() != '[:]' && value.toString() != '[]') {           
				def isChange = isStateChange(device, name, value.toString())
				isDisplayed = isChange
				sendEvent(name: name, value: value.toString(), isStateChange: isChange, displayed: isDisplayed)       
			}
		}
	}
}

private def getSpeed(value) {
	def miles = value
	if (state?.scale == "C"){
		return milesToKm(miles)
	} else {
		return miles
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


private def getDistanceScale() {
	def scale= state?.scale
	if (scale == 'C') {
		return "kmh"
	}
	return "mph"
}


// Get the basic thermostat status (heating,cooling,fan only)
// To be called after a poll() or refresh() to have the latest status

def getThermostatOperatingState() {

	def thermostatId= determine_tstat_id("")
	def operatingState = data?.thermostats[0]?.operating_state?.toUpperCase()
	def fanRunning=fanActive()     
	def currentOpState = operatingState?.contains('HEAT')? 'heating' : (operatingState?.contains('COOL')? 'cooling' : 
		(fanRunning)? 'fan only': 'idle')
	return currentOpState
}

// thermostatId may only be a specific thermostatId or "" (for current thermostat)
// To be called after a poll() or refresh() to have the latest status


private void api( method, id, args=null, success = {}) {
	def MAX_EXCEPTION_COUNT=20
	String URI_ROOT = "${get_API_URI_ROOT()}"
   
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
		'setThermostatSettings':
			[uri: "${URI_ROOT}/${get_API_VERSION()}/put", type: 'put'],
		'setHold': 
			[uri: "${URI_ROOT}/${get_API_VERSION()}/put", type: 'put'],
		'setStructure': 
			[uri: "${URI_ROOT}/${get_API_VERSION()}/put", type: 'put'],
		]
	def request = methods.getAt(method)
	traceEvent(settings.logFilter,"api> about to call doRequest with (unencoded) args = ${args}", settings.trace)
	doRequest(request.uri, args, request.type, success)
	if (request.type=="get" && args) {
		def args_encoded = java.net.URLEncoder.encode(args.toString(), "UTF-8")
		request.uri=request.uri + "?${args_encoded}"    
//		request.uri=request.uri + "?${args}"    
	}    
     
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

// Need to be authenticated in before this is called.
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
			refresh_tokens()  
		}    	
	}
    
   
}


void produceSummaryReport(pastDaysCount) {
	traceEvent(settings.logFilter,"produceSummaryReport>begin",settings.trace, GLOBAL_LOG_TRACE)
	def avg_tstat_temp,avg_room_setpoint, min_room_setpoint=200, max_room_setpoint=0, avg_tstat_humidity    
	boolean found_values=false
	Date todayDate = new Date()
	Date startOfPeriod = todayDate - pastDaysCount
	long min_room_timestamp,max_room_timestamp
	int holdCount, scheduleCount
	def rmSetpointData = device.statesSince("thermostatSetpoint", startOfPeriod, [max:200])
	def temperatureData = device.statesSince("temperature", startOfPeriod, [max:200])
	def humidityData = device.statesSince("humidity", startOfPeriod, [max:200])
	def holdEvents= device.statesSince("programScheduleName", startOfPeriod, [max:200])
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
		avg_tstat_temp= (temperatureData.sum{it.floatValue.toFloat()}/ (temperatureData.size())).toFloat().round(1)
		found_values=true        
	}        
	if (humidityData) {    
		avg_tstat_humidity = (humidityData.sum{it.value.toInteger()}/ (humidityData.size())).toFloat().round(0)
		found_values=true        
	} 
	if (holdEvents) {    
		holdCount= holdEvents.count{it.value.toString()=='hold'}
		scheduleCount= holdEvents.count{it.value.toString()=='schedule'}
		found_values=true        
	}        
	if (!found_values) {
		traceEvent(settings.logFilter,"produceSummaryReport>found no values for report,exiting",settings.trace)
		sendEvent(name: "summaryReport", value: "")
		return        
	}    
	String scale=state?.scale, unitScale='Fahrenheit',timePeriod="In the past ${pastDaysCount} days"
	def struct_HomeAwayMode= device.currentValue("st_away")
	def currentMode=device.currentValue("thermostatMode")    
	if (scale=='C') { 
		unitScale='Celsius'    
	}    
	if (pastDaysCount <2) {
		timePeriod="In the past day"    
	}    
	String roomName =device.currentValue("where_name")
//	String stName=device.currentValue("st_name")    
	String summary_report = "At your home, your Nest thermostat is in $currentMode mode." 
	summary_report=summary_report + "${timePeriod}"    
	if (roomName) {	
		summary_report= summary_report + ",in the room ${roomName} where the thermostat ${device.displayName} is located"
	} else {
    
		summary_report= summary_report + ",at the Nest ${device.displayName}"
	}    
    
	if (holdCount) {
 		summary_report= summary_report + ", there were $holdCount hold(s) observed" 
 	}
	if (scheduleCount) {
 		summary_report= summary_report + ", and $scheduleCount schedule change(s)" 
 	}
    
	if (avg_room_temp) {
		summary_report= summary_report + ",the average room temperature was ${avg_room_temp.toString()} degrees ${unitScale}"
	}
    
	if (avg_room_setpoint) {
 		summary_report= summary_report + ",your Nest thermostat's setpoint was ${avg_room_setpoint.toString()} degrees in average" 
 	}
	if (min_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(min_room_timestamp)					    
		summary_report= summary_report + ".The Nest's minimum setpoint was ${min_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
	if (max_room_setpoint && (min_room_timestamp != max_room_timestamp)) {
		def timeInLocalTime= formatTimeInTimezone(max_room_timestamp)					    
		summary_report= summary_report + ",and the Nest's maximum setpoint was ${max_room_setpoint.toString()} degrees on ${timeInLocalTime.substring(0,16)}" 
	}
    
	if (avg_tstat_temp) {
		summary_report= summary_report + ".The Nest's average ambient temp collected was ${avg_tstat_temp.toString()} degrees ${unitScale}."
	}
    
	if (avg_tstat_humidity) {
		summary_report= summary_report + "And finally, the thermostat's average relative humidity observed was ${avg_tstat_humidity.toString()}%."      
	}

	sendEvent(name: "summaryReport", value: summary_report, isStateChange: true)
    
	traceEvent(settings.logFilter,"produceSummaryReport>end",settings.trace, GLOBAL_LOG_TRACE)

}

// thermostatId is a unique serial number (not a list) 
//	if no thermostatId is provided, it is defaulted to the current thermostatId 
// settings can be anything supported by Nest at the ST community wiki for MyNextTstat
void setThermostatSettings(thermostatId,String thermostatSettings) {
	def settings=[:]
	try {
		settings = new groovy.json.JsonSlurper().parseText(thermostatSettings)   
		traceEvent(settings.logFilter,"setThermostatSettings>was able to convert json settings  to a map",settings.trace, GLOBAL_LOG_INFO)
	} catch (e) {
		traceEvent(settings.logFilter,"setThermostatSettings>was not able to convert settings {$thermostatSettings) to a map, not a valid json map",settings.trace, GLOBAL_LOG_ERROR)
		return
	}
//	call the corresponding method with the map  
	setThermostatSettings(thermostatId, settings)    
    
}

// thermostatId is single serial id 
//	if no thermostatId is provided, it is defaulted to the current thermostatId 
// settings can be anything supported by Nest at https://developers.nest.com/documentation/cloud/api-thermostat
void setThermostatSettings(thermostatId,tstatSettings = []) {
	def TOKEN_EXPIRED=401    
	def REDIRECT_ERROR=307  
	def NEST_SUCCESS=200    
	def BLOCKED=429
	def interval=1*60
	def bodyReq	
    
   	thermostatId= determine_tstat_id(thermostatId) 	    
	traceEvent(settings.logFilter,"setThermostatSettings>called with values ${tstatSettings} for ${thermostatId}",settings.trace)
	if (tstatSettings == null || tstatSettings == "" || tstatSettings == [] ) {
		traceEvent(settings.logFilter, "setThermostatSettings>tstatSettings set is empty, exiting", settings.trace)
		return        
	}
	bodyReq = [object_key:"device.${thermostatId}",op:"MERGE",value:tstatSettings]    
	    
	int statusCode
	def exceptionCheck
	api('setThermostatSettings', thermostatId, bodyReq) {resp ->
		statusCode = resp?.status
		state?.lastStatusCodeForSettings=statusCode				                
		if (statusCode==BLOCKED) {
			traceEvent(settings.logFilter,"setThermostatSettings>thermostatId=${thermostatId},Nest throttling in progress, error $statusCode, retries={state?.retriesSettingsCounter}", settings.trace, GLOBAL_LOG_ERROR)
			interval=1*60 			   // set a minimum of 5min. interval to avoid unecessary load on Nest servers
		}			
		exceptionCheck=device.currentValue("verboseTrace")
		if (statusCode == NEST_SUCCESS) {
			/* when success, reset the exception counter */
			state.exceptionCount=0
			if ((data?."replaySettingsId${state?.retriesSettingsCounter}" == null) ||
				(state?.retriesSettingsCounter > get_MAX_SETTER_RETRIES())) {          // reset the counter if last entry is null
				reset_replay_data('Settings')                
				state?.retriesSettingsCounter=0
			}            
			state?.lastPollTimestamp=now()
			traceEvent(settings.logFilter,"setThermostatSettings>done for ${thermostatId}", settings.trace)
		} else {
			traceEvent(settings.logFilter,"setThermostatSettings> error=${statusCode.toString()} for ${thermostatId}", true, GLOBAL_LOG_ERROR)
		} /* end if statusCode */
	} /* end api call */                
	if (exceptionCheck?.contains("exception")) {
		sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		traceEvent(settings.logFilter,"setThermostatSettings>exception=${exceptionCheck}", true, GLOBAL_LOG_ERROR)
	}                
	if ((statusCode == BLOCKED) ||
		(exceptionCheck?.contains("Nest response error")) || 
		(exceptionCheck?.contains("ConnectTimeoutException"))) {
		state?.retriesSettingsCounter=(state?.retriesSettingsCounter?:0)+1            
		if (!(interval= get_exception_interval_delay(state?.retriesSettingsCounter))) {   
			traceEvent(settings.logFilter,"setThermostatSettings>too many retries", true, GLOBAL_LOG_ERROR)
			state?.retriesSettingsCounter=0 
			reset_replay_data('Settings')                
			return        
		}        
		state.lastPollTimestamp = (statusCode==BLOCKED) ? (now() + (interval * 1000)):(now() + (0.5 * 60 * 1000)) 
		data?."replaySettingsId${state?.retriesSettingsCounter}"=thermostatId
		data?."replaySettings${state?.retriesSettingsCounter}"=tstatSettings  
		traceEvent(settings.logFilter,"setThermostatSettings>about to call setThermostatSettingsReplay,retries counter=${state?.retriesSettingsCounter}", true, GLOBAL_LOG_INFO)
		runIn(interval, "setThermostatSettingsReplay",[overwrite:true])          
	}    
}


void setThermostatSettingsReplay() {
	def exceptionCheck=""

	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) {
		def thermostatId = data?."replaySettingsId$i"
		if (thermostatId == null) continue  // already processed        
		def tstatSettings = data?."replaySettings$i"
		def poll_interval=1 
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		traceEvent(settings.logFilter,"setThermostatSettingsReplay>about to recall setThermostatSettings for thermostatId=$thermostatId,retries counter=$i" +
			",tstatSettings=$tstatSettings", true, GLOBAL_LOG_INFO)
		setThermostatSettings(thermostatId,tstatSettings) 
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

// thermostatId is single serial id 
//	if no thermostatId is provided, it is defaulted to the current thermostatId 
// settings can be anything supported by Nest at https://developers.nest.com/documentation/cloud/api-thermostat
void setThermostatSharedSettings(thermostatId,tstatSettings = []) {
	def TOKEN_EXPIRED=401    
	def REDIRECT_ERROR=307  
	def NEST_SUCCESS=200    
	def BLOCKED=429
	def interval=1*60
    
   	thermostatId= determine_tstat_id(thermostatId) 	    
	traceEvent(settings.logFilter,"setThermostatSettings>called with values ${tstatSettings} for ${thermostatId}",settings.trace)
	if (tstatSettings == null || tstatSettings == "" || tstatSettings == [] ) {
		traceEvent(settings.logFilter, "setThermostatSettings>tstatSettings set is empty, exiting", settings.trace)
		return        
	}
	def bodyReq = [object_key:"shared.${thermostatId}",op:"MERGE",value:tstatSettings]    
	    
	int statusCode
	def exceptionCheck
	api('setThermostatSettings', thermostatId, bodyReq) {resp ->
		statusCode = resp?.status
		state?.lastStatusCodeSharedSettings=statusCode				                
		if (statusCode==BLOCKED) {
			traceEvent(settings.logFilter,"setThermostatSharedSettings>thermostatId=${thermostatId},Nest throttling in progress, error $statusCode, retries={state?.retriesSharedSettingsCounter}", settings.trace, GLOBAL_LOG_ERROR)
			interval=1*60 			   // set a minimum of 5min. interval to avoid unecessary load on Nest servers
		}			
		exceptionCheck=device.currentValue("verboseTrace")
		if (statusCode == NEST_SUCCESS) {
			/* when success, reset the exception counter */
			state.exceptionCount=0
			if ((data?."replaySharedSettingsId${state?.retriesSharedSettingsCounter}" == null) ||
				(state?.retriesSharedSettingsCounter > get_MAX_SETTER_RETRIES())) {          // reset the counter if last entry is null
				reset_replay_data('SharedSettings')                
				state?.retriesSharedSettingsCounter=0
			}            
			traceEvent(settings.logFilter,"setThermostatSharedSettings>done for ${thermostatId}", settings.trace)
		} else {
			traceEvent(settings.logFilter,"setThermostatSharedSettings> error=${statusCode.toString()} for ${thermostatId}", true, GLOBAL_LOG_ERROR)
		} /* end if statusCode */
	} /* end api call */                
	if (exceptionCheck?.contains("exception")) {
		sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		traceEvent(settings.logFilter,"setThermostatSharedSettings>exception=${exceptionCheck}", true, GLOBAL_LOG_ERROR)
	}                
	if ((statusCode == BLOCKED) ||
		(exceptionCheck?.contains("Nest response error")) || 
		(exceptionCheck?.contains("ConnectTimeoutException"))) {
		state?.retriesSharedSettingsCounter=(state?.retriesSharedSettingsCounter?:0)+1            
		if (!(interval= get_exception_interval_delay(state?.retriesSharedSettingsCounter))) {   
			traceEvent(settings.logFilter,"setThermostatSharedSettings>too many retries", true, GLOBAL_LOG_ERROR)
			state?.retriesSharedSettingsCounter=0 
			reset_replay_data('SharedSettings')                
			return        
		}        
		state.lastPollTimestamp = (statusCode==BLOCKED) ? (now() + (interval * 1000)):(now() + (0.5 * 60 * 1000)) 
		data?."replaySharedSettingsId${state?.retriesSharedSettingsCounter}"=thermostatId
		data?."replaySharedSettings${state?.retriesSharedSettingsCounter}"=tstatSettings  
		traceEvent(settings.logFilter,"setThermostatSharedSettings>about to call setThermostatSharedSettingsReplay,retries counter=${state?.retriesSharedSettingsCounter}", true, GLOBAL_LOG_INFO)
		runIn(interval, "setThermostatSharedSettingsReplay",[overwrite:true])          
	}    
}

void setThermostatSharedSettingsReplay() {
	def exceptionCheck=""

	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) {
		def thermostatId = data?."replaySharedSettingsId$i"
		if (thermostatId == null) continue  // already processed        
		def tstatSettings = data?."replaySharedSettings$i"
		def poll_interval=1 
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		traceEvent(settings.logFilter,"setThermostatSharedSettingsReplay>about to recall setThermostatSettingsShared for thermostatId=$thermostatId,retries counter=$i" +
			",tstatSettings=$tstatSettings", true, GLOBAL_LOG_INFO)
		setThermostatSharedSettings(thermostatId,tstatSettings) 
		exceptionCheck=device.currentValue("verboseTrace")
		if (exceptionCheck?.contains("done")) {
			data?."replaySharedSettingsId$i"=null        
		} /* end if */
	} /* end for */
	if (exceptionCheck?.contains("done")) { // if last command executed OK, then reset the counter
		reset_replay_data('SharedSettings')                
		state?.retriesSharedSettingsCounter=0
	}     
}    





//	if no thermostatId is provided, it is defaulted to the current thermostatId 
void setHold(thermostatId, coolingSetPoint, heatingSetPoint) {
	def NEST_SUCCESS=200
	def TOKEN_EXPIRED=401    
	def REDIRECT_ERROR=307    
	def BLOCKED=429   
	def interval=1*60     
    
	traceEvent(settings.logFilter,"sethold>called with values ${coolingSetPoint}, ${heatingSetPoint} for ${thermostatId}", settings.trace)
   	def tstatId= determine_tstat_id() 	
	thermostatId=determine_tstat_id(thermostatId)    
	traceEvent(settings.logFilter,"setHold>thermostatId = ${thermostatId} vs. tstatId=$tstatId",
		settings.trace)            
	if (thermostatId != tstatId) { // get the thermostat as the current thermostat id
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		traceEvent(settings.logFilter,"setHold>about to get latest info for thermostatId = ${thermostatId}",
			settings.trace)            
		getThermostatInfo(thermostatId)    
	}    
/*    
	if (state?.lastStatusCodeForHold==BLOCKED) {    
		def poll_interval=1  // set a minimum of 1 min interval to avoid unecessary load on Nest servers
		def time_check_for_poll = (now() - (poll_interval * 60 * 1000))
		if ((state?.lastPollTimestamp) && (state?.lastPollTimestamp > time_check_for_poll)) {
			traceEvent(settings.logFilter,"setHold>thermostatId = ${thermostatId},time_check_for_poll (${time_check_for_poll} < state.lastPollTimestamp (${state.lastPollTimestamp}), throttling in progress, command not being processed",
				settings.trace, GLOBAL_LOG_ERROR)            
			return
		}
	}
*/    
	String currentMode=device.currentValue("thermostatMode")
	def canHeat=canHeat()    
	def canCool=canCool()    
    
//	traceEvent(settings.logFilter,"sethold>About to translate into targetCoolTemp & targetHeatTemp values", settings.trace)
	def scale = state?.scale
    
	def values=[:]    
	def targetCoolTemp = (scale=='C')? coolingSetPoint.toDouble() : coolingSetPoint.toDouble().round().intValue()
	def targetHeatTemp = (scale=='C')? heatingSetPoint.toDouble() : heatingSetPoint.toDouble().round().intValue()
//	traceEvent(settings.logFilter,"sethold>targetCoolTemp=$targetCoolTemp & targetHeatTemp=targetHeatTemp", settings.trace)
    
	if (scale == 'C') {
		if (currentMode == 'auto' && canHeat && canCool) {
			def current_temp_low=device.currentValue("target_temperature_low_c")
			def current_temp_high=device.currentValue("target_temperature_high_c")
			current_temp_high = ( current_temp_high) ? current_temp_high.toDouble() : 21.0          
			current_temp_low = ( current_temp_low) ? current_temp_low.toDouble() : 21.0           
			if (targetHeatTemp.toDouble() != current_temp_low) {            
				values= ["target_temperature_low": targetHeatTemp]
			}                
			if (targetCoolTemp.toDouble() != current_temp_high) {
				values = values + ["target_temperature_high": targetCoolTemp]    			   
			}                
		} else if (currentMode== 'heat' && canHeat) {
			values= ["target_temperature": targetHeatTemp]
		} else if (currentMode== 'cool' && canCool) {
			values= ["target_temperature": targetCoolTemp]
		} else {
			traceEvent(settings.logFilter,"setHold>thermostatId=${thermostatId},not able to change the setpoint as the thermostat is in wrong mode ($currentMode)",
				settings.trace, GLOBAL_LOG_WARN)                
			return
		}    
        
	} else {
		double target_temp_in_C            
		if (currentMode == 'auto' && canHeat && canCool) {
			def current_temp_low=device.currentValue("target_temperature_low_f")
			def current_temp_high=device.currentValue("target_temperature_high_f")
			current_temp_high = ( current_temp_high) ? current_temp_high.toDouble() : 72           
			current_temp_low = ( current_temp_low) ? current_temp_low.toDouble() : 72           
			if (targetHeatTemp.toDouble() != current_temp_low) {  
				target_temp_in_C= fToC(targetHeatTemp)            
				values= ["target_temperature_low": target_temp_in_C]
			                
			}                
			if (targetCoolTemp.toDouble() != current_temp_high) {
				target_temp_in_C= fToC(targetCoolTemp)            
				values = values + ["target_temperature_high": target_temp_in_C]    			   
			}                
		} else if (currentMode== 'heat' && canHeat) {
			target_temp_in_C= fToC(targetHeatTemp)            
			values= ["target_temperature": target_temp_in_C]
		} else if (currentMode== 'cool' && canCool) {
			target_temp_in_C= fToC(targetCoolTemp)            
			values= ["target_temperature": target_temp_in_C]
		} else {
			traceEvent(settings.logFilter,"setHold>thermostatId=${thermostatId},not able to change the setpoint as the thermostat is in wrong mode ($currentMode)",
				settings.trace, GLOBAL_LOG_WARN)                
			return
		}    
	}
	int statusCode
	def exceptionCheck  
	def bodyReq=[object_key: "shared.${thermostatId}", op:"MERGE", value:values]	    
	api('setHold', thermostatId, bodyReq) {resp ->
		statusCode = resp?.status
		state?.lastStatusCodeForHold=statusCode				                
		if (statusCode==BLOCKED) {
			traceEvent(settings.logFilter,"setHold>thermostatId=${thermostatId},Nest throttling in progress,error $statusCode, retries=${state?.retriesHoldCounter}", settings.trace, GLOBAL_LOG_ERROR)
			interval=1 * 60   // set a minimum of 1min. interval to avoid unecessary load on Nest servers
		}
		exceptionCheck=device.currentValue("verboseTrace")
		if (statusCode == NEST_SUCCESS) {
			/* when success, reset the exception counter */
			if ((data?."replayHoldId${state?.retriesHoldCounter}" == null) || 
				(state?.retriesHoldCounter > get_MAX_SETTER_RETRIES())) {          // reset the counter if last entry is null
				reset_replay_data('Hold')                
				state?.retriesHoldCounter=0
			}            
			state.exceptionCount=0
			state?.lastPollTimestamp=now()
			traceEvent(settings.logFilter,"setHold>done for ${thermostatId}",settings.trace)
		} else {
			traceEvent(settings.logFilter,"setHold>> error=${statusCode.toString()} for ${thermostatId}",true, GLOBAL_LOG_ERROR)
		} /* end if statusCode */
	} /* end api call */
	if (exceptionCheck?.contains("exception")) {
		sendEvent(name: "verboseTrace", value: "", displayed:(settings.trace?:false)) // reset verboseTrace            
		traceEvent(settings.logFilter,"setHold>exception=${exceptionCheck}", true, GLOBAL_LOG_ERROR)
	}                
        
	if ((statusCode == BLOCKED) ||
		(exceptionCheck?.contains("Nest response error")) || 
		(exceptionCheck?.contains("ConnectTimeoutException"))) {
		state?.retriesHoldCounter=(state?.retriesHoldCounter?:0)+1        
		if (!(interval=get_exception_interval_delay( state?.retriesHoldCounter))) {   
			traceEvent(settings.logFilter,"setHold>too many retries", true, GLOBAL_LOG_ERROR)
			reset_replay_data('Hold')                
			state?.retriesHoldCounter=0            
			return        
		}        
		state.lastPollTimestamp = (statusCode==BLOCKED) ? (now() + (interval * 1000)):(now() + (0.5 * 60 * 1000)) 
		data?."replayHoldId${state?.retriesHoldCounter}"=thermostatId
		data?."replayCoolingSetpoint${state?.retriesHoldCounter}"=coolingSetPoint        
		data?."replayHeatingSetpoint${state?.retriesHoldCounter}"=heatingSetPoint 
		traceEvent(settings.logFilter,"setHold>about to call setHoldReplay,retries counter=${state?.retriesHoldCounter}", true, GLOBAL_LOG_INFO)
		runIn(interval, "setHoldReplay",[overwrite:true])        
	}      
}
void setHoldReplay() {
	def exceptionCheck=""
	for (int i=1; (i<= get_MAX_SETTER_RETRIES()); i++) {
		def thermostatId = data?."replayHoldId${i}"
		if (thermostatId == null) continue  // already processed        
		def coolingSetpoint= data?."replayCoolingSetpoint${i}"        
		def heatingSetpoint= data?."replayHeatingSetpoint${i}"
		def poll_interval=1 
		state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
		traceEvent(settings.logFilter,"setHoldReplay>about to recall setHold for thermostatId=$thermostatId,retries counter=$i" +
			",coolingSetpoint=$coolingSetpoint, heatingSetpoint=$heatingSetpoint", true, GLOBAL_LOG_INFO)
		setHold(thermostatId, coolingSetpoint, heatingSetpoint)
		exceptionCheck=device.currentValue("verboseTrace")
		if (exceptionCheck?.contains("done")) {
			data?."replayHoldId$i"=null        
		} /* end if */
	} /* end for */
	if (exceptionCheck?.contains("done")) { // if last command executed OK, then reset the counter
		reset_replay_data('Hold')                
		state?.retriesHoldCounter=0
	} /* end if */
}    

void updateChildData(objects) {
	traceEvent(settings.logFilter,"updateChildData>objects from parent=$objects",settings.trace,GLOBAL_LOG_TRACE)        
	if (!data?.thermostats) {
		data?.thermostats=[]    
	}    
	data?.thermostats=objects
	traceEvent(settings.logFilter,"updateChildData>data?.thermostats=${data?.thermostats}",settings.trace,GLOBAL_LOG_TRACE)        
}

void updateChildStructureData(objects) {
	traceEvent(settings.logFilter,"updateChildStructureData>objects from parent=$objects",settings.trace,GLOBAL_LOG_TRACE)        
	if (!data?.structure) {
		data?.structure=[]    
	}    
	data?.structure=objects
	traceEvent(settings.logFilter,"updateChildStructureData>data?.structure=${data?.structure}",settings.trace,GLOBAL_LOG_TRACE)        
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


//	if no thermostatId is provided, it is defaulted to the current thermostatId 
void getThermostatInfo(thermostatId,useCache=true) {

	thermostatId=determine_tstat_id(thermostatId)    

	parent.getObject(thermostatId,"thermostat",useCache)
	parent.updateObjects(this, "thermostat", thermostatId)   
    
}


void getThermostatInfoReplay() {
	def id = data?.replayId
	def poll_interval=1 
	state?.lastPollTimestamp= (now() - (poll_interval * 60 * 1000)) // reset the lastPollTimeStamp to pass through
	traceEvent(settings.logFilter,"getThermostatInfoReplay>about to call getThermostatInfo for ${id}",settings.trace, GLOBAL_LOG_INFO)
	getThermostatInfo(id) 
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





private def process_redirectURL(redirect) {

	if (!redirect) {
		return true        
	}    
	def redirectURL= redirect.minus('https://')
	redirectURL= 'https://' + redirectURL.substring(0,(redirectURL?.indexOf('/',0)))             
	traceEvent(settings.logFilter,"process_redirectURL>orignal redirection= ${redirect}, redirectURL=$redirectURL")           
	state?.redirectURLcount=(state?.redirectURLcount?:0)+1            
	save_redirectURL(redirectURL)    
	if (state?.redirectURLcount > get_MAX_REDIRECT()) {
		return false    
	}		        
	return true    
}

private boolean refresh_tokens() {
	// Nest logic for refresh_tokens not available for the moment.
	parent.refreshThisChildAuthTokens(this)    
	return true
}

private void save_redirectURL(redirectURL) {
	state?.redirectURL=redirectURL  // save the redirect location for further call purposes
	traceEvent(settings.logFilter,"save_redirectURL>${state?.redirectURL}",settings.trace)  
}
synchronized void save_data_auth(auth) {

	data?.auth?.access_token = auth.access_token 
//	data?.auth?.refresh_token = auth.refresh_token
	data?.auth?.expires_in = auth.expires_in
	data?.auth?.nest_czfe_url = auth?.nest_czfe_url    
	data?.auth?.google_jwt = auth?.google_jwt    
	data?.auth?.nest_auth_token = auth?.nest_auth_token    
	data?.auth?.token_type =   "Basic" 
//	data?.auth?.token_type =   auth.token_type 
//	data?.auth?.scope = auth?.scope
	data?.auth?.authexptime = auth.authexptime
	traceEvent(settings.logFilter,"save_data_auth>saved data.auth=$data.auth")
}


private def isLoggedIn() {
	if (data?.auth?.access_token == null) {
		traceEvent(settings.logFilter,"isLoggedIn> no data auth", settings.trace,GLOBAL_LOG_TRACE)
		return false
	} 
	return true
}
private def isTokenExpired() {
	def buffer_time_expiration=5  // set a 5 min. buffer time 
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


// Determine id from settings or initialSetup
private def determine_tstat_id(tstat_id) {
	def tstatId=device.currentValue("thermostatId")
    
	if ((tstat_id != null) && (tstat_id != "")) {
		tstatId = tstat_id
	} else if ((settings.thermostatId != null) && (settings.thermostatId  != "")) {
		tstatId = settings.thermostatId.trim()
		traceEvent(settings.logFilter,"determine_tstat_id> tstatId = ${settings.thermostatId}", settings.trace)
	} else if (data?.auth?.thermostatId) {
		settings.appKey = data.auth.appKey
		settings.thermostatId = data.auth.thermostatId
		tstatId=data.auth.thermostatId
		traceEvent(settings.logFilter,"determine_tstat_id> tstatId from data.auth= ${data.auth.thermostatId}",settings.trace)
	} else if ((tstatId !=null) && (tstatId != "")) {
		settings.thermostatId = tstatId
		traceEvent(settings.logFilter,"determine_tstat_id> tstatId from device= ${tstatId}",settings.trace)
	}
    
	if ((tstat_id != "") && (tstatId && tstat_id != tstatId)) {
		sendEvent(name: "thermostatId", displayed: (settings.trace?:false),value: tstatId)    
	}
	return tstatId
}

// Get the appKey for authentication
private def get_appKey() {
	return data?.auth?.appKey    
    
}    

// @Get the privateKey for authentication
private def get_privateKey() {
	
	return data?.auth?.privateKey
}    
   


// Called by MyNextServiceMgr for initial creation of a child Device
void initialSetup(auth_data, device_tstat_id) {
	settings.trace=true
	settings?.logFilter=5
    
	traceEvent(settings.logFilter,"initialSetup>begin",settings.trace)
//	log.debug "initialSetup> structure_id = ${structure_id}"
	log.debug "initialSetup> device_tstat_id = ${device_tstat_id}"
//	log.debug "initialSetup> device_client_id = ${device_client_id}"
//	log.debug "initialSetup> private_key_id = ${private_key_id}"
//	traceEvent(settings.logFilter,"initialSetup> structure_id = ${structure_id}",settings.trace)
	traceEvent(settings.logFilter,"initialSetup> device_tstat_id = ${device_tstat_id}",settings.trace)
//	traceEvent(settings.logFilter,"initialSetup> device_client_id = ${device_client_id}",settings.trace)
//	traceEvent(settings.logFilter,"initialSetup> private_key_id = ${private_key_id}",settings.trace)
//	settings?.structureId = structure_id
	settings.thermostatId = device_tstat_id
	sendEvent(name: "thermostatId", value:device_tstat_id,  displayed: (settings.trace?: false))    
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

	state?.scale = getTemperatureScale()
	runIn(1*30, "initialRefresh")
	state?.exceptionCount=0    
	state?.currentMode=device.currentValue("thermostatMode")    
    
}


private def get_stats_attribute(attribute, startDate,endDate,operation='avg',filterEvents=false,value=null) {
	def result
	int count    
	def recentStates = device.statesBetween(attribute, startDate, endDate, [max:200])
	def MIN_TEMP_VALUE=0
	def MAX_TEMP_VALUE=120
    
	if ((!value) && filterEvents) {    
		count =recentStates.count {(it.floatValue >MIN_TEMP_VALUE && it.floatValue < MAX_TEMP_VALUE)}
	} else if (!value) {    
		count =recentStates.count {it.value}
	} else {
		count =recentStates.count {(it.value == value)}    
	}    
	switch (operation) {    
		case 'total':
			if (filterEvents) {             
				result =recentStates.inject(0) { total, i -> total + ((i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)? i.floatValue :0) }
			} else {
				result =recentStates.inject(0) { total, i -> total + i.floatValue }
	
    		}
			break        
		case 'avg':
		case 'deviation':
			float avgResult        
			if (filterEvents) {             
				avgResult = recentStates.inject(0) { total, i -> total + ((i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)? i.floatValue:0)}
			} else {
				avgResult = recentStates.inject(0) { total, i -> total + i.floatValue}
			}            
			avgResult = (count)? (avgResult /count).round(1):0        
			if (operation == 'avg') {
				result=avgResult            
				break
			}   
			if (filterEvents) {             
				result = recentStates.inject(0) { totalDev, i -> totalDev + ((i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)? 
					((i.value.toFloat() - avgResult).abs()) : 0)}
			} else {  
				result = recentStates.inject(0) { totalDev, i -> totalDev + ((i.floatValue - avgResult).abs())}
			}			                    
			result = (count)? (result /count).round(2):0        
//			traceEvent(settings.logFilter,"get_stats_attribute>for ${attribute}, avg=${avgResult},avgDeviation=${result},count=${count}",settings.trace)
			break            
		case 'min':
			if (filterEvents) {             
				result =recentStates.inject(MAX_TEMP_VALUE) {min, i -> Math.min(min,((i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)?i.floatValue:MAX_TEMP_VALUE))}
			} else {
				result =recentStates.inject(MAX_TEMP_VALUE) {min, i -> Math.min(min, i.floatValue) }
			}            
			break            
		case 'max':
			if (filterEvents) {             
				result =recentStates.inject(MIN_TEMP_VALUE) {max, i -> Math.max(max,((i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)?i.floatValue:MIN_TEMP_VALUE)) }
			} else {
				result =recentStates.inject(MIN_TEMP_VALUE) {max, i -> Math.max(max,i.floatValue) }
			}            
			break            
		case 'values':
        
			if (filterEvents) {             
				result =recentStates.inject([]) {valueSet, i -> (i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)?
					valueSet << i.floatValue: {} }
			} else if (value) {
				def valuesFound =recentStates.find {it.value==value }
				result = valuesFound.inject([])  {valueSet, i -> valueSet << i.value}               
                
			} else {                 
				result =recentStates.inject([]) {valueSet, i -> valueSet << i.value}
			}                
			                
			break            
		case 'dates':
			if (filterEvents) {             
				result =recentStates.inject([]) {valueSet, i -> (i.floatValue >MIN_TEMP_VALUE && i.floatValue < MAX_TEMP_VALUE)?
					valueSet << i.date.getTime(): {} }
			} else if (value) {
				def valuesFound =recentStates.find {it.value==value }
//				if (settings.trace) {                
//					valuesFound.each {                
//						traceEvent(settings.logFilter,"get_stats_attribute>dates valueFound= ${it.value}, date=${it.date.getTime()}, true)
//					}                        
//				}                    
				result = valuesFound.inject([])  {valueSet, i -> valueSet << i.date.getTime()}               
//				traceEvent(settings.logFilter,"get_stats_attribute>dates result set= ${result}",settings.trace)
			} else {                 
				result =recentStates.inject([]) {valueSet, i ->  valueSet << i.date.getTime()}
			}                
			break            
		default:
			result=count
		break            
	}    
	return result    
}

private def get_recommendation(tip) {

	def tips = [
		'tip01': 
			[level:1, 
				text:"You should set the default Sleep climate at Nest according to your Sleep schedule and use energy efficient settings as indicated on the energy.gov website." +
				"You can save as much as 10% to 20% a year on heating and cooling by doing a temperature setback or setforward of 7 degrees Fahrenheit or 3 degrees Celsius for 8 hours a day from its normal setting." +
				"A temperature setback is when you lower your desired temperature before your thermostat calls for heat." +
				"A temperature setforward is when you increase your desired temperature before your thermostat calls for cool."
			], 
		'tip02': 
			[level:1, 
				text:"You should set the eco settings at Nest according to your work schedule and use energy efficient settings as indicated on the energy.gov website." +
				"You can save as much as 10% to 20% a year on heating and cooling by doing a temperature setback or setforward of 7 degrees Fahrenheit or 3 degrees Celsius for 8 hours a day from its normal setting." +
				"A temperature setback is when you lower your desired temperature before your thermostat calls for heat." +
				"A temperature setforward is when you increase your desired temperature before your thermostat calls for cool."
			],
		'tip03': 
			[level:1, 
 				text: "When you have more than one thermostat registered to your account, you can group them together. Adjustments made on one thermostat are then made to ALL thermostats in that group. It will allow you to determine which features within a specific group are synchronized. " +
				"For example if you select Synchronize User Preferences, when you configure any user preference on one thermostat (i.e. choose Celsius vs. Fahrenheit) ALL thermostats within that group will change to Celsius.  Consult your Nest user guide for more details."			
			],
		'tip04': 
			[level:1, 
				text: "To reduce the number of cycles, you can try to increase the Heat and Cool Minimum On Time and the Heat and Cool Differential settings at the thermostat unit in Main Menu, Settings, Installation Settings, and finally Thresholds for longer cycles." +
				"You can also try to increase the Heat or Cool dissipation time"  +
				" at the thermostat unit, which is 30 seconds by default. The dissipation time settings control the fan runtime in seconds after a cool or heat cycle which helps to evacuate any hot or cool air from the ducts." + 
				" Consult the Nest user guide for more details."
			],
		'tip05': 
			[level:1, 
				text:"The default Hold action is “Until you change it.” You can configure how long a temperature hold will remain in effect. On the thermostat, Select Main Menu, Settings,Preferences, then Select Hold action."+
				"Your options are: a) 2 hours (Holds temperature change for a period of 2 hours, then resumes your normal schedule)." +
				"b) 4 hours, c) until the next scheduled activity or comfort setting and the last one d) Until you change it. For maximum efficiency, it is recommended to use the 'until the next scheduled activity or transition' as Nest will use its own scheduling as much as possible and avoid permanent holds." 
			],
		'tip06': 
			[level:1, 
				text:"You can lock your Nest Thermostat from the Settings menu on your thermostat, or with the Nest app." +
				"Locking your Nest Thermostat is a good way to keep guests or curious children from changing your thermostat’s settings while still letting you and your family adjust your home’s temperature within the range you’ve set. " 
			],
		'tip07': 
			[level:2, 
				text:"Airwave is a Nest option that will automatically switch your AC compressor off after the room is cooled. Then, it uses your AC's fan to blow the cool air throughout the house. This saves money on cooling costs." +
				"To turn the option on, go to the app and tap on the Settings icon. Then, tap on Airwave and toggle on the switch icon."
			],
		'tip10': 
			[level:2, 
				text:"You can save as much as 10% to 20% a year on heating and cooling by doing a temperature setback or setforward of 7 degrees Fahrenheit or 3 degrees Celsius for 8 hours a day from its normal setting." 
			],
		'tip11': 
			[level:2, 
				text:"The auto-schedule option on the Nest learns how warm or cold you like your home throughout the day and automatically sets the temperature for you, using what it has learned. If you feel that your Nest can get confused, " +
				"you can turn this feature off. Go to the app and tap on the Settings icon. Then, tap on Auto-Schedule and toggle off the switch icon. You can set up your own schedule for better efficency."                
			],
		'tip13': 
			[level:2, 
				text:"The location of your thermostat can affect its performance and efficiency. Read the manufacturer's installation instructions to prevent ghost readings or unnecessary furnace or air conditioner cycling." +
				"To operate properly, the thermostat must be on an interior wall away from direct sunlight, drafts, doorways, skylights, and windows. It should be located where natural room air currents–warm air rising, cool air sinking–occur." +
				"Otherwise, please use the Nest's Sunblock feature under Settings. Also, make sure to change your furnace filter and clean your A/C evaporator coils using mild detergents and water for maximum equipment efficiency."
			],
		'tip14': 
			[level:2, 
				text:"Your Nest thermostat works with wireless remote sensors to measure temperature and occupancy in multiple rooms and make smarter heating and cooling decisions for you. They keep you comfortable while saving you money." +
				"Just as you can select which sensors participate in Comfort Settings, you can select which don’t participate." +                 
				"To configure your sensor participation settings, select Menu, Sensors, Sensor Name, Participation and select all of the Comfort Settings you want this sensor to participate in."			
			],
		'tip15': 
			[level:2, 
				text:"Your Nest Thermostat offers a simple way to turn the fan on whenever you want. For example, you can set the fan to run all night and turn off in the morning automatically. " +
				"Or you could schedule it to run for a few hours in the afternoon when the kids get home from school. You don’t need to turn on your heater or your air conditioner to use your fan, but you won’t be able to turn your fan on while your thermostat is Off." +
				"You can make the settings changes at the thermostat unit or using the Nest App."
			],
		'tip16': 
			[level:2, 
				text:"You should change the default Sleep climate at Nest according to your Sleep schedule and use energy efficient settings as indicated on the energy.gov website." +
				"You can save as much as 10% to 20% a year on heating and cooling by doing a temperature setback or setforward of 7 degrees Fahrenheit or 3 degrees Celsius for 8 hours a day from its normal setting." 
			], 
		'tip17': 
			[level:2, 
				text:"You should set the eco settings at Nest according to your work schedule and use energy efficient settings as indicated on the energy.gov website." +
				"You can save as much as 10% to 20% a year on heating and cooling by doing a temperature setback or setforward of 7 degrees Fahrenheit or 3 degrees Celsius for 8 hours a day from its normal setting." 
			],
		'tip18': 
			[level:2, 
				text:"If you're away for a long period of time, you should use the Vacation mode. The Vacation feature on your Nest helps you conserve energy while you are away for extended periods and ensures your home is at a comfortable temperature before you arrive home. " +
				"The Vacation feature is smart, which means you can leave your normal schedule as-is, and your Nest will automatically return to it when your vacation ends. You can even create multiple vacations, so you can program it right when you book your vacations, and not have to think about it again." 
			],
		'tip20': 
			[level:3, 
				text:"To maintain comfort inside the home, the humidity inside the home must be controlled along with the temperature of the air. Consider a warm summers day, if the humidity inside the home increases, the air will hold more heat " +
				"and the air conditioner will need to run longer to offset both the humidity and the warm air. The same principle applies for heating as it takes more time to heat air heavy with humidity."
			],
		'tip21': 
			[level:3, 
				text:"To maintain comfort inside the home, the humidity inside the home must be controlled along with the temperature of the air. As you don't have a dehumidifer connected to Nest, you can use " +
					"the A/C to dehumidify your home by using the A/C Overcool option. In the thermostat menu, Select Settings, Installation Settings, Thresholds, and finally AC Overcool Max." +
					"The amount the system will cool your house beyond the desired temperature in the currently used Comfort Setting is determined by the desired temperature itself."                    
			],
		'tip22': 
			[level:3, 
				text:"To maintain comfort inside the home, the humidity inside the home must be controlled along with the temperature of the air. Your dehumidifier is connected to your Nest, you should then use " +
					"ecomatiq homes specialized MonitorAndSetNestHumidity smartapp to activate automatically your dehumidifier when required. Contact ecomatiq homes.com for more details."
			],
		'tip31': 
			[level:3, 
				text:"The outdoor temperature has been fairly constant, yet your A/C or Furnace runtime has increased in the last day." +
				"Try to use the ecomatiq homes windowOrDoorOpen smartapp to avoid cooling or heating your home when a door or window contact is open for too long." +
				"For more comfort, you should also try to heat or cool your home using the zoned heating and cooling smartapps available at www.ecomatiq homes.com."
		],
		'tip32': 
			[level:3, 
				text:"To reduce the number of cooling cycles and cooling runtime, you should try " +
					"ecomatiq homes zoned heating and cooling smartapps to activate automatically a damper or big fan or evaporative cooler switch to enable free cooling." +
					"Contact ecomatiq homes.com for more details."
			],
		'tip40': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Heat instead of your main HVAC component."
			],
		'tip41': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Heat instead of your main HVAC component."
			],
		'tip42': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Heat instead of your main HVAC component."
			],
		'tip43': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Heat instead of your main HVAC component."
			],
		'tip44': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Heat instead of your main HVAC component."
			],
		'tip45': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Cool instead of your main HVAC component."
			],
		'tip46': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Cool instead of your main HVAC component."
			],
		'tip47': 
			[level:4, 
				text:"Over time, you may want to keep track your HVAC efficiency and this smartapp can give you some metrics about it.  Consult your manual and make sure that your HVAC " +
				"can reach its balance point. Your HVAC efficiency changes with outdoor temperature. The balance point is the temperature at which it is more efficient to run Auxiliary Cool instead of your main HVAC component."
			]
	]
	def recommendation = tips.getAt(tip)
	traceEvent(settings.logFilter,"get_recommendation_text>got ${tip}'s text from tips table", settings.trace)
	return recommendation
}

private void initialize_tips() {
	for (int i=1;i<=get_MAX_TIPS();i++) {    
		def attribute = "tip${i}Text"    
		sendEvent name: attribute, value: "",displayed: (settings.trace?:false)
		attribute = "tip${i}Level"
		sendEvent name: attribute, value: "",displayed: (settings.trace?:false)
		attribute = "tip${i}Solution"
		sendEvent name: attribute, value: "",displayed: (settings.trace?:false)
	}        

}

private boolean isWeekday() {
	Calendar myDate = Calendar.getInstance()
	int dow = myDate.get (Calendar.DAY_OF_WEEK)
	boolean isWeekday = ((dow >= Calendar.MONDAY) && (dow <= Calendar.FRIDAY))
	return isWeekday
}

void resetTips() {
	traceEvent(settings.logFilter,"resetTips>begin with state?.tips=${state.tips}, about to reset the state variable",settings.trace)
	state?.tips=[] // reset the state variable which saves all tips previously given
}

void getTips(level=1) {
	if (state?.tips==null) {resetTips()}
	state?.currentTip=0
    
	def scale = state?.scale
	def tipsAlreadyGiven = (state?.tips != [])? state.tips: []
	def maxTips=get_MAX_TIPS()    
	traceEvent(settings.logFilter,"getTips>begin with level=$level",settings.trace)
	traceEvent(settings.logFilter,"getTips>tipsAlreadyGiven=$tipsAlreadyGiven,state.tips=${state?.tips}",settings.trace)
	initialize_tips()   
	def recommendation=null
	def attribute    
	boolean isWeekday=isWeekday()    
    
	float MAX_DEVIATION_TEMPERATURE= (scale=='F')?0.6:0.3
	float MAX_DIFFERENCE_TEMPERATURE=(scale=='F')?3:1.5
	float MAX_DEVIATION_OUTDOOR_TEMP=(scale=='F')?10:5
	float MIN_DEVIATION_COOLING_SETPOINT= (scale=='F')?6:3
	float MIN_DEVIATION_HEATING_SETPOINT= (scale=='F')?6:3
	int MAX_HUMIDITY_LEVEL=55
	int HUMIDITY_DIFF_ALLOWED=3
	int MILLISECONDS_PER_HOUR=(60 * 60 * 1000)    
	int MAX_HOLD_THRESHOLD=3
	int MAX_HEATING_CYCLE=10
	int MAX_COOLING_CYCLE=10
    
	def countHeating=0, countCooling=0,countAway=0,countHome=0,awayDuration=0,countHolds=0
	def component

	String mode = device.currentValue("thermostatMode")    
	Date endDate= new Date()
	Date startDate = endDate -1
	Date aWeekAgo=endDate-7
	if ((level == 1) ||  (level == 0)) {
    
		def minCoolingSetpoint,maxCoolingSetpoint
		def minHeatingSetpoint,maxHeatingSetpoint
		def coolingValuesSet=get_stats_attribute("coolingSetpoint",startDate,endDate,'values')
		def heatingValuesSet=get_stats_attribute("heatingSetpoint",startDate,endDate,'values')
    
		if (mode in ['cool','auto', 'off']) {    
			countCooling=get_stats_attribute("thermostatOperatingState",startDate,endDate,'count',false,'cooling')
			minCoolingSetpoint=coolingValuesSet.min()    
			maxCoolingSetpoint=coolingValuesSet.max()    
		}        
		if (mode in ['heat','auto', 'off']) {
			countHeating=get_stats_attribute("thermostatOperatingState",startDate,endDate,'count',false,'heating')
			minHeatingSetpoint=heatingValuesSet.min()    
			maxHeatingSetpoint=heatingValuesSet.max()    
		}    
		countHolds=get_stats_attribute("programScheduleName",startDate,endDate,'count',false,'hold')  
		countAway=get_stats_attribute("thermostatMode",startDate,endDate,'count',false,'eco')  
		def outdoorTemp = device.currentValue("weatherTemperature")
		int currentIndoorHum = device.currentValue("humidity")            
		def currentTemp = device.currentValue("temperature")            
    
		if (settings.trace) {    
			traceEvent(settings.logFilter,"getTips>currentTemp = $currentTemp", settings.trace)
			traceEvent(settings.logFilter,"getTips>currentIndoorHum = $currentIndoorHum", settings.trace)            
			traceEvent(settings.logFilter,"getTips>outdoorTemp=$outdoorTemp", settings.trace)
			traceEvent(settings.logFilter,"getTips>countCooling=$countCooling", settings.trace)
			traceEvent(settings.logFilter,"getTips>countHeating=$countHeating", settings.trace)
			traceEvent(settings.logFilter,"getTips>countHolds=$countHolds", settings.trace)
			traceEvent(settings.logFilter,"get_tips>coolingSetPoint values=$coolingValuesSet", settings.trace)
			traceEvent(settings.logFilter,"get_tips>heatingSetPoint values=$heatingValuesSet", settings.trace)
			traceEvent(settings.logFilter,"getTips>min coolingSetpoint=$minCoolingSetpoint", settings.trace)
			traceEvent(settings.logFilter,"getTips>min heatingSetpoint=$minHeatingSetpoint", settings.trace)
			traceEvent(settings.logFilter,"getTips>max coolingSetpoint=$maxCoolingSetpoint", settings.trace)
			traceEvent(settings.logFilter, "getTips>max heatingSetpoint=$maxHeatingSetpoint", settings.trace)
			traceEvent(settings.logFilter,"getTips>countEco=$countAway", settings.trace)
		}  
        
		if ((isWeekday) && (!tipsAlreadyGiven?.contains("tip02"))) {
			if ((mode in ['heat','auto', 'off']) && countHeating && !countAway) {
				recommendation= get_recommendation('tip02')
				tipsAlreadyGiven.add('tip02')                
				state?.currentTip=state?.currentTip +1             
				attribute ="tip${state?.currentTip}Text"                
				sendEvent name: attribute, value: "Observation: Your thermostat's minimum heating setpoint of ${minHeatingSetpoint} degrees could be decreased during workdays when away for work. Current tip is: " + recommendation.text,
					displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Level"                
				sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Solution"                
				sendEvent name: attribute, value: "tip02,heating",displayed: (settings.trace?:false)
			}        
			if (((!tipsAlreadyGiven?.contains("tip02")) && countCooling) && !countAway) {
				recommendation= get_recommendation('tip02')
				tipsAlreadyGiven.add('tip02')                
				state?.currentTip=state?.currentTip +1             
				attribute ="tip${state?.currentTip}Text"                
				sendEvent name: attribute, value: "Observation: Your thermostat's maximum cooling setpoint of ${maxCoolingSetpoint} degrees could be increased during workdays when away for work. Current tip is: " + recommendation.text,
					displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Level"                
				sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Solution"                
				sendEvent name: attribute, value: "tip02,cooling",displayed: (settings.trace?:false)
			}        
		} /* end of tip02 logic */
/*        
		if ((state?.currentTip < maxTips) && (data.thermostatCount > 1) && (!tipsAlreadyGiven?.contains("tip03"))) {
			recommendation= get_recommendation('tip03')
			tipsAlreadyGiven.add('tip03')                
			state?.currentTip=state?.currentTip +1             
			attribute ="tip${state?.currentTip}Text"                
			sendEvent name: attribute, value: "Observation: It seems that you have ${data.thermostatCount} Nest thermostats at your location." + recommendation.text,
				displayed: (settings.trace?:false)
			attribute ="tip${state?.currentTip}Level"                
			sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
			attribute ="tip${state?.currentTip}Solution"                
			sendEvent name: attribute, value: "tip03",displayed: (settings.trace?:false)
		}	  
           
		if ((state?.currentTip < maxTips) && !tipsAlreadyGiven?.contains("tip04") ) {
			if ((countHeating) && (countHeating.toInteger() >= MAX_HEATING_CYCLE)) {
				recommendation= get_recommendation('tip04')
				tipsAlreadyGiven.add('tip04')                
				state?.currentTip=state?.currentTip +1             
				attribute ="tip${state?.currentTip}Text"                
				sendEvent name: attribute, value: "Observation: There were ${countHeating} heating cycles in the last 24 hours. Current tip is: " + recommendation.text,
					displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Level"                
				sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Solution"                
				sendEvent name: attribute, value: "tip04,heating",displayed: (settings.trace?:false)
			}
            
            
			if ((state?.currentTip < maxTips) && ((countCooling) && (countCooling.toInteger() >= MAX_COOLING_CYCLE))) {
				recommendation= get_recommendation('tip04')
				tipsAlreadyGiven.add('tip04')                
				state?.currentTip=state?.currentTip +1             
				attribute ="tip${state?.currentTip}Text"                
				sendEvent name: attribute, value: "Observation: There were ${countCooling} cooling cycles in the last 24 hours. Current tip is: " + recommendation.text,
					displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Level"                
				sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
				attribute ="tip${state?.currentTip}Solution"                
				sendEvent name: attribute, value: "tip04,cooling",displayed: (settings.trace?:false)
			}                 
		}        
		if ((state?.currentTip < maxTips) && (countHolds >= MAX_HOLD_THRESHOLD) && (!tipsAlreadyGiven?.contains("tip05"))) {
			recommendation= get_recommendation('tip05')
			tipsAlreadyGiven.add('tip05')                
			state?.currentTip=state?.currentTip +1             
			attribute ="tip${state?.currentTip}Text"                
			sendEvent name: attribute, value: "Observation: Your thermostat has been on hold ${countHolds} times during the day.  Current tip is: " + recommendation.text,
				displayed: (settings.trace?:false)
			attribute ="tip${state?.currentTip}Level"                
			sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
			attribute ="tip${state?.currentTip}Solution"                
			sendEvent name: attribute, value: "tip05",displayed: (settings.trace?:false)
		} 
  */
        
		if ((state?.currentTip < maxTips) && (countHolds >= MAX_HOLD_THRESHOLD) && (!tipsAlreadyGiven?.contains("tip06"))) {
			recommendation= get_recommendation('tip06')
			tipsAlreadyGiven.add('tip06')                
			state?.currentTip=state?.currentTip +1             
			attribute ="tip${state?.currentTip}Text"                
			sendEvent name: attribute, value: "Observation: Your thermostat has been on hold ${countHolds} times during the day.  Current tip is: " + recommendation.text,
				displayed: (settings.trace?:false)
			attribute ="tip${state?.currentTip}Level"                
			sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
			attribute ="tip${state?.currentTip}Solution"                
			sendEvent name: attribute, value: "tip06",displayed: (settings.trace?:false)
		} /* end of tip06 logic */
        
		if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip07")) && (countCooling)) {
			recommendation= get_recommendation('tip07')
			tipsAlreadyGiven.add('tip07')                 
			state?.currentTip=state?.currentTip +1             
			attribute ="tip${state?.currentTip}Text"                
			sendEvent name: attribute, value: "Observation: Your Nest thermostat is currently in cool mode and there has been $countCooling holds. Current tip is: " + recommendation.text,
				displayed: (settings.trace?:false)                
			attribute ="tip${state?.currentTip}Level"                
			sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
		}	
		state?.tips=tipsAlreadyGiven    
		traceEvent(settings.logFilter,"getTips>end Level 1 with tipsAlreadyGiven=$tipsAlreadyGiven,state.tips=${state?.tips}",settings.trace)
	} /* end of level 1 */    
	    

	if ((state?.currentTip < maxTips) && ((level == 2) ||  (level == 0))) {
		tipsAlreadyGiven=get_tips_level2()    
		state?.tips = tipsAlreadyGiven 
	}  
    
    
	traceEvent(settings.logFilter,"getTips>end with tipsAlreadyGiven=$tipsAlreadyGiven,state.tips=${state?.tips}",settings.trace)

}

private def get_tips_level2() {
	def tipsAlreadyGiven = (state?.tips != [])? state.tips: []
	traceEvent(settings.logFilter,"get_tips_level2>begin with tipsAlreadyGiven=$tipsAlreadyGiven,state.tips=${state?.tips}",settings.trace)
	def scale = state?.scale
	def maxTips=get_MAX_TIPS()    
	def countHeating=0, countCooling=0,awayDuration=0,countHolds=0
	def component

	def recommendation=null
	def attribute    
	boolean isWeekday=isWeekday()    
    
	float MAX_DEVIATION_TEMPERATURE= (scale=='F')?0.6:0.3
	float MAX_DIFFERENCE_TEMPERATURE=(scale=='F')?3:1.5
	int MAX_HEATING_CYCLE=10
	int MAX_COOLING_CYCLE=10
	int MAX_COOLING_MINUTES_TIME=600   
	int MAX_HEATING_MINUTES_TIME=300    
	float MAX_DEVIATION_OUTDOOR_TEMP=(scale=='F')?10:5
	int MILLISECONDS_PER_HOUR=(60 * 60 * 1000)    
	int MIN_USUAL_SLEEP_DURATION=7
	int MIN_USUAL_AWAY_DURATION=4 
	int MAX_USUAL_AWAY_DURATION=20
	float MINIMUM_FAN_RUNTIME=400    
	Date endDate= new Date()
	Date startDate = endDate -1
	Date aWeekAgo=endDate-7
    

	String mode = device.currentValue("thermostatMode")    
	int currentIndoorHum = device.currentValue("humidity")            
	def currentTemp = device.currentValue("temperature")            
	def outdoorTemp = device.currentValue("weatherTemperature")
	def minCoolingSetpoint,maxCoolingSetpoint
	def minHeatingSetpoint,maxHeatingSetpoint
	def coolingValuesSet=get_stats_attribute("coolingSetpoint",startDate,endDate,'values')
	def heatingValuesSet=get_stats_attribute("heatingSetpoint",startDate,endDate,'values')
    
	if (mode in ['cool','auto', 'off']) {    
		countCooling=get_stats_attribute("thermostatOperatingState",startDate,endDate,'count',false,'cooling')
		minCoolingSetpoint=coolingValuesSet.min()    
		maxCoolingSetpoint=coolingValuesSet.max()    
	}        
	if (mode in ['heat','auto', 'off']) {
		countHeating=get_stats_attribute("thermostatOperatingState",startDate,endDate,'count',false,'heating')
		minHeatingSetpoint=heatingValuesSet.min()    
		maxHeatingSetpoint=heatingValuesSet.max()    
	}    
	def awayDates=get_stats_attribute("thermostatMode",startDate,endDate,'dates',false,'eco')
	def countAway=get_stats_attribute("thermostatMode",startDate,endDate,'count',false,'eco')  
	def countHome=get_stats_attribute("structureAway",startDate,endDate,'count',false,'Home')  
	if (countHome && countAway) {    
		def homeMaxDate=homeDates.max()
		def awayMaxDate=awayDates.max()
		awayDuration=(((homeMaxDate.toLong() - awayMaxDate.toLong()).abs()) / MILLISECONDS_PER_HOUR).toFloat().round(1)    
	}
	def avgTemperature=get_stats_attribute("temperature",startDate,endDate,'avg')
	def devTemperature=get_stats_attribute("temperature",startDate,endDate,'deviation')
    
	if (settings.trace) {    
		traceEvent(settings.logFilter,"get_tips_level2>currentTemp = $currentTemp", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>avgTemperature=$avgTemperature", settings.trace)    
		traceEvent(settings.logFilter,"get_tips_level2>currentIndoorHum = $currentIndoorHum", settings.trace)           
		traceEvent(settings.logFilter,"get_tips_level2>outdoorTemp=$outdoorTemp", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>countCooling=$countCooling", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>countHeating=$countHeating", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>coolingSetPoint values=$coolingValuesSet", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>heatingSetPoint values=$heatingValuesSet", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>min heatingSetpoint=$minHeatingSetpoint", settings.trace)
		traceEvent(settings.logFilter,"get_tips_level2>min coolingSetpoint=$minCoolingSetpoint", settings.trace)
		traceEvent(settings.logFilter,"get_tips_level2>max coolingSetpoint=$maxCoolingSetpoint", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>max heatingSetpoint=$maxHeatingSetpoint", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>awayDates=$awayDates", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>awayDuration=$awayDuration", settings.trace)
		traceEvent(settings.logFilter, "get_tips_level2>homeDates=$homeDates", settings.trace)
	}   
	if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip10")) && (countHeating && maxHeatingSetpoint && minHeatingSetpoint) && ((maxHeatingSetpoint.toFloat() -minHeatingSetpoint.toFloat()) <= MIN_DEVIATION_HEATING_SETPOINT)) {
		recommendation= get_recommendation('tip10')
		tipsAlreadyGiven.add('tip10')                
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: There is a not a big difference between your thermostat's minimum heating setpoint of ${minHeatingSetpoint} and your maximum heating setpoint of ${maxHeatingSetpoint} degrees in the last 24 hours. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Solution"                
		sendEvent name: attribute, value: "tip10,heating",displayed: (settings.trace?:false)
	}        
	if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip10")) && (countCooling && maxCoolingSetpoint && minCoolingSetpoint) && ((maxCoolingSetpoint.toFloat() -minCoolingSetpoint.toFloat()) <= MIN_DEVIATION_COOLING_SETPOINT)) {
		recommendation= get_recommendation('tip10')
		tipsAlreadyGiven.add('tip10')                
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: There is a not a big difference between your thermostat's maximum cooling setpoint of ${maxCoolingSetpoint} and your minimum cooling setpoint of ${minCoolingSetpoint} degrees in the last 24 hours. Current tip is: " + recommendation.text,
		displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Solution"                
		sendEvent name: attribute, value: "tip10,cooling",displayed: (settings.trace?:false)
	} /* end of tip10 logic */
        
	if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip11")) && (devTemperature.toFloat() >=MAX_DEVIATION_TEMPERATURE)) {
		recommendation= get_recommendation('tip11')
		tipsAlreadyGiven.add('tip11')                 
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: Your average indoor temperature of ${avgTemperature} degrees has not been constant in the last 24 hours as the standard deviation is ${devTemperature} degrees. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)                
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
	}	

	if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip13")) && (devTemperature.toFloat() >=MAX_DEVIATION_TEMPERATURE)) {
		recommendation= get_recommendation('tip13')
		tipsAlreadyGiven.add('tip13')                 
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: Your average indoor temperature of ${avgTemperature} degrees has not been constant in the last 24 hours as the standard deviation is ${devTemperature} degrees. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)                
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
	}	
	if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip15")) && (devTemperature.toFloat() >=MAX_DEVIATION_TEMPERATURE)) {
		recommendation= get_recommendation('tip15')
		tipsAlreadyGiven.add('tip15')                 
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: Your average indoor temperature of ${avgTemperature} degrees has not been constant in the last 24 hours as the standard deviation is ${devTemperature} degrees. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)                
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
	}	
	if ((isWeekday) && (state?.currentTip < maxTips) &&  (!tipsAlreadyGiven?.contains("tip17")) && (awayDuration) && (awayDuration.toFloat() < MIN_USUAL_AWAY_DURATION)) {
		recommendation= get_recommendation('tip17')
		tipsAlreadyGiven.add('tip17')                
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: Your Away program at Nest was used for approximately ${awayDuration} hour(s) in the last day " + 
			"which is below the usual Away schedule of ${MIN_USUAL_AWAY_DURATION} hours if you work 9-5. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Solution"                
		sendEvent name: attribute, value: "tip17",displayed: (settings.trace?:false)
	}  	/* end of tip17 logic */  	
        
	if ((state?.currentTip < maxTips) &&  (!tipsAlreadyGiven?.contains("tip18")) && (awayDuration) && (awayDuration.toFloat() > MAX_USUAL_AWAY_DURATION)) {
		recommendation= get_recommendation('tip18')
		tipsAlreadyGiven.add('tip18')                
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: Your Away program at Nest was used for approximately ${awayDuration} hour(s) in the last day " + 
			"which is above the usual Away schedule of ${MAX_USUAL_AWAY_DURATION} hours if you work 9-5. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Solution"                
		sendEvent name: attribute, value: "tip18",displayed: (settings.trace?:false)
	}  	/* end of tip18 logic */  	
	if ((state?.currentTip < maxTips) && (!tipsAlreadyGiven?.contains("tip20")) && (currentIndoorHum > (targetHumidity + HUMIDITY_DIFF_ALLOWED))) {
		recommendation= get_recommendation('tip20')
		tipsAlreadyGiven.add('tip20')                
		state?.currentTip=state?.currentTip +1             
		attribute ="tip${state?.currentTip}Text"                
		sendEvent name: attribute, value: "Observation: Your indoor humidity is currently ${currentIndoorHum}% " + 
			"which is well above your ideal indoor humidity of ${targetHumidity}% based on the current outdoor temperature of ${outdoorTemp} degrees. Current tip is: " + recommendation.text,
			displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Level"                
		sendEvent name: attribute, value: recommendation.level,displayed: (settings.trace?:false)
		attribute ="tip${state?.currentTip}Solution"                
		sendEvent name: attribute, value: "tip20",displayed: (settings.trace?:false)
 	}  		  
        
	state?.tips=tipsAlreadyGiven    
	traceEvent(settings.logFilter,"getTips>end Level 2 with tipsAlreadyGiven=$tipsAlreadyGiven,state.tips=${state?.tips}",settings.trace) 	     
	return tipsAlreadyGiven    

}   /* end getLevel 2 */  
 

private int find_ideal_indoor_humidity(outsideTemp) {
	def scale = state?.scale
	float outdoorTemp=(scale=='C')?outsideTemp.toFloat():fToC(outsideTemp.toFloat())	
	// -30C => 30%, at 0C => 45%

	int targetHum = 45 + (0.5 * outdoorTemp)
	return (Math.max(Math.min(targetHum, 60), 30))
}

private def toQueryString(Map m) {
	return m.collect { k, v -> "${k}=${URLEncoder.encode(v.toString())}" }.sort().join("&")
}


private def cToF(temp) {
	if (temp==null) return 0
	return (temp * 1.8 + 32)
}

private def cToF_Rounded(temp) {
	if (temp==null) return 0
	def temp_inF= temp * 1.8 + 32
	return temp_inF.toDouble().round()
}
private def fToC(temp) {
	if (temp==null) return 0
	return (temp - 32).toDouble() / 1.8
}
private def milesToKm(distance) {
	return (distance * 1.609344) 
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

private def ISODateFormat(dateString, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	String timezoneInString = new Date().format("zzz", myTimezone)
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz")
	Date aDate = sdf.parse(dateString.substring(0,19) + ' ' + timezoneInString)
	String ISODateInString =new Date(aDate.getTime()).format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	return ISODateInString
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

private Date formatLocalDate(dateInString, timezone='') {
	def myTimezone=(timezone)?TimeZone.getTimeZone(timezone):location.timeZone 
	if ((dateInString==null) || (dateInString.trim()=="")) {
		return (new Date())
	}    
	SimpleDateFormat ISODateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date ISODate = ISODateFormat.parse(dateInString.substring(0,19) + 'Z')
	return ISODate
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
	def heatingSetpointData = device.statesSince("heatingSetpoint", startOfWeek, [max:200])
	def coolingSetpointData = device.statesSince("coolingSetpoint", startOfWeek, [max:200])
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
	traceEvent(settings.logFilter,"retrieveDataForGraph>temperatureTable (size=${state?.temperatureTable.size()}) =${state?.temperatureTable}",settings.trace,GLOBAL_LOG_TRACE)  
	traceEvent(settings.logFilter,"retrieveDataForGraph>state.currentMode= ${state?.currentMode}",settings.trace)    
	traceEvent(settings.logFilter,"retrieveDataForGraph>heatingSetpointTable (size=${state?.heatingSetpointTable.size()}) =${state?.heatingSetpointTable}",settings.trace, GLOBAL_LOG_TRACE)  
	traceEvent(settings.logFilter,"retrieveDataForGraph>coolingSetpointTable (size=${state?.coolingSetpointTable.size()}) =${state?.coolingSetpointTable}",settings.trace, GLOBAL_LOG_TRACE)  
	traceEvent(settings.logFilter,"retrieveDataForGraph>humidityTable (size=${state?.humidityTable.size()}) =${state?.humidityTable}",settings.trace,GLOBAL_LOG_TRACE)  
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