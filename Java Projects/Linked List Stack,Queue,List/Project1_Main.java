import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class listNode {
    int data;
    listNode next;
    public listNode(int inputData) {
        data = inputData;
        next = null;
    }
    public listNode(int inputData, listNode nextNode) {
        data = inputData;
        next = nextNode;
    }
    public listNode() {
        data = 0;
        next = null;
    }
    public void printNode() {
        System.out.println("Data: " + this.data + "Next: " + this.next.data);
    }
}
class LLStack {
    listNode top; // points to dummy node
    public LLStack() {
        top = new listNode(-9999);
    }
    public static void push(listNode top, listNode newNode) { // push newNode after dummy
        newNode.next = top.next;
        top.next = newNode;
    }
    public boolean isEmpty() {
        if(top.next == null) {
            return true;
        } else {
            return false;
        }
    }
    public static listNode pop(listNode top) {
        if(top.next != null) {
            listNode temp = top.next;
            top.next = temp.next;
            temp.next = null;
            return temp;
        } else {
            return null;
        }
    }
    public static void buildStack(LLStack S, Scanner reader, BufferedWriter dbgFileBufferedWriter) {
        try {
            dbgFileBufferedWriter.write("Entering buildStack()\n");
            while(reader.hasNextLine()) {
                String inputLine = reader.nextLine();
                char op = inputLine.charAt(0);
                int data = Integer.parseInt(inputLine.substring(2));
                dbgFileBufferedWriter.write("In buildStack(); op = " + op + " data = " + data + "\n");
                if(op == '+') {
                    listNode newNode = new listNode(data);
                    push(S.top,newNode);
                } else if(op == '-') {
                    listNode topNode = pop(S.top);
                    if(topNode != null) {
                        dbgFileBufferedWriter.write("In buildStack(); topNode is deleted.\n");
                        topNode = null;
                    } else {
                        dbgFileBufferedWriter.write("In buildStack(); Stack is empty.\n");
                    }
                } else { //if op != '=' and op != '+'
                    dbgFileBufferedWriter.write("In buildStack(); op is illegal. op = " + op + "\n");
                }
                dbgFileBufferedWriter.write("In buildStack(); printing Stack.\n");
                printStack(S, dbgFileBufferedWriter);
            }
            dbgFileBufferedWriter.write("leaving buildStack()\n");
            reader.close();
        } catch(IOException e) {
            System.out.println("Error occured with PrintWriter.");
            e.printStackTrace();
        }
    }
    public static void printStack(LLStack S, BufferedWriter dbgFileBufferedWriter) {
        try {
            listNode currentNode = S.top;
            dbgFileBufferedWriter.write("Top -> ");
            while(currentNode != null) {
                dbgFileBufferedWriter.write("(" + currentNode.data + ",");
                if(currentNode.next != null) {
                    dbgFileBufferedWriter.write(currentNode.next.data + ")");
                } else {
                    dbgFileBufferedWriter.write("NULL)");
                }
                dbgFileBufferedWriter.write(" -> ");
                currentNode = currentNode.next;
            }
            dbgFileBufferedWriter.write("NULL\n");
        } catch(Exception e) {
            System.out.println("An error occurred with BufferedWriter");
            e.printStackTrace();
        }
    }
}
class LLQueue {
    listNode head;
    listNode tail;
    public LLQueue() {
        head = new listNode(-9999);
        tail = head;
    }
    public static void insertQ(LLQueue Q, listNode inputNode) {
        Q.tail.next = inputNode;
        Q.tail = inputNode;
    }
    public static listNode deleteQ(LLQueue Q) {
        listNode t = Q.head;
        if(t.next != null) {
            if(Q.tail == t.next) {
                Q.tail = Q.head;
            }
            listNode temp = t.next;
            t.next = temp.next;
            temp.next = null;
            return temp;
        }
        return null;
    }
    public boolean isEmpty() {
        if(this.tail == this.head) {
            return true;
        } else {
            return false;
        }
    }
    public static void buildQueue(LLQueue Q, Scanner reader, BufferedWriter dbgFileBufferedWriter) {
        try {
            dbgFileBufferedWriter.write("Entering buildQueue()\n");
            while(reader.hasNextLine()) {
                String inputLine = reader.nextLine();
                char op = inputLine.charAt(0);
                int data = Integer.parseInt(inputLine.substring(2));
                dbgFileBufferedWriter.write("In buildQueue(); op = " + op + " data = " + data + "\n");
                if(op == '+') {
                    listNode newNode = new listNode(data);
                    insertQ(Q,newNode);
                } else if(op == '-') {
                    listNode tailNode = deleteQ(Q);
                    if(tailNode != null) {
                        dbgFileBufferedWriter.write("In buildQueue(); tailNode's data is " + tailNode.data + "\n");
                        tailNode = null;
                    } else {
                        dbgFileBufferedWriter.write("In buildQueue(); the Queue is empty.\n");
                    }
                } else { // if op!= '=' and op != '+'
                    dbgFileBufferedWriter.write("in buildQueue(); op is illegal. op = " + op + "\n");
                }
                dbgFileBufferedWriter.write("in buildQueue(); printing Queue\n");
                printQueue(Q, dbgFileBufferedWriter);
            }
            dbgFileBufferedWriter.write("Leaving buildQueue()\n");
            reader.close();
        } catch(Exception e) {
            System.out.println("An error occurred with BufferedWriter");
            e.printStackTrace();
        }
    }
    public static void printQueue(LLQueue Q, BufferedWriter dbgFileBufferedWriter) {
        try {
            listNode currentNode = Q.head;
            dbgFileBufferedWriter.write("Head -> ");
            while(currentNode != null) {
                dbgFileBufferedWriter.write("(" + currentNode.data + ",");
                if(currentNode.next != null) {
                    dbgFileBufferedWriter.write(currentNode.next.data + ") -> ");
                    currentNode = currentNode.next;
                } else {
                    dbgFileBufferedWriter.write("NULL) -> ");
                    break;
                }
            }
            dbgFileBufferedWriter.write("NULL\n");
        } catch(Exception e) {
            System.out.println("An error occurred with BufferedWriter");
            e.printStackTrace();
        }
    }
}
class LLlist {
    listNode listHead;
    public LLlist() {
        listHead = new listNode(-9999);
    }
    public static listNode findSpot(listNode listHead, listNode newNode) {
        listNode Spot = listHead;
        while(Spot.next != null && Spot.next.data < newNode.data) {
            Spot = Spot.next;
        }
        return Spot;
    }
    public static void insertOneNode(listNode Spot, listNode newNode) {
        newNode.next = Spot.next;
        Spot.next = newNode;
    }
    public static listNode deleteOneNode(listNode listHead, int data) {
        listNode Spot = listHead;
        while(Spot.next != null && Spot.next.data < data) {
            Spot = Spot.next;
        }
        if(Spot.next != null && Spot.next.data == data) {
            listNode tmp = Spot.next;
            Spot.next = tmp.next;
            tmp.next = null;
            return tmp;
        }
        return null;
    }
    public static void buildList(LLlist L, Scanner reader, BufferedWriter dbgFileBufferedWriter) {
        try {
        dbgFileBufferedWriter.write("Entering buildList()\n");
        while(reader.hasNextLine()) {
            String inputLine = reader.nextLine();
            char op = inputLine.charAt(0);
            int data = Integer.parseInt(inputLine.substring(2));
            dbgFileBufferedWriter.write("In buildList(); op = " + op + " data = " + data + "\n");
            if(op == '+') {
                listNode newNode = new listNode(data);
                listNode Spot = findSpot(L.listHead, newNode);
                insertOneNode(Spot, newNode);
            } else if(op == '-') {
                listNode junk = deleteOneNode(L.listHead, data);
                if(junk != null) {
                    dbgFileBufferedWriter.write("In buildList(); data found and node deleted\n");
                    junk = null;
                } else {
                    dbgFileBufferedWriter.write("In buildList(); the data is not in the list.\n");
                }
            } else { // if op!= '=' and op != '+'
                dbgFileBufferedWriter.write("in buildList(); op is illegal. op = " + op + "\n");
            }
            dbgFileBufferedWriter.write("in buildList(); printing List\n");
            printList(L, dbgFileBufferedWriter);
        }
        dbgFileBufferedWriter.write("Leaving buildList()\n");
        reader.close();
        } catch(Exception e) {
            System.out.println("An error occurred with BufferedWriter");
            e.printStackTrace();
        }
    }
    public static void printList(LLlist L, BufferedWriter dbgFileBufferedWriter) {
        try {
            listNode currentNode = L.listHead;
            dbgFileBufferedWriter.write("listHead -> ");
            while(currentNode != null) {
                dbgFileBufferedWriter.write("(" + currentNode.data + ",");
                if(currentNode.next != null) {
                    dbgFileBufferedWriter.write(currentNode.next.data + ")");
                } else {
                    dbgFileBufferedWriter.write("NULL)");
                }
                dbgFileBufferedWriter.write(" -> ");
                currentNode = currentNode.next;
            }
            dbgFileBufferedWriter.write("NULL\n");
        } catch(Exception e) {
            System.out.println("An error occurred with BufferedWriter");
            e.printStackTrace();
        }
    }
}
public class Project1_Main {
    public static void main(String[] args) throws Exception {
        File inFile = new File(args[0]);
        File outFile = new File(args[1]);
        File deBugFile = new File(args[2]);
        FileWriter debugWriter = new FileWriter(deBugFile);
        FileWriter outFileWriter = new FileWriter(outFile);
        BufferedWriter dbgFileBufferedWriter = new BufferedWriter (debugWriter);
        BufferedWriter outFileBufferedWriter = new BufferedWriter(outFileWriter);
        dbgFileBufferedWriter.write("In main(); after open all files\n");
        LLStack S = new LLStack();
        Scanner inFileReader = new Scanner(inFile);
        LLStack.buildStack(S, inFileReader, dbgFileBufferedWriter);
        outFileBufferedWriter.write("In main(); after buildStack and printing stack\n");
        LLStack.printStack(S, outFileBufferedWriter);
        inFileReader.close();
        inFileReader = new Scanner(inFile);
        LLQueue Q = new LLQueue();
        LLQueue.buildQueue(Q, inFileReader, dbgFileBufferedWriter);
        outFileBufferedWriter.write("In main(); after buildQueue() and printing Queue\n");
        LLQueue.printQueue(Q, outFileBufferedWriter);
        inFileReader.close();
        inFileReader = new Scanner(inFile);
        LLlist LL = new LLlist();
        LLlist.buildList(LL, inFileReader, dbgFileBufferedWriter);
        outFileBufferedWriter.write("In main(); after buildList and printing list\n");
        LLlist.printList(LL, outFileBufferedWriter);
        dbgFileBufferedWriter.close();
        outFileBufferedWriter.close();
    }
}