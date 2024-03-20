import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class listNode {
    int data;
    listNode next;
    public listNode() {
        data = -9999;
        next = null;
    }
    public listNode(int inputData) {
        data = inputData;
        next = null;
    }
    public listNode(int inputData, listNode nextNode) {
        data = inputData;
        next = nextNode;
    }
}
class LLQueue {
    listNode head;
    listNode tail;
    public LLQueue() {
        head = new listNode(-9999);
        tail = head;
    }
    public void insertQ(listNode newNode) {
        this.tail.next = newNode;
        this.tail = newNode;
    }
    public listNode deleteQ() {
        listNode t = this.head;
        if(t.next != null) {
            if(this.tail == t.next) {
                this.tail = this.head;
            }
            listNode temp = t.next;
            t.next = temp.next;
            temp.next = null;
            return temp;
        }
        return null;
    }
    public Boolean isEmpty() {
        return (tail==head);
    }
    public void printQueue(BufferedWriter outFileWriter) throws IOException {
        listNode currentNode = this.head;
        outFileWriter.write("Head -> ");
        while(currentNode != null) {
            outFileWriter.write("(" + currentNode.data + ",");
            if(currentNode.next != null) {
                outFileWriter.write(currentNode.next.data + ") -> ");
                currentNode = currentNode.next;
            } else {
                outFileWriter.write("NULL) -> ");
                break;
            }
        }
        outFileWriter.write("NULL");
    }
    public void reduceOffset(int offSet) {
        listNode currentNode = this.head.next;
        while(currentNode != null) {
            currentNode.data -= offSet;
            currentNode = currentNode.next;
        }
    }
}
class RadixSort {
    int tableSize = 10;
    LLQueue hashTable[][] = new LLQueue[2][tableSize];
    int data;
    int currentTable;
    int previousTable;
    int maxLength;
    int offSet;
    int digitPosition;
    int currentDigit;
    public RadixSort() {
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 10; j++) {
                hashTable[i][j] = new LLQueue();
            }
        }
    }
    public void preProcessing(Scanner inputScanner, BufferedWriter deBugFileWriter) throws IOException {
        deBugFileWriter.write("** Entering preProcessing()\n");
        int negativeNum = 0;
        int positiveNum = 0;
        while(inputScanner.hasNextInt()) {
            int data = Integer.parseInt(inputScanner.next());
            if(data<negativeNum) {
                negativeNum = data;
            }
            if(data>positiveNum) {
                positiveNum = data;
            }
        }
        if(negativeNum<0) {
            this.offSet = Math.abs(negativeNum);
        } else {
            this.offSet = 0;
        }
        positiveNum += this.offSet;
        this.maxLength = getLength(positiveNum);
        deBugFileWriter.write("In preProcessing(): negativeNum=" + negativeNum + ", offSet=" + this.offSet + ", positiveNum=" + positiveNum + ", maxDigits=" + this.maxLength + "\n");
        deBugFileWriter.write("Leaving preProcessing()\n");
    }
    public void RSort(Scanner inputScanner, BufferedWriter outFileWriter, BufferedWriter deBugFileWriter) throws IOException {
        deBugFileWriter.write("*** Entering RSort()\n");
        int digitPosition = 0;
        int currentTable = 0;
        //step2
        while(inputScanner.hasNextInt()) {
            int data = Integer.parseInt(inputScanner.next());
            data += this.offSet;
            listNode newNode = new listNode(data);
            int hashIndex = getIndex(data, digitPosition);
            LLQueue Q = this.hashTable[currentTable][hashIndex];
            Q.insertQ(newNode);
        }
        //step3
        deBugFileWriter.write("in RSort: after inserting all data from inFile into hashTable[0]\n");
        this.printTable(currentTable, deBugFileWriter);
        while(digitPosition < this.maxLength) {
            //step4
            digitPosition++;
            int previousTable = currentTable;
            currentTable = (currentTable+1)%2;
            //step5
            outFileWriter.write("In RSort(), digitPosition=" + digitPosition + ", currentTable=" + currentTable + ", previousTable=" + previousTable + "\n");
            //step6
            outFileWriter.write("in RSort(), printing previous hashTable\n");
            this.printTable(previousTable, outFileWriter);
            //step7
            int tableIndex = 0;
            //step8
            while((tableIndex<this.tableSize)) {
                while(!(this.hashTable[previousTable][tableIndex].head.next == null)) {
                    listNode newNode = this.hashTable[previousTable][tableIndex].deleteQ();
                    int hashIndex = getIndex(newNode.data, digitPosition);
                    LLQueue Q = this.hashTable[currentTable][hashIndex];
                    Q.insertQ(newNode);
                }
                //step9
                deBugFileWriter.write("in RSort(), finish moving one queue from previousTable to currentTable; tableIndex=" + tableIndex + "\n");
                //step10
                tableIndex++;
            } //step11
        } //step12
        //step13
        outFileWriter.write("Printing the sorted data\n");
        this.hashTable[currentTable][0].reduceOffset(this.offSet); // reduce by offset
        printSortedData(currentTable, outFileWriter);
    }
    public static int getLength(int positiveNum) {
        return Integer.toString(positiveNum).length();
    }
    public static int getIndex(int data, int position) {
        return Math.abs((data/(int)Math.pow(10,position)))%10;
    }
    public void printTable(int whichTable, BufferedWriter outFileWriter) throws IOException {
        for(int i = 0; i < this.tableSize; i++) {
            LLQueue Q = this.hashTable[whichTable][i];
            if(Q.isEmpty()) continue;
            outFileWriter.write("hashTable["+whichTable+"]"+"["+i+"]"+": ");
            Q.printQueue(outFileWriter);
            outFileWriter.newLine();
        }
    }
    public void printSortedData(int whichTable, BufferedWriter outFileWriter) throws IOException {
        LLQueue Q = this.hashTable[whichTable][0];
        Q.printQueue(outFileWriter);
    }
}
public class Project2_Main {
    public static void main(String args[]) throws IOException {
        //step 0
        File inFile = new File(args[0]);
        File outFile1 = new File(args[1]);
        File deBugFile = new File(args[2]);
        Scanner inputFileScanner = new Scanner(inFile);
        BufferedWriter outFileWriter = new BufferedWriter(new FileWriter(outFile1));
        BufferedWriter deBugFileWriter = new BufferedWriter(new FileWriter(deBugFile));
        RadixSort R = new RadixSort();
        //step 1
        R.preProcessing(inputFileScanner,deBugFileWriter);
        //step 2
        inputFileScanner.close();
        inputFileScanner = new Scanner(inFile);
        //step 3
        R.RSort(inputFileScanner,outFileWriter,deBugFileWriter);
        //step 4
        inputFileScanner.close();
        outFileWriter.close();
        deBugFileWriter.close();
    }
}