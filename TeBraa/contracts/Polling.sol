pragma solidity ^0.4.25;

contract Polling {
    
    struct Candidate{
        string name;
        uint voteCount;
    }
    
    struct Voter{
        bool authorised;
        bool voted;
        uint vote;
    }
    address public owner;
    string public electionName;
    mapping(address=>Voter) public voters;
    Candidate[] public candidates;
    uint public totalVotes;
    
modifier ownerOnly(){
    require(msg.sender==owner);
    _;
}    
    constructor(string _name) public{
        owner=msg.sender;
        electionName=_name;
    }

function addCandidate(string _name) ownerOnly public{
    candidates.push(Candidate(_name,0));
}
function getNumCandidate() public view returns(uint){
    return candidates.length;
}
function authorise(address _person) ownerOnly public{
    voters[_person].authorised=true;
}
function vote(uint _voteIndex) public{
    require(!voters[msg.sender].voted);
    require(voters[msg.sender].authorised);
    
    voters[msg.sender].vote=_voteIndex;
    voters[msg.sender].voted=true;
    
    candidates[_voteIndex].voteCount+=1;
    totalVotes+=1;
    
}
function end() ownerOnly public{
    selfdestruct(owner);
}
}