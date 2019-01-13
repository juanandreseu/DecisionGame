package TreePackage;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import StackPackage.*;
import QueuePackage.*;

public class BinaryTree<T> implements BinaryTreeInterface<T>, Serializable
{
    protected BinaryNode<T> root;

    public BinaryTree()
    {
        root = null;
    }

    public BinaryTree(T rootData)
    {
        root = new BinaryNode<>(rootData);
    }

    public BinaryTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree)
    {
        initializeTree(rootData, leftTree, rightTree);
    }

    @Override
    public T getRootData()
    {
        if(!isEmpty())
        {
            return root.getData();
        }
        return null;
    }

    @Override
    public int getHeight()
    {
        return root.getHeight();
    }

    @Override
    public int getNumberOfNodes()
    {
        return root.getNumberOfNodes();
    }

    @Override
    public Iterator<T> getPreorderIterator()
    {
        return new PreorderIterator();
    }

    @Override
    public Iterator<T> getPostorderIterator()
    {
        return new PostorderIterator();
    }

    @Override
    public void clear()
    {
        root = null;
    }

    @Override
    public Iterator<T> getInorderIterator()
    {
        return new InOrderIterator();
    }

    @Override
    public Iterator<T> getLevelOrderIterator()
    {
        return new LevelOrderIterator();
    }

    @Override
    public void setTree(T rootData)
    {
        setTree(rootData, null, null);
    }

    @Override
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree, BinaryTreeInterface<T> rightTree)
    {
        initializeTree(rootData, (BinaryTree<T>) leftTree, (BinaryTree<T>) rightTree);
    }

    private void initializeTree(T rootData, BinaryTree<T> leftTree, BinaryTree<T> rightTree)
    {
        root = new BinaryNode<>(rootData);

        if((leftTree != null) && !leftTree.isEmpty())
            root.setLeftChild(leftTree.root);
        if((rightTree != null) && !rightTree.isEmpty())
        {
            if (rightTree != leftTree)
            {
                root.setRightChild(rightTree.root);
            }
            else
                root.setRightChild(rightTree.root.copy());
        }
        if((leftTree != null) && (leftTree != this))
        {
            leftTree.clear();
        }
        if((rightTree != null) && (rightTree != this))
        {
            rightTree.clear();
        }
    }

    @Override
    public boolean isEmpty()
    {
        return root == null;
    }

    // traversal that doesn't use an iterator (for demonstration purposes only)
    public void iterativeInorderTraverse()
    {
        StackInterface<BinaryNode<T>> nodeStack = new ArrayStack<>();
        BinaryNode<T> currentNode = root;

        while (!nodeStack.isEmpty() || (currentNode != null))
        {
            while (currentNode != null)
            {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            if (!nodeStack.isEmpty())
            {
                BinaryNode<T> nextNode = nodeStack.pop();

                System.out.println(nextNode.getData());
                currentNode = nextNode.getRightChild();
            }
        }
    }

    private class InOrderIterator implements Iterator<T>
    {
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public InOrderIterator()
        {
            nodeStack = new ArrayStack<>();
            currentNode = root;
        }

        @Override
        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || (currentNode != null);
        }

        @Override
        public T next()
        {
            BinaryNode<T> nextNode = null;

            // find leftmost node with no left child
            while(currentNode != null)
            {
                nodeStack.push(currentNode);
                currentNode = currentNode.getLeftChild();
            }

            if(!nodeStack.isEmpty())
            {
                nextNode = nodeStack.pop();
                currentNode = nextNode.getRightChild();
            }
            else
                throw new NoSuchElementException();
            return nextNode.getData();
        }
    }
    private class PostorderIterator implements Iterator<T>{
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;
        public PostorderIterator()
        {
            nodeStack = new ArrayStack<>();
            currentNode = root;
        }
        @Override
        public boolean hasNext()
        {
            return !nodeStack.isEmpty() || (currentNode != null);
        }
        @Override()
        public T next(){
            BinaryNode<T> nextNode=null;
            if(currentNode!=null){
                if(currentNode.isLeaf())
                    nodeStack.push(currentNode);
                while(!currentNode.isLeaf()){
                    nodeStack.push(currentNode);
                    if(currentNode.hasLeftChild()){
                        currentNode=currentNode.getLeftChild();
                    }
                    else
                        currentNode=currentNode.getRightChild();
                    if(currentNode.isLeaf()){
                        nodeStack.push(currentNode);
                    }
                }
            }
            if(!nodeStack.isEmpty()){
                nextNode=nodeStack.pop();
                if(!nodeStack.isEmpty() && nodeStack.peek().hasLeftChild() && nextNode==nodeStack.peek().getLeftChild()){
                    currentNode=nodeStack.peek().getRightChild();
                }
                else
                    currentNode=null;
            }
            return nextNode.getData();
        }

    }
    private class LevelOrderIterator implements Iterator<T>{
        private QueueInterface<BinaryNode<T>> nodeQueue;
        private BinaryNode<T> currentNode;

        public LevelOrderIterator(){
            nodeQueue = new LinkedQueue<>();
            currentNode = root;
            nodeQueue.enqueue(currentNode);
        }

        @Override
        public boolean hasNext(){
            return !nodeQueue.isEmpty();
        }

        @Override
        public T next(){
            BinaryNode<T> nextNode = null;
            if(hasNext()){
                nextNode=nodeQueue.dequeue();
                if(nextNode.getLeftChild()!=null)
                    nodeQueue.enqueue(nextNode.getLeftChild());
                if(nextNode.getRightChild()!=null)
                    nodeQueue.enqueue(nextNode.getRightChild());
            }
            else
                throw new NoSuchElementException();
            return nextNode.getData();
        }
    }

    private class PreorderIterator implements Iterator<T>{
        private StackInterface<BinaryNode<T>> nodeStack;
        private BinaryNode<T> currentNode;

        public PreorderIterator(){
            nodeStack = new ArrayStack<>();
            currentNode = root;
            nodeStack.push(currentNode);
        }

        @Override
        public boolean hasNext(){
            return !nodeStack.isEmpty() ;
        }
        @Override
        public T next(){
            BinaryNode<T> nextNode=null;
            if(hasNext()){
                nextNode=nodeStack.pop();
                if(nextNode.getRightChild()!=null)
                    nodeStack.push(nextNode.getRightChild());
                if(nextNode.getLeftChild()!=null)
                    nodeStack.push(nextNode.getLeftChild());
            }
            else
                throw new NoSuchElementException();
            return nextNode.getData();
        }
    }
}
