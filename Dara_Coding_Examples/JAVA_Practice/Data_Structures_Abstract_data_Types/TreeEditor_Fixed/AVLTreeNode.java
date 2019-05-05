package comp2402TreeEditor;

import java.util.*;
import java.awt.*;
import java.io.*;
public class AVLTreeNode extends BSTreeNode{
 
 // CONSTRUCTORS ========================================================
  public AVLTreeNode() {
  }


 public AVLTreeNode(Point aPoint) {
  super(aPoint);
 }
 
 public AVLTreeNode(DataADT data) {
  super(data);
 }
 
 public void balance(AVLTreeNode head){
   //the balance tree method
   //balances according to AVL Node requirement
   int leftHeight;
   int rightHeight;
   if(head == null){return;}
   
   balance((AVLTreeNode) head.leftChild);
   balance((AVLTreeNode) head.rightChild);
   
   if(head.rightChild == null){ // makes sure an empty node childs height is set to -1 for convenience
     rightHeight = -1;
   }
   else
     rightHeight = head.rightChild.height();
   
   if(head.leftChild == null){
     leftHeight = -1;
   }
   else
     leftHeight = head.leftChild.height();
 
    
   if(leftHeight - rightHeight > 1){ 
     //if it is left heavy, calls rightright rotate
     //to balance tree
     rightRightRotate(head);
     System.out.println("done");
   }
   
   else if(leftHeight - rightHeight < -1){
     //if it is right heavy, calls leftLeft rotate
     //to balance
     leftLeftRotate(head);
   }
 }
 
 private void leftLeftRotate(AVLTreeNode parent){
   //for a left rotate
   //makes the right child the new parent node
   AVLTreeNode tempNode = null;
   //if(parent.isRoot() == true){
     
     if(parent.rightChild.rightChild() != null){
   if(parent.rightChild.leftChild() != null){
      tempNode = (AVLTreeNode) parent.rightChild.leftChild();
      parent.rightChild.removeChildNode(parent.rightChild.leftChild());
   }
   Data storeParent = (Data) parent.getData();
   TreeNode newLetterNode = new AVLTreeNode(storeParent);
   Data storeRightChild = (Data) parent.rightChild.rightChild().getData();
   TreeNode newLetterNodetwo = new AVLTreeNode(storeRightChild);
   parent.setData(parent.rightChild.getData());
   parent.replaceChildNode(parent.rightChild, parent.rightChild.rightChild);
   parent.setLeftChild((AVLTreeNode) newLetterNode);
   parent.leftChild.setParent(parent);
   parent.setRightChild((AVLTreeNode) newLetterNodetwo);
   parent.rightChild.setParent(parent);
   if(tempNode != null){
   parent.leftChild.setRightChild(tempNode);
   parent.leftChild.rightChild.setParent(parent.leftChild);
   }
     }
     
     else{
       Data storeParent = (Data) parent.getData();
      TreeNode newLetterNode = new AVLTreeNode(storeParent);
       Data storeLeftChild = (Data) parent.leftChild.getData();
      TreeNode newLetterNodetwo = new AVLTreeNode(storeLeftChild);
       parent.setData(parent.leftChild.rightChild.getData());
       parent.leftChild.rightChild.setParent(null);
       parent.setRightChild((AVLTreeNode) newLetterNode);
       parent.rightChild.setParent(parent);
       parent.setLeftChild((AVLTreeNode) newLetterNodetwo);
        parent.leftChild.setParent(parent);
     }
 }
 
 
 private void leftRightRotate(AVLTreeNode parent){
   rightRightRotate((AVLTreeNode)parent.leftChild);
   leftLeftRotate(parent);
 }
 
 private void rightRightRotate(AVLTreeNode parent){
   //makes leftchild new parent node to balance tree
   AVLTreeNode tempNode = null;
     if(parent.leftChild.leftChild() != null){
   if(parent.leftChild.rightChild() != null){
      tempNode = (AVLTreeNode) parent.leftChild.rightChild();
      parent.leftChild.removeChildNode(parent.leftChild.rightChild());
   }
   Data storeParent = (Data) parent.getData();
   TreeNode newLetterNode = new AVLTreeNode(storeParent);
   Data storeLeftChild = (Data) parent.leftChild.leftChild().getData();
   TreeNode newLetterNodetwo = new AVLTreeNode(storeLeftChild);
   parent.setData(parent.leftChild.getData());
   parent.replaceChildNode(parent.leftChild, parent.leftChild.leftChild);
   parent.setRightChild((AVLTreeNode) newLetterNode);
   parent.rightChild.setParent(parent);
   parent.setLeftChild((AVLTreeNode) newLetterNodetwo);
   parent.leftChild.setParent(parent);
   if(tempNode != null){
   parent.rightChild.setLeftChild(tempNode);
   parent.rightChild.leftChild.setParent(parent.rightChild);
   }
     }
     
     else{
       Data storeParent = (Data) parent.getData();
      TreeNode newLetterNode = new AVLTreeNode(storeParent);
       Data storeLeftChild = (Data) parent.leftChild.getData();
      TreeNode newLetterNodetwo = new AVLTreeNode(storeLeftChild);
       parent.setData(parent.leftChild.rightChild.getData());
       parent.leftChild.rightChild.setParent(null);
       parent.setRightChild((AVLTreeNode) newLetterNode);
       parent.rightChild.setParent(parent);
       parent.setLeftChild((AVLTreeNode) newLetterNodetwo);
        parent.leftChild.setParent(parent);
     }
 }
 
 private void replaceChildNode(TreeNode currentChild, TreeNode newChild){ // to replace child Node
  //replace the currentChild of this node with the newChild node
   //if(getChildren == 0){
  if(leftChild == currentChild){
   removeChildNode(currentChild);
   leftChild = (BSTreeNode) newChild;
   newChild.setParent(this);
  }
  else if(rightChild == currentChild){
   removeChildNode(currentChild);
   rightChild = (BSTreeNode) newChild;
   newChild.setParent(this);
  }
   //}
  
   //else{
     //replaceChildNode(this, leftChild());
    
 //}
}
}
 
