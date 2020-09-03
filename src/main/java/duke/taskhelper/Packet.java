/**
 * Organises data from input string into distinct data types and structure.
 * For scalability purposes, a hash table is implemented to map param type to its corresponding params.
 */
package duke.taskhelper;
import duke.dukehelper.*;
import java.util.Hashtable;
import java.util.Set;

public class Packet {
    private String packetType;
    private String packetPayload;
    private Hashtable paramMap;
    // Constructors
    public Packet(String taskType, String taskName){
        this.packetType = taskType;
        this.packetPayload = taskName;
        this.paramMap = new Hashtable();
    }
    public Packet(String taskType){
        this(taskType, null);
    }

    // Param type refers to /.* (eg. /a)
    // Param refers to string following the param type
    public String getParam(String paramType){
        if (!paramMap.containsKey(paramType)){
            return null;
        } else {
            return (String) paramMap.get(paramType);
        }
    }
    public Set getParamTypes() {
        return paramMap.keySet();
    }
    public String getPacketType(){
        return this.packetType;
    }
    public String getPacketPayload(){
        return this.packetPayload;
    }
    public Hashtable getParamMap(){
        return (Hashtable)this.paramMap.clone();
    }
    public void setPacketPayload(String payload){
        this.packetPayload = (payload.equals("") ? null : payload);
    }
    public Constants.Error addParamToMap(String paramType, String paramString){
        this.paramMap.put(paramType, paramString);
        return Constants.Error.NO_ERROR;
    }
}