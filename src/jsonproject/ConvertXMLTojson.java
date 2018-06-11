package jsonproject;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.json.simple.JSONArray;
import java.util.*;
import java.util.logging.Logger;
/**
 *
 * @author Halim
 */


public class ConvertXMLTojson {

    private static final int PRETTY_PRINT_INDENT_FACTOR = 4;
    private static final String encoding="UTF-8";
    
    private static URL urlp1 = null;
    private static URL urlp2 = null;
    private static URL urlp3 = null;

    private static InputStream inputS1 = null;   
    private static InputStream inputS2 = null;   
    private static InputStream inputS3 = null;   

    private static JSONObject jObj = null;
    private static JSONObject jObjChild = null;
    private static JSONArray jArray = null;  
 
    private static Integer count = 0;
    
    private static ArrayList pList;
    private static String name, id, price, currency, bestBefore;  
        
    public void convertXMLtojsonclass() throws IOException, JSONException{
   
        jObj = new JSONObject();
        jArray = new JSONArray();
                
        pList = new ArrayList<Products>();        

        urlp1 = JsonprojectMain.class.getClassLoader().getResource("products_package\\products1.xml");
        urlp2 = JsonprojectMain.class.getClassLoader().getResource("products_package\\products2.xml");
        urlp3 = JsonprojectMain.class.getClassLoader().getResource("products_package\\products3.xml");

        inputS1 = urlp1.openStream();
        inputS2 = urlp2.openStream();
        inputS3 = urlp3.openStream();
}

    public void JconvertProduct1() throws IOException, JSONException {
                      
        String jsonPrettyPrintString,xmlData; 
        JSONObject xmlJSONObj, productchild;
        
        xmlData= IOUtils.toString(inputS1, encoding);
        xmlJSONObj = XML.toJSONObject(xmlData);
      
        JSONObject responseObj = xmlJSONObj.getJSONObject("Response");
        JSONObject productsObj = responseObj.getJSONObject("Products");
        
        org.json.JSONArray product = productsObj.getJSONArray("Product");
        
        for(int i = 0; i<product.length();i++)
        {
            productchild = (JSONObject)product.get(i);

            try {
                 id = productchild.getString("Id");
            } catch (Exception e) {
                 id ="0";
            }
            
            name = productchild.getString("Name");
            currency = productchild.getString("Currency");
            price = productchild.get("Price").toString();  
            bestBefore = productchild.getString("BestBefore");
                   
            
            ctrlListbyNameAndAddPrdt(pList); 
                        
        }
               
    }

    public void JconvertProduct2() throws IOException, JSONException {
        
        String xmlData; 
        JSONObject xmlJSONObj;
        
        xmlData= IOUtils.toString(inputS2, encoding);
        xmlJSONObj = XML.toJSONObject(xmlData);
      
        JSONObject responseObj = xmlJSONObj.getJSONObject("Response");
        JSONObject productsObj = responseObj.getJSONObject("Products");
        
        org.json.JSONArray productArry = productsObj.getJSONArray("Product");
        
        for(int i = 0; i<productArry.length();i++)
        {

            JSONObject arrayObj = (JSONObject)productArry.get(i);
            JSONObject priceObj = arrayObj.getJSONObject("Price");
            JSONObject bestbeforeObj = arrayObj.getJSONObject("BestBefore");

            try {
                 id = arrayObj.getString("Id");
            } catch (Exception e) {
                 id ="0";
            }
            
            try { // content değeri atanmamış bir ürün var "0.01" atandı.
                price = priceObj.get("content").toString();
            } catch (Exception e) {
                price = "0.01";
            }            
                    
            name = arrayObj.getString("Name");
            currency= priceObj.getString("Currency");
                
            String oldFormat = "dd-MM-yyyy";
            String newFormat = "yyyy-MM-dd";

            String oldDateString = bestbeforeObj.getString("Date"); 
            if (oldDateString.trim().equals("")) 
                oldDateString="01-01-1992"; //tarih boş olanlara 01-01-1992
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
            Date date = null;
            try {
                date = sdf.parse(oldDateString);
            } catch (ParseException ex) {
                Logger.getLogger("ex date format!");
            }
            sdf.applyPattern(newFormat);
            newDateString = sdf.format(date);
            
            bestBefore = newDateString;
            
            ctrlListbyNameAndAddPrdt(pList);
            
	} 
       
    }
    public void JconvertProduct3() throws IOException, JSONException {
        
        String xmlData; 
        JSONObject productObj,product,xmlJSONObj;
        
        xmlData= IOUtils.toString(inputS3, encoding);
        xmlJSONObj = XML.toJSONObject(xmlData);

        JSONObject responseObj = xmlJSONObj.getJSONObject("Response");
        JSONObject productsObj = responseObj.getJSONObject("Products");
        org.json.JSONArray itemArray = productsObj.getJSONArray("Item");

        for(int i = 0; i<itemArray.length();i++)
        {
            productObj = (JSONObject)itemArray.get(i);

            product = productObj.getJSONObject("Product");

            try {
                 id = productObj.getString("Id");
            } catch (Exception e) {
                 id ="0";
            }
  
            name = product.getString("Name");
            currency = product.getString("Currency");
            price = product.get("Price").toString();
                          
            String oldformat = "dd.MM.yyyy";
            String newformat = "yyyy-MM-dd";

            String oldDateString = product.getString("BestBefore"); 
            if (oldDateString.trim().equals("")) 
                oldDateString="01.01.2001";
            String newDateString;

            SimpleDateFormat sdf = new SimpleDateFormat(oldformat);
            Date date = null;
            try {
                date = sdf.parse(oldDateString);
            } catch (ParseException ex) {
                Logger.getLogger("ex date format!");
            }
            sdf.applyPattern(newformat);
            newDateString = sdf.format(date);
            
            bestBefore = newDateString;
            
            ctrlListbyNameAndAddPrdt(pList); 
            
	}
        
        compareDate(pList);
        createJsnObj(pList);
        
    }
    public static void createJsnObj(ArrayList<Products> pList) throws JSONException {
       //şartlar sağlandıktan sonra elde edilen obje ile yeni bir json oluşturuldu.      
       
        for(int i = 0; i<pList.size();i++)
          {
            jObjChild =new JSONObject();
            
            jObjChild.put("id", pList.get(i).getId());
            jObjChild.put("name", pList.get(i).getName());              
            jObjChild.put("price",Double.valueOf(pList.get(i).getPrice()));          
            jObjChild.put("currency", pList.get(i).getCurrency());
            jObjChild.put("bestbefore", pList.get(i).getBestBefore());
            
            
            jArray.add(jObjChild);
               
          }

        jObj.put("Products",jArray);
         
        String jsonPrettyPrintString2 = jObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        System.out.println(jsonPrettyPrintString2);
          
      }

    private static void ctrlListbyNameAndAddPrdt(ArrayList<Products> pList) { 
        //ürünlerin tekil olması sağlandı.Aynı üründen birden fazla varsa bestBefore büyük olan tercih edilecek
        
        Boolean isSameName = false;
        
        String testPrice = price;
        String[] sayilar = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9","."};
        String result="";
       
        try {//eğer numerik format hatası verirse
            
		Double.parseDouble(testPrice);
	
        } catch (NumberFormatException numberFormatException) {
             for(int i=0;i<testPrice.length();i++)
            {
                String itemp = String.valueOf(testPrice.charAt(i));
                for(int j=0; j<sayilar.length;j++)
                {
                    if(sayilar[j].equals(itemp)){
                        result = result + itemp;
                    }
                }
            }
            price = result; 
	}
                
        String  priceValue = price;   
        String  currencyValue = currency;
            
        if(!currency.equals("EUR") && !currency.equals("") ){
            changetoEUR(currencyValue, priceValue);
        }
               
        for(int j=0; j< pList.size(); j++)
            {
                if(pList.get(j).getName().equals(name))
                {
                   if(pList.get(j).getBestBefore().compareTo(bestBefore)<0)
                   {
                       pList.get(j).setBestBefore(bestBefore);
                       pList.get(j).setId(id);
                       pList.get(j).setCurrency(currency);
                       pList.get(j).setPrice(price);
                   
                   }
                   isSameName = true;
                   break;
                }
            }
        if (!isSameName){
        
            pList.add(new Products(id, name, price, currency, bestBefore));
            
        }else isSameName = false;

       /* for(int i = 0; i<pList.size(); i++)
        {
            System.out.println("count : " + count +" i : "+ i + "--->" + pList.get(i));//liste elemanlarını yazdır        
        }*/
    }

    private static void compareDate(ArrayList<Products> pList) {
        //bastBefore değerine göre en güncelden en eskiye göre sıralandı.
        
        int a = pList.size();
          // Listeyi sıralıyoruz
		Collections.sort(pList, (Products p1, Products p2) -> {
                    String pBestBefore1 = p1.getBestBefore();
                    String pBestBefore2 = p2.getBestBefore();
                    return pBestBefore2.compareTo(pBestBefore1);
        });
    }
    
    private static void changetoEUR( String pCurrency, String pPrice) {
        //eUR olmaya bir birimde para felirse online birim çevirici API kullanılarak değiştirildi.
        //kullanılan API:  https://github.com/fixerAPI/fixer 
        
       try {
           
           // tanımlı key kullanıldı
            String url = "http://data.fixer.io/api/latest?access_key=2dea4f3f6560c4deb143950eaafca715";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int responseCode = con.getResponseCode();
            
            BufferedReader in =new BufferedReader(
                new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
           
            while ((inputLine = in.readLine()) != null) {
                
                response.append(inputLine);
                
            } in .close();
             
             JSONObject myresponse = new JSONObject(response.toString());
             JSONObject rates_object = new JSONObject(myresponse.getJSONObject("rates").toString());
       
             try{
               // if(pCurrency.equals("")) pCurrency ="EUR";
                Double nPrice = rates_object.getDouble(pCurrency);
                if(nPrice == 0) nPrice=0.01;
                if(pPrice.trim().equals(""))
                  pPrice="0.001";
                Double dprice = (Double)(Double.valueOf(pPrice)/nPrice);

                //çıkan sonuç virgülden sonra iki basamaklı gösterildi
                String pNPrice = (new DecimalFormat("##.##").format(dprice)).toString(); 
                pNPrice = pNPrice.replace(",", ".");
                price = pNPrice;
                currency = "EUR";
                
             }catch (NumberFormatException ex){
                 System.out.println("EUR cevirimi yapılamadı!");                 
             }
                         
        } catch(Exception e) {
    
            System.out.println(e);
  
        }
 
    }
    
}
