package com.bankle.common.asis.component.frimBanking;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

import com.bankle.common.asis.component.Box;
import com.bankle.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class GetSetData {

    private final byte[] BYTE_SOSI = {0x0e, 0x0f};    
	/*===========================================================================================*/
	/* 전문 LayOut 변수                                                                          */
	/*===========================================================================================*/
	private int             cntColumn = 0;	    // Layout Column Count
	String[]                arrName   = null;   // Layout Column Name
	int   []                arrLength = null;   // Layout Column Length
	String[]                arrType   = null;   // Layout Column Type	
	HashMap<String, byte[]> mapTLGM   = null;   // 전문 Layout 내용
	HashMap<String, String> mapTYPE   = null;   // 전문 Layout 타입
	
	/*===========================================================================================*/
	/* Function Name : setVariable    전문별 객체(Q2X0....) 초기화시 가장 먼저 호출. 변수 셋팅   */
	/* Parameter     : cnt            Layout 컬럼 Count                                          */
	/*===========================================================================================*/
	public void setVariable(int cnt) {
		
		cntColumn = cnt;		
		arrName   = new String[cnt];
		arrLength = new int   [cnt];
		arrType   = new String[cnt];		
		mapTLGM   = new HashMap<String, byte[]>();  // 전문 Layout 내용
		mapTYPE   = new HashMap<String, String>();  // 전문 Layout 타입
	}

	/*===========================================================================================*/
	/* Function Name : Getter                                                                    */
	/*===========================================================================================*/	
	public String[]                getArrName  () { return arrName;   }
    public int   []                getArrLength() { return arrLength; }
    public String[]                getArrType  () { return arrType;   }
    public HashMap<String, byte[]> getMapTLGM  () { return mapTLGM;   }
    public HashMap<String, String> getMapTYPE  () { return mapTYPE;   }

	/*===========================================================================================*/
	/* Function Name : setMap  셋팅된 Layout Array 정보를 기준으로 Map 셋팅                      */
	/*===========================================================================================*/    
    public void setMap() {
		for (int i=0; i < cntColumn; i++) {
			mapTLGM.put(arrName[i], initByte(arrLength[i]));  // Layout Map 셋팅  
			mapTYPE.put(arrName[i], arrType[i]            );	// Layout Type 셋팅
		}    
    }
    
    public byte[] initByte(int arrLen) {
        byte[] data = new byte[arrLen];
        // 실제데이터값이 채워지지 않은 배열은 공백으로 채운다.
        for (int i = 0; i < arrLen; i++) {
            data[i] = ' ';
        }
        return data;
    }
    /*===========================================================================================*/
    /* Function Name : readDataStream 전문내역을 읽어 HashMap 에 할당                            */
    /*===========================================================================================*/	
    public void readDataStream(InputStream stream) {
    	
    	String varName = "";
    	
        try {
        	
        	for (int i=0; i < cntColumn; i++) {        		
        	
        		varName = arrName[i];        		
        		stream.read(getByte( varName ) , 0, getLength( varName));
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public int    getLength(String varName) { return ((byte[])mapTLGM.get(varName)).length; }	
	public String getType  (String varName) { return          mapTYPE.get(varName);         }
	public byte[] getByte  (String varName) { return  (byte[])mapTLGM.get(varName);         }
	
	/*===========================================================================================*/
	/* Function Name ; setData      전문 정보 Setter                                             */
	/* Parameter     : byteArray => 전문 byte Array 정보                                         */
	/*                 str       => 전문에 byte Array에 담을 데이터                              */	
	/*===========================================================================================*/
	public void setData(String varName, String strValue) throws Exception {
	    
        byte[] byteData     = getByte(varName );
        String valTP        = StringUtil.nvl(getType(varName));
		int    byteLen      = byteData.length;	
        byte[] bytes        = null;
        byte[] oriByteData  = null;

        if (!StringUtils.hasText(strValue)) {
            if ("N".equals(valTP)) { strValue = "0"; } 
            else                   { strValue = " "; }
        }
        
        if ("K".equals(valTP)) { byteLen = byteLen - 2;}
        oriByteData = new byte[byteLen];
       
        if ("K".equals(valTP)) { bytes = strValue.getBytes("MS949"); } 
        else                   { bytes = strValue.getBytes();        }
        
        if (!"N".equals(valTP)) {
            
            // 실제데이터값의 바이트배열길이
            int endIdx = 0;

            // 실제데이터 바이트배열 설정
            if (byteLen < bytes.length) endIdx = byteLen;      // 실제데이터배열이 필드 배열보다 클경우
            else                        endIdx = bytes.length; // 필드의 배열이 실제데이터배열보다 작을 경우
            
            if ("K".equals(valTP)) {
                // 필드배열에 실제데이터배열값으로 채운다.
                for (int i = 0; i < endIdx; i++) {
                    oriByteData[i] = bytes[i];
                }
                
                // 실제데이터값이 채워지지 않은 배열은 공백으로 채운다.
                for (int j = endIdx; j < byteLen; j++) {
                    oriByteData[j] = ' ';
                }
                
                System.arraycopy(BYTE_SOSI  , 0, byteData, 0                     , 1                 );  // SO            
                System.arraycopy(oriByteData, 0, byteData, 1                     , oriByteData.length);  // Data
                System.arraycopy(BYTE_SOSI  , 1, byteData, 1 + oriByteData.length, 1                 );  // SI
                
            } else {
                // 필드배열에 실제데이터배열값으로 채운다.
                for (int i = 0; i < endIdx; i++) {
                    byteData[i] = bytes[i];
                }
                
                // 실제데이터값이 채워지지 않은 배열은 공백으로 채운다.
                for (int j = endIdx; j < byteLen; j++) {
                    byteData[j] = ' '; 
                }                
            }
            
        } else { 
            
            strValue = StringUtil.lpad(new String(bytes), byteLen, "0");
            bytes    = strValue.getBytes();        
            
            for (int i = 0; i < byteLen; i++) {
                byteData[i] = bytes[i];
            }            
        }
        
        
    }
		
    /*===========================================================================================*/
    /* Function Name ; getData      전문 정보 Getter                                             */
    /* Parameter     : byteArray => 전문 byte Array 정보                                         */
    /*                 valTP     => 데이터 타입 [ N : 숫자, S : 문자, K : 한글포함문자 ]         */
    /*                 lenKeyYN  => 전문 길이 유지 여부 [ Y : 길이유지, N : 데이터만 ]           */
	/*                 tgtDsc    => get 사용 대상 (K L KOS [default], B : BANK)                  */
    /*===========================================================================================*/
	public String getOrgnData(String varName) throws Exception {
	    return getData(varName, "N");
	}	
	public String getData(String varName, String utf8YN) throws Exception {
		
        byte[] byteData = getByte(varName);
        String valTP    = StringUtil.nvl(getType(varName));
        
        String str      = "";
 
//        byte[] BYTE_SO  = {0x0e};
//        byte[] BYTE_SI  = {0x0f};
//      byte[] copyByteData = new byte[byteData.length];
        
		if (byteData != null) {
		    
		    if ("K".equals(valTP)) {

                if ("Y".equals(utf8YN)) { str = new String(byteData, "UTF-8"); } 
                else                    { str = new String(byteData, "MS949"); }
                
//                str = str.replace(new String(BYTE_SO), " ").replace(new String(BYTE_SI), " ").trim();
//                str = str.replace(new String(BYTE_SO), " ").replace(new String(BYTE_SI), " ");                
		        
//		        System.arraycopy(byteData, 1, copyByteData, 0, byteData.length-2);  // SO SI  제거
//    		    if ("Y".equals(utf8YN)) { str = new String(copyByteData, "UTF-8"); } 
//    		    else                    { str = new String(copyByteData, "MS949"); }
    		    
		    } else {
//		        str = (new String(byteData)).trim();
		        str = new String(byteData);
		    }
        }
        
        return str;
    }
	
    /*===========================================================================================*/
	/* Function Name : print  Layout 정보 출력                                                   */
    /*===========================================================================================*/
	public String toString(String logYN) {

        StringBuffer sb = new StringBuffer();
        
    	try {
        
    		String varName = "";
    		
    		for (int i=0; i < cntColumn; i++) {
    			varName  = arrName[i];
    			
                if ("N".equals(logYN)) {
                    sb.append( getData(varName, "N") );                    
                    
                } else {
                    
                    sb.append(   StringUtil.rpad(varName                , 15, " ") + " : "
                               + StringUtil.lpad(getType  ( varName )+"",  1, "0") + "\t"
                               + StringUtil.lpad(getLength( varName )+"",  4, "0") + "\tData ["
                               + getData(varName, "N")                             + "]\n");                    
                }
    		}
    	} catch (Exception Ex) {
    		Ex.printStackTrace();
    	}
    	
    	if ("Y".equals(logYN)) log.info("\nSocket Message : \n[\n" + sb.toString() + "]");
    	
        return sb.toString();                                                                                                                                                    
    }

	public byte[] toAllByte() throws Exception {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );

	    String varName = "";
	    
        for (int i=0; i < cntColumn; i++) {             
            
            varName = arrName[i];

            outputStream.write(getByte(varName));
        }
	    
	    byte[] data  = outputStream.toByteArray();
	    return data;
	}
	
    /*===========================================================================================*/
    /* Function Name : copyToBox 전문내용을 Box에 복사                                           */
    /* Parameter     : paramBox : 복사한 정보를 담을 Box 객체                                    */ 
    /*===========================================================================================*/
    public void copyToBox(Box paramBox) throws Exception {

        for (int i=0; i < cntColumn; i++) {
            String varName  = arrName[i];
            String varValue = getOrgnData(varName);
            
            paramBox.put(varName, varValue);
        }
    }	

    /*===========================================================================================*/
    /* Function Name : copyToMap 전문내용을 Box에 복사                                           */
    /* Parameter     : paramMap : 복사한 정보를 담을 Box 객체                                    */ 
    /*===========================================================================================*/
    public void copyToMap(HashMap<String, Object> paramMap) throws Exception {

        for (int i=0; i < cntColumn; i++) {
            String varName  = arrName[i];
            String varValue = getOrgnData(varName);
            
            paramMap.put(varName, varValue);
        }
    }   

    public HashMap<String, Object> getDataMap() throws Exception {

        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        String varName  = "";
        String valTP    = "";
        String varValue = "";
        
        byte[] BYTE_SO  = {0x0e};
        byte[] BYTE_SI  = {0x0f};
        
        for (int i=0; i < cntColumn; i++) {
            varName  = arrName[i];
            valTP    = StringUtil.nvl(getType(varName));
            varValue = getOrgnData(varName);
            
            if ("K".equals(valTP)) {
                
                varValue = varValue.replace(new String(BYTE_SO), "").replace(new String(BYTE_SI), " ").trim();
            }
            
            paramMap.put(varName, varValue);
        }
        return paramMap;
    }    
	/*===========================================================================================*/
	/* Function Name ; isUTF8       byte Array 에 담긴 데이터의 UTF-8 여부 판별                  */
	/* Parameter     : byteArray => CharSet 을 판별 할 데이터 byte Array 정보                    */
	/* Return        : boolean   => byte Array 에 담긴 데이터 CharSet 이                         */
	/*                              UTF-8 이면 true, 아니면 false                                */
	/*===========================================================================================*/
	public static final boolean isUTF8(byte[] inBytes) {
	    
	    final String strConvert = new String (inBytes, StandardCharsets.UTF_8);
        final byte[] outBytes   = strConvert.getBytes(StandardCharsets.UTF_8);
        return Arrays.equals(inBytes, outBytes);                 
    }	
}
