package comp2402TreeEditor;
import java.util.*;
import java.awt.*;
import java.io.*;

public class TrieNode extends TreeNode{
  
  TrieNode parent;
  
  public TrieNode() {
 }


 public TrieNode(Point aPoint) {
  super(aPoint);
 }
 
 public TrieNode(DataADT data) {
  super(data);
 }
 
  public void insertNode(TreeNode aNode){ 
    //insert Nodes as specified for trie nodes
    //if it has the same letter, it goes the next letter becomes a child of that letter
    //till it gets to the end of the string
    //else makes a new node and makes it the new parent node
   boolean found = false;
   TrieNode nodeWithSameLetter = new TrieNode();
   int i = 0;
   
   if(!(aNode instanceof TrieNode)) return;
   
       if(children().size() > 0){
          while((i < children().size()) && (found == false)){
            if(aNode.key().charAt(0)==children().get(i).key().charAt(0)){
            nodeWithSameLetter = (TrieNode) children().get(i);
            found = true;
          }
        i++;
      }
      if(found == true){
       parent = nodeWithSameLetter;
      }
      
      else{
        addChildNode(aNode);
        parent = (TrieNode) aNode;
      }
         
    }
    
    else{
      addChildNode(aNode);
      parent = (TrieNode) aNode;
  }
  }
  
 public void remove(String aKeyString){
    //remove any nodes whose Data object's key matches aKeyString
    //when the string is removed, all letters are removed with it 
   //unless it is a parent of other nodes
      parent = this;
      TrieNode child;
      if(key() != null && key().equals(aKeyString) && parent != null) {
       parent.removeChildNode(this);
      }
      
      for(int i=0; i<getChildren().size(); i++){
        if(getChildren().get(i).key().equals(aKeyString)){
          removeChildNode(getChildren().get(i));
          while(parent.getChildren().size() == 0){
             child = parent;
             parent = (TrieNode) parent.getParent();
             parent.removeChildNode(child);
          }
        }
        else{
          getChildren().get(i).remove(aKeyString);
          }
        }
      }
      
   
   public TrieNode isParent(){
     return parent;
   }
   
   
}
 
