#include <iostream>
#include <fstream>
using namespace std;

class HtreeNode {
    public:
    string chStr;
    int probability;
    string code;
    HtreeNode *left;
    HtreeNode *right;
    HtreeNode *next;
    HtreeNode(string inputStr, int inputProbability, string inputCode, HtreeNode *inputLeft, HtreeNode *inputRight, HtreeNode *inputNext) {
        this->chStr = inputStr;
        this->probability = inputProbability;
        this->code = inputCode;
        this->left = inputLeft;
        this->right = inputRight;
        this->next = inputNext;
    }
    static void printNode(HtreeNode *T, ofstream &outFile) {
        outFile << "(" << T->chStr << ", " << T->probability << ", ";
        if(T->next != NULL) {
            outFile << T->next->chStr << ", ";
        } else {
            outFile << "NULL, ";
        }
        if(T->left != NULL) {
            outFile << T->left->chStr << ", ";
        } else {
            outFile << "NULL, ";
        }
        if(T->right != NULL) {
            outFile << T->right->chStr << ")";
        } else {
            outFile << "NULL)";
        }
    }
    void printNode(ofstream &outFile) {
        outFile << "(" << this->chStr << ", " << this->probability << ", ";
        if(this->next != NULL) {
            outFile << this->next->chStr << ", ";
        } else {
            outFile << "NULL, ";
        }
        if(this->left != NULL) {
            outFile << this->left->chStr << ", ";
        } else {
            outFile << "NULL, ";
        }
        if(this->right != NULL) {
            outFile << this->right->chStr << ")";
        } else {
            outFile << "NULL)";
        }
    }
};
class HuffmanBinaryTree {
    public:
    HtreeNode *listHead;
    HtreeNode *Root;
    HuffmanBinaryTree() {
        HtreeNode *newNode = new HtreeNode("dummy",0,"",NULL,NULL,NULL);
        listHead = newNode;
        Root = newNode;
    }
    HtreeNode *findSpot(HtreeNode *spot, HtreeNode *node) {
        HtreeNode *currentNode = listHead;
        while(currentNode->next != NULL && currentNode->next->probability < node->probability) {
            currentNode = currentNode->next;
        }
        return currentNode;
    }
    void listInsert(HtreeNode *spot, HtreeNode *newNode) {
        newNode->next = spot->next;
        spot->next = newNode;
    }
    HtreeNode *constructHuffmanLList(ifstream &inFile, ofstream &dbgFile) {
        //step 0
        dbgFile << "Entering constructHuffmanLList() method" << endl;
        //step 1
        this->listHead = new HtreeNode("dummy",0,"",NULL,NULL,NULL);
        //step 2
        string chr;
        int prob;
        while(inFile >> chr >> prob) {
            dbgFile << "In constructHuffmanLList (), chr = " << chr << " prob = " << prob << endl;
            //step 3
            HtreeNode *newNode = new HtreeNode(chr, prob, "", NULL, NULL, NULL);
            dbgFile << "In constructHuffmanLList (), printing newNode" << endl;
            newNode->printNode(dbgFile);
            dbgFile << endl;
            //step 4
            HtreeNode *spot = findSpot(listHead, newNode);
            //step 5
            listInsert(spot, newNode);
            //step 6
            dbgFile << "In constructHuffmanLList () printing list" << endl;
            printList(listHead, dbgFile);
        } // step 7
        //step 8
        return listHead;
    }
    HtreeNode *constructHuffmanBinTree(HtreeNode *listHead, ofstream &dbgFile) {
        //step 0
        dbgFile << "Entering constructHuffmanBinTree ()" << endl;

        if (listHead->next == NULL || listHead->next->next == NULL) {
            return listHead->next;
        }
        while(listHead->next != NULL && listHead->next->next != NULL) {
            //step 1
            HtreeNode *newNode = new HtreeNode("",0,"",NULL,NULL,NULL);
            newNode->probability = listHead->next->probability + listHead->next->next->probability;
            newNode->chStr = listHead->next->chStr + listHead->next->next->chStr;
            newNode->left = listHead->next;
            newNode->right = listHead->next->next;
            //step 2
            dbgFile << "In constructHuffmanBinTree, printing newNode" << endl;
            newNode->printNode(newNode, dbgFile);
            dbgFile << endl;
            HtreeNode *spot = findSpot(listHead, newNode);
            //step 3
            listInsert(spot, newNode);
            //step 4
            listHead->next = listHead->next->next->next;
            //step 5
            dbgFile << "In constructHuffmanBinTree, printing list" << endl;
            printList(listHead, dbgFile);
        } // step 6
        //step 7
        return listHead->next;
    }
    void preOrderTraversal(HtreeNode *T, ofstream &outFile) {
        if(isLeaf(T)) {
            T->printNode(T, outFile);
            outFile << endl;
        } else {
            T->printNode(T, outFile);
            outFile << endl;
            preOrderTraversal(T->left, outFile);
            preOrderTraversal(T->right, outFile);
        }
    }
    void inOrderTraversal(HtreeNode *T, ofstream &outFile) {
        if(isLeaf(T)) {
            T->printNode(T, outFile);
            outFile << endl;
        } else {
            inOrderTraversal(T->left, outFile);
            T->printNode(T, outFile);
            outFile << endl;
            inOrderTraversal(T->right, outFile);
        }
    }
    void postOrderTraversal(HtreeNode *T, ofstream &outFile) {
        if(isLeaf(T)) {
            T->printNode(T, outFile);
            outFile << endl;
        } else {
            postOrderTraversal(T->left, outFile);
            postOrderTraversal(T->right, outFile);
            T->printNode(T, outFile);
            outFile << endl;
        }
    }
    void constructCharCode(HtreeNode *T, string code, ofstream &outFile) {
        if(isLeaf(T)) {
            T->code = code;
            outFile << T->chStr << " " << T->code << endl;
        } else {
            if (T->left) {
            string leftCode = code + "0";
            constructCharCode(T->left, leftCode, outFile);
        }
        if (T->right) {
            string rightCode = code + "1";
            constructCharCode(T->right, rightCode, outFile);
            }
        }
    }
    bool isLeaf(HtreeNode *node) {
        return !(node->left) && !(node->right);
    }
    void printList(HtreeNode *listHead, ofstream &outFile) {
        HtreeNode *currentNode = listHead;
        outFile << "listHead";
        while(currentNode != NULL) {
            outFile << " -> ";
            currentNode->printNode(outFile);
            currentNode = currentNode->next;
        }
        outFile << " -> NULL" << endl;
    }
};

int main(int argc, char *argv[]) {
    //step 0
    ifstream inputFile;
    ofstream outFile1, deBugFile;
    inputFile.open(argv[1]);
    outFile1.open(argv[2]);
    deBugFile.open(argv[3]);
    //step 1
    HuffmanBinaryTree huffmantree; 
    HtreeNode *listHead = huffmantree.constructHuffmanLList(inputFile, deBugFile);
    //step 2
    outFile1 << "In main() printing list" << endl;
    huffmantree.printList(listHead, outFile1);
    //step 3
    HtreeNode *root = huffmantree.constructHuffmanBinTree(listHead, deBugFile);
    //step 4
    huffmantree.constructCharCode(root, "", outFile1);
    //step 5
    outFile1 << "Pre order traversal:" << endl;
    huffmantree.preOrderTraversal(root, outFile1);
    //step 6
    outFile1 << "In order traversal:" << endl;
    huffmantree.inOrderTraversal(root, outFile1);
    //step 7
    outFile1 << "Post order traversal:" << endl;
    huffmantree.postOrderTraversal(root, outFile1);
    //step 8
    inputFile.close();
    outFile1.close();
    deBugFile.close();
    return 0;
}