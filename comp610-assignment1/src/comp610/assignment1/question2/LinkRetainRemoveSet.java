/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp610.assignment1.question2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author ssr7324
 * @param <E>
 */
public class LinkRetainRemoveSet<E extends Comparable<E>> extends LinkedSet<E> {

    public LinkRetainRemoveSet() {
        super();
    }

    @Override
    public boolean add(E o) {
        if (Objects.nonNull(o)) {
            Node<E> newNode = new Node<>(o);

            if (!contains(o)) {
                if (firstNode == null) {
                    firstNode = newNode;
                    numElements++;
                    return true;
                } else {
                    if (firstNode.element.compareTo(newNode.element) > 0) {
                        addNodeToStart(firstNode, newNode);
                    } else {
                        Node<E> nodeAtCurrentPosition = firstNode;
                        while (Objects.nonNull(nodeAtCurrentPosition.next)) {
                            if (nodeAtCurrentPosition.next.element.compareTo(newNode.element) < 0f) {
                                nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                            } else {
                                break;
                            }
                        }
                        if ((nodeAtCurrentPosition.next != null)) {
                            newNode.next = nodeAtCurrentPosition.next;
                            nodeAtCurrentPosition.next = newNode;
                            numElements += 1;
                            return true;
                        } else {
                            nodeAtCurrentPosition.next = newNode;
                            newNode.next = null;
                            numElements += 1;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void addNodeToStart(Node<E> nodeAtCurrentPosition, Node<E> addNode) {
        addNode.next = nodeAtCurrentPosition;
        firstNode = addNode;
        numElements++;
    }

    public LinkRetainRemoveSet<E> remove(E start, E end) throws Exception {
        LinkRetainRemoveSet<E> removeLinkSet = new LinkRetainRemoveSet<>();
        Node<E> nodeAtCurrentPosition = firstNode;
        Node<E> nodeAtEndPosition = firstNode;
        boolean found = false;

        if ((Objects.isNull(start)) && (Objects.nonNull(end))) {
            start = firstNode.element;
        } else if ((Objects.nonNull(start)) && (Objects.isNull(end))) {
            end = getNodeAtLast();
            found = true;
        } else if ((Objects.isNull(start)) && (Objects.isNull(end))) {
            start = firstNode.element;
            end = getNodeAtLast();
            found = true;
        }
        if ((!contains(start)) || (!contains(end))) {
            throw new Exception("Element of node is not founded");
        } else {
            while (Objects.nonNull(nodeAtCurrentPosition.next)) {
                if ((nodeAtCurrentPosition.element == firstNode.element)
                        && (nodeAtCurrentPosition.element.compareTo(start) != 0)) {
                    removeLinkSet.add(nodeAtCurrentPosition.element);
                    firstNode = nodeAtCurrentPosition.next;
                    nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                } else {
                    if (nodeAtCurrentPosition.next.element.compareTo(end) == 0) {
                        if (found) {
                            nodeAtEndPosition = nodeAtCurrentPosition.next;
                            nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                        } else {
                            nodeAtEndPosition = nodeAtCurrentPosition;
                            removeLinkSet.add(nodeAtCurrentPosition.next.element);
                            nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                        }
                    } else if (nodeAtCurrentPosition.next.element.compareTo(end) > 0) {
                        removeLinkSet.add(nodeAtCurrentPosition.next.element);
                        nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                    } else {
                        nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                    }
                }
            }
            nodeAtEndPosition.next = null;
        }
        return removeLinkSet;
    }

    public LinkRetainRemoveSet<E> retain(E start, E end) throws Exception {

        LinkRetainRemoveSet<E> retainLinkSet = new LinkRetainRemoveSet<>();
        Node<E> nodeAtCurrentPosition = firstNode;
        boolean firstNodeInRange = false;
        boolean found = false;

        if (Objects.isNull(start) && Objects.nonNull(end)) {
            start = firstNode.element;
        } else if (Objects.nonNull(start) && Objects.isNull(end)) {
            end = getNodeAtLast();
            found = true;
        } else if (Objects.isNull(start) && Objects.isNull(end)) {
            start = firstNode.element;
            end = getNodeAtLast();
            found = true;
        }
        if (!contains(start) || !contains(end)) {
            throw new Exception("Element of node is not founded");
        } else {
            while (Objects.nonNull(nodeAtCurrentPosition.next)) {
                if ((nodeAtCurrentPosition.element == firstNode.element)
                        && (nodeAtCurrentPosition.element.compareTo(start)) == 0) {
                    retainLinkSet.add(firstNode.element);
                    firstNode = nodeAtCurrentPosition.next;
                    firstNodeInRange = true;
                } else if (nodeAtCurrentPosition.next.element.compareTo(start) >= 0) {
                    if ((nodeAtCurrentPosition.next.element.compareTo(end) == 0)
                            && (found == false)) {
                        break;
                    } else if (firstNodeInRange) {
                        retainLinkSet.add(nodeAtCurrentPosition.next.element);
                        nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                        firstNode = nodeAtCurrentPosition.next;
                    } else {
                        retainLinkSet.add(nodeAtCurrentPosition.next.element);
                        nodeAtCurrentPosition.next = nodeAtCurrentPosition.next.next;
                    }
                } else {
                    nodeAtCurrentPosition = nodeAtCurrentPosition.next;
                }
            }
        }
        return retainLinkSet;
    }

    private E getNodeAtLast() {
        Node<E> nodeAtCurrentPosition = firstNode;
        while (Objects.nonNull(nodeAtCurrentPosition.next)) {
            nodeAtCurrentPosition = nodeAtCurrentPosition.next;
        }
        return nodeAtCurrentPosition.element;
    }

    private void formOfPrintSet(Iterator iterator) {
        System.out.print("{");
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
            if (iterator.hasNext()) {
                System.out.print("; ");
            }
        }
        System.out.print("}");
    }

    private void putNumbersToLinkSet(LinkRetainRemoveSet<Integer> numbers) {
        int[] numberArray = {3, 4, 2, 5, 7, 1, 6};
        for (int i = 0; i < numberArray.length; i++) {
            numbers.add(numberArray[i]);
        }
    }

    private void putWordsToLinkSet(LinkRetainRemoveSet<String> words) {
        String[] wordlist = {"B", "E", "K", "P"};
        words.addAll(Arrays.asList(wordlist));
    }

    private void printRetainedNum(LinkRetainRemoveSet<Integer> numberLists,
            Integer setStart, Integer setEnd) throws Exception {

        LinkRetainRemoveSet numberList = numberLists;
        numberList.putNumbersToLinkSet(numberList);

        System.out.println();
        System.out.print("set = ");
        numberList.formOfPrintSet(numberList.iterator());
        System.out.println();
        System.out.println("retain(" + setStart + "," + setEnd + ")");
        System.out.print("set = ");
        numberList.formOfPrintSet(numberList.retain(setStart, setEnd).iterator());
        System.out.print(" returned set = ");
        numberList.formOfPrintSet(numberList.iterator());
        System.out.println();

    }

    private void printRemovedNum(LinkRetainRemoveSet<Integer> numberLists,
            Integer setStart, Integer setEnd) throws Exception {

        LinkRetainRemoveSet numberList = numberLists;
        numberList.putNumbersToLinkSet(numberList);

        System.out.println();
        System.out.print("set = ");
        numberList.formOfPrintSet(numberList.iterator());
        System.out.println();
        System.out.println("remove(" + setStart + "," + setEnd + ")");
        System.out.print("set = ");
        numberList.formOfPrintSet(numberList.remove(setStart, setEnd).iterator());
        System.out.print(" returned set = ");
        numberList.formOfPrintSet(numberList.iterator());
        System.out.println();

    }

    private void printRemovedWord(LinkRetainRemoveSet<String> wordLists,
            Integer setStart, Integer setEnd) throws Exception {

        LinkRetainRemoveSet wordList = wordLists;
        wordList.putWordsToLinkSet(wordList);
        
        System.out.println();
        System.out.print("set = ");
        wordList.formOfPrintSet(wordList.iterator());
        System.out.println();
        System.out.println("remove(" + setStart + "," + setEnd + ")");
        System.out.print("set = ");
        wordList.formOfPrintSet(wordList.remove(setStart, setEnd).iterator());
        System.out.print(" returned set = ");
        wordList.formOfPrintSet(wordList.iterator());
        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        LinkRetainRemoveSet wordList = new LinkRetainRemoveSet();
        LinkRetainRemoveSet numberList = new LinkRetainRemoveSet();

        //remove null, null
        wordList.printRemovedWord(wordList, null, null);
        //retain 2,6
        numberList.printRetainedNum(numberList, 2, 6);
        //remove 2,6
        numberList.printRemovedNum(numberList, 2, 6);
        //remove 4,5
        numberList.printRemovedNum(numberList, 4, 5);
        //retain 6,7
        numberList.printRetainedNum(numberList, 6, 7);
        //retain null,4
        numberList.printRetainedNum(numberList, null, 4);
        //retain 4,null
        numberList.printRetainedNum(numberList, 4, null);
        //remove 4,null
        numberList.printRemovedNum(numberList, 4, null);
    }
}
