package com.smarthome.api.model;

public class DeviceStatus {
	
	String pid;
	int bluetooth;
	int wifi;
	int volumn;
	int brightness;
	String date;
	String im;
	String cellular;
	String powerUpTime;
	String powerOffTime;
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getBluetooth() {
		return bluetooth;
	}
	public void setBluetooth(int bluetooth) {
		this.bluetooth = bluetooth;
	}
	public int getWifi() {
		return wifi;
	}
	public void setWifi(int wifi) {
		this.wifi = wifi;
	}
	public int getVolumn() {
		return volumn;
	}
	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}
	public int getBrightness() {
		return brightness;
	}
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public String getCellular() {
		return cellular;
	}
	public void setCellular(String cellular) {
		this.cellular = cellular;
	}
	public String getPowerUpTime() {
		return powerUpTime;
	}
	public void setPowerUpTime(String powerUpTime) {
		this.powerUpTime = powerUpTime;
	}
	public String getPowerOffTime() {
		return powerOffTime;
	}
	public void setPowerOffTime(String powerOffTime) {
		this.powerOffTime = powerOffTime;
	}
	
	
	
}
