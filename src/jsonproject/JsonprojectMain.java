/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonproject;

//package com.javasampleapproach.jsonxml;


//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
import org.json.JSONException;
//import org.json.JSONObject;
public class JsonprojectMain  {
        
    
	public static void main(String[] args)  throws IOException, JSONException{
	
            ConvertXMLTojson xmlTojson = new ConvertXMLTojson();
                               
            xmlTojson.convertXMLtojsonclass();
            xmlTojson.JconvertProduct1();
            xmlTojson.JconvertProduct2();
            xmlTojson.JconvertProduct3();
                                      
    }


   }