package comp2402TreeEditor;

import java.util.*;
import java.awt.*;
import java.io.*;
import javax.swing.JOptionPane;

public class AVLTree extends BSTree{
 //       =====
 //DUMMY CLASS TO BE REPLACED
    
    public void insert(String aKeyValueString){ //insert method to insert a particular string
       DataADT data = new Data(aKeyValueString); //make the string a data
     
       TreeNode newChildNode = new AVLTreeNode(data); //make the data into a node
     
       if(isEmpty()) { //if it is empty, sets the node to the root node
         setRoot(newChildNode);
         newChildNode.setLocation(getOwner().getDefaultRootLocation());
     }
       else{ //else calls insertNode method in AVLNode class
         getRoot().insertNode(newChildNode);//inserts node
         ((AVLTreeNode) getRoot()).balance((AVLTreeNode) getRoot());//then balances
    }
    }
    
    public void remove(String aKeyString){
    //remove a node whose Data object's key matches aKeyString
    
    //O(height)
    
      
      if(isEmpty()) return;
      
      if(size()==1){
       Data temp = new Data(aKeyString);
       if(root().getData().compare(temp) == 0)
         setRoot(null);
       return;
      }
      
      getRoot().remove(aKeyString);
      
    }
    
    public void createNewRoot(Point aLocation){
     //create a new root for the tree     
     setRoot(new AVLTreeNode(aLocation));
    }    
    
    public void createChildForNode(TreeNode aNode, Point aLocation){
     /*Graphical creating of nodes is not allowed for a AVL tree
      *since the AVL
      *tree wants to control where Nodes are placed.
     */
         
        JOptionPane.showMessageDialog(getOwner(), 
        "Please use ADT Insert to add nodes to AVL Tree", 
        "Not allowed for AVL Tree", 
        JOptionPane.ERROR_MESSAGE);
  
     
    }
    
    public boolean allowsGraphicalDeletion(){ 
    //AVL trees do not allow the deletion of arbitrary nodes since the tree
    //must get a chance to rebalance itself. The TreeADT remove method should be use
    //to delete nodes
      return false;
    }

    }

    