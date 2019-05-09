package comp2402TreeEditor;
import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;

public class Trie extends Tree{
  
  public Trie(){
  }
  
  //public void addLeafNode(String aNode) {leafNodes.add(aNode);}
    //public ArrayList<String> getLeafNodes() {return leafNodes;}
 
  public void insert(String aKeyValueString){ //to insert nodes to trie tree
     
     Data data = new Data(aKeyValueString);
     TreeNode newChildNode = new TrieNode(data);
     
     
     if(isEmpty()){//set root of tree to null
       String root = " ";
       Data treeRoot = new Data(root);
       TreeNode newNode = new TrieNode(treeRoot);
       setRoot(newNode);
       newNode.setLocation(getOwner().getDefaultRootLocation());
     }
       
     TrieNode parent = (TrieNode) getRoot();
     
     for(int i=0; i<=newChildNode.key().length(); i++){
       //calls insert method for every letter of the string to insert into node
       if(i == newChildNode.key().length()){
         parent.insertNode(newChildNode);
       }
       
       else{
        String letter = Character.toString(newChildNode.key().charAt(i)); //makes letter into a node to be added to tree
        Data addToTree = new Data(letter);
        TreeNode newLetterNode = new TrieNode(addToTree);
        newLetterNode.setLocation(parent.getLocation());
         parent.insertNode(newLetterNode);//insertNode in trieNode class
       }
       parent = parent.isParent(); //calls the isParent() method to find out what the new parent node is
     }
  }
  
 public void remove(String aKeyString){
    //remove the nodes of the letters of the string and the node corresponding to the actual string
    
    //O(n) 
    
      if(isEmpty()) return;
      else ((TrieNode)getRoot()).remove(aKeyString);
    }
  
  public void draw(Graphics2D aPen){
    // draws a normal node if it is a letter but a leaf node for the word
     
        if(isEmpty()) return;
        
        //Draw tree statistics
        aPen.drawString("type= " + getClass().getName(), 30, 30);
        aPen.drawString("size= " + size(),30,45);
        aPen.drawString("height= " + height(),30,60);
        
        //Draw the actual tree
        ArrayList<TreeNode> theNodes = getNodes();
        for(int i=0; i<theNodes.size(); i++){
          theNodes.get(i).draw(aPen);
          if(theNodes.get(i).isLeaf()){
            theNodes.get(i).drawLeaf(aPen);
          }

     }
  }
        
        public void createChildForNode(TreeNode aNode, Point aLocation){
     /*Graphical creating of nodes is not allowed for a Trie tree
      *since the Trie
      *tree wants to control where Nodes are placed.
     */
         
        JOptionPane.showMessageDialog(getOwner(), 
        "Please use ADT Insert to add nodes to Trie Tree", 
        "Not allowed for Trie Tree", 
        JOptionPane.ERROR_MESSAGE);
  
     
    }

    public boolean allowsGraphicalDeletion(){ 
    //Trie trees do not allow the deletion of arbitrary nodes since the tree
    //must get a chance to rebalance itself. The TreeADT remove method should be use
    //to delete nodes
      return false;
    }

    }


