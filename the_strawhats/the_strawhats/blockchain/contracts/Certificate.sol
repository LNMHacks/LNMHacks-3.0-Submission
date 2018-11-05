pragma solidity ^0.4.21;

contract Certificate {

    struct user_data {
        string[10] certificates;
        uint certificate_count;
        address[10] sender;
    }
    
    
    mapping (address => user_data) public users_mapping;
    
    
    function  insert_certificate (string _certificate_data, address addrs) public {
       uint count = users_mapping[addrs].certificate_count;
       users_mapping[addrs].certificates[count] = _certificate_data;
       users_mapping[addrs].sender[count] = msg.sender;
        users_mapping[addrs].certificate_count ++;
    }
    
    function get_certificate_self(address addrs) constant public returns(string) {
        uint count=users_mapping[msg.sender].certificate_count;
        string  data;
        bool val = false;
        for (uint i=0; i<=count;i++) {
            address _address= users_mapping[msg.sender].sender[i];
            if (_address== addrs){
                data=users_mapping[msg.sender].certificates[i];
                val=true;
                break;
            }
            
        }
        if(val== false){return "No certificate found";}
        return data;
    }
    
    function get_count(address addrs) constant public returns (uint) {
       uint count=users_mapping[addrs].certificate_count;
       uint index = --count;
       return index;
    }
    
    function index_certificate(address addrs, uint index) constant returns(string){
        string data = users_mapping[addrs].certificates[index];
        return data;
    }
    
}