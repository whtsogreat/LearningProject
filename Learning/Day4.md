##### BST（二叉搜索树）

每个节点的值大于其任意左侧子节点的值，小于其任意右节点的值。

###### BST的插入

插入操作类似于查找，是个递归过程，只不过插入操作在查找不到的时候创建一个新节点并将其加入树，需考虑下面4种情形：

1. 插入操作类似于查找，是个递归过程，只不过插入操作在查找不到的时候创建一个新节点并将其加入树，需考虑下面4种情形：
   当前节点的关键值等于待插入节点关键值，则不做任何处理（若需要可更新该节点），返回；
2. 当前节点的关键值小于待插入节点关键值，根据BST的定义，待插入节点应插入当前节点的左子树：
     a) 若当前节点的左子树为空，则待插入节点应为当前节点的左孩子，新建节点并插入，
     b) 若当前节点的左子树非空，则递归插入；
3. 当前节点的关键值大于待插入节点关键值，根据BST的定义，待插入节点应插入当前节点的右子树：
     a) 若当前节点的右子树为空，则待插入节点应为当前节点的右孩子，新建节点并插入，
     b) 若当前节点的右子树非空，则递归插入；
4. 若当前节点为空，则说明当前为空树，待插入节点应为树根。

###### BST的删除

插入操作也类似于查找，是个递归过程，只不过删除操作在找到被删除节点后的处理要复杂些，需考虑下面4种情形：

1. 当前节点的关键值等于待删除关键值，则进入删除处理过程；
2. 当前节点的关键值小于待插入节点关键值，根据BST的定义，应在当前节点的左子树上递归删除操作；
3. 当前节点的关键值大于待插入节点关键值，根据BST的定义，应在当前节点的右子树上递归删除操作；
4. 若当前节点为空，则说明查找不到待删除关键值的节点，返回-1指示删除失败。

删除处理过程又需要考虑以前几种情形：

1. 待删除节点为叶子节点（左右孩子均为空）；
     a) 将待删除节点的父节点指向该待删除节点的指针置为空，
     b) 删除待删除节点。

   ![image](https://images.cnblogs.com/cnblogs_com/dskit/201208/20120818224906386.png)

2. 待删除节点(10)为左孩子为空，右孩子非空；
     a) 将待删除节点(10)的父节点(8)原来指向待删除节点(10)的指针重新指向待删除节点(10)的右孩子(14)，
     b) 将待删除节点(10)的右孩子节点(14)原来指向待删除节点(10)的父指针重新指向待删除节点(10)的父节点(8)，
     b) 删除待删除节点(10)。

   ![image](https://images.cnblogs.com/cnblogs_com/dskit/201208/201208182249166439.png)

3. 待删除节点(14)为左孩子非空，右孩子为空；
     a) 将待删除节点(14)的父节点(10)原来指向待删除节点(14)的指针重新指向待删除节点(10)的左孩子(13)，
     b) 将待删除节点(14)的左孩子节点(13)原来指向待删除节点(14)的父指针重新指向待删除节点(14)的父节点(10)，
     c) 删除待删除节点(14)。

   ![image](https://images.cnblogs.com/cnblogs_com/dskit/201208/201208182249328659.png)

4. 待删除节点(3)为左孩子非空，右孩子非空。
     a) 将待删除节点(3)的关键值与其右子树上值最小节点(4)的值交换，原节点(4)转换为待删除节点，准备被删除，
       注1：待删除节点右子树上最小值节点的左孩子必为空， 否则，根据BST定义，最小值节点应在该节点的左子树上；
       注2：待删除节点右子树上最小值节点的右孩子可为空，也可不为空；
       注3：待删除节点与其右子树上最小值节点交换后，删除原右子树最小值节点后，仍为BST。
     b) 根据注1，删除新待删除节点转换为删除叶子节点或删除只有右孩子节点的情况，本例为删除叶子节点。

   ![image](https://images.cnblogs.com/cnblogs_com/dskit/201208/201208182249405325.png)

   另外，也可以选择待删除节点左子树上的最大值节点进行交换。

###### 性能分析

​            		平均复杂度     最坏情况复杂度

插入操作       O(logN)         	O(N)

查询操作       O(logN)        	 O(N)

删除操作       O(logN)         	O(N)



##### AVL树(平衡二叉树)：

AVL树本质上是一颗二叉查找树，但是它又具有以下特点：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。在AVL树中任何节点的两个子树的高度最大差别为一，所以它也被称为平衡二叉树。

###### AVL的插入

AVL树的插入，单旋转的第一种情况---右旋：

![img](https://images0.cnblogs.com/i/566545/201403/311901354227875.png)

AVL树的插入，单旋转的第一种情况---左旋：

![img](https://images0.cnblogs.com/i/566545/201403/311932227973692.png)

AVL树的插入，双旋转的第一种情况---左右(先左后右)旋：

![img](https://images0.cnblogs.com/i/566545/201403/312241527975291.png)

AVL树的插入，双旋转的第二种情况---右左(先右后左)旋：

![img](https://images0.cnblogs.com/i/566545/201403/312151521573646.png)

###### AVL的删除

第一种情况

删除叶子节点1,节点9,节点4,节点6

![img](https://img-blog.csdnimg.cn/20200418172744475.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20200418173233362.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20200418173550484.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20200418173849705.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

第二种情况：

删除非叶子节点，该节点只有左孩子

![img](https://img-blog.csdnimg.cn/20200418174425322.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

第三种情况

删除非叶子节点，该节点只有右孩子

![img](https://img-blog.csdnimg.cn/2020041817570775.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

第四种情况

删除非叶子节点（节点3，节点7，节点5），非叶子节点既有左孩子，又有右孩子

![img](https://img-blog.csdnimg.cn/20200418180925175.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20200418181649249.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

![img](https://img-blog.csdnimg.cn/20200418182253185.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzIxMzg4NTM1,size_16,color_FFFFFF,t_70)

##### 红黑树

红黑树是一棵二叉搜索树，它在每个结点上增加了一个存储位来表示结点的颜色，可以是RED 或 BLACK。通过对任何一条根到叶子的简单路径上各个结点的颜色进行约束，红黑树确保没有一条路径会比其他路径长出2倍，因而是近似平衡的。

树的每个结点包含 5 个属性：color，key，left，right和p。如果一个结点没有子结点或者父结点，则该结点相应的指针属性的值为NULL。我们可以把这些NULL视为指向二叉搜索树叶结点的指针，而把带关键字的结点视为树的内部结点。

###### 红黑树的性质：

　　一棵红黑树是满足下面红黑性质的二叉搜索树：

　　1.每个结点或是红色的，或是黑色的

　　2.根节点是黑色的

　　3.每个叶结点(NULL)是黑色的

　　4.如果一个结点是红色的，那么他的两个子结点都是黑色的

　　5.对于每个结点，从该结点到其所有后代叶结点的简单路径上， 	       包含相同数目的黑色结点

![img](https://images0.cnblogs.com/i/566545/201404/021905396568458.png)

###### 红黑树的插入

红黑树插入一个新节点z的过程是在二叉查找树插入过程的基础上改进的，先按照二叉排序找到插入位置，将插入节点设为红色，再对插入节点后的结构进行重新着色，使得满足红黑树的性质。
如果每次插入新的节点导致红黑树的性质被破坏，那么不是性质2就是性质4。违反性质2是因为插入节点是根且为红色，违反性质4是因为插入节点和其父节点都是红色。
违反性质4的情况可以给出下面三种可能：

**情况1：z的叔叔节点y是红色的**
此时z的父节点和y都是红色的，解决办法是将z的父节点]和叔叔结点y都着为黑色，而将z的祖父结点着为红色，然后从祖父结点继续向上判断是否破坏红黑树的性质。处理过程如下图所示：

![img](https://img2018.cnblogs.com/blog/1666300/201907/1666300-20190712162606426-415830517.png)

**情况2：z的叔叔y是黑色的，而且z是右孩子**
**情况3：z的叔叔y是黑色的，而且z是左孩子**
情况2和情况3中y都是黑色的，通过z是左孩子还是右孩子进行区分的。可以将情况2通过旋转为情况3。情况2中z是右孩子，旋转后成为情况3，使得z变为左孩子，可以在parent[z]结点出使用一次左旋转来完成。无论是间接还是直接的通过情况2进入到情况3，z的叔叔y总是黑色的。在情况3中，将parent[z]着为黑色，parent[parent[z]]着为红色，然后从parent[parent[z]]处进行一次右旋转。情况2、3修正了对性质4的违反，修正过程不会导致其他的红黑性质被破坏。修正过程如下图所示：

![img](https://img2018.cnblogs.com/blog/1666300/201907/1666300-20190712163148172-1796204777.png)

给一个完整的例子来说明插入过程，如下图所示：

![img](https://img2018.cnblogs.com/blog/1666300/201907/1666300-20190712163500636-2082882002.png)

###### 红黑树的删除

https://www.jianshu.com/p/84416644c080

##### B树

B树也称B-树,它是一颗多路平衡查找树。

- 每个节点最多有m-1个**关键字**（可以存有的键值对）。
- 根节点最少可以只有1个**关键字**。
- 非根节点至少有m/2个**关键字**。
- 每个节点中的关键字都按照从小到大的顺序排列，每个关键字的左子树中的所有关键字都小于它，而右子树中的所有关键字都大于它。
- 所有叶子节点都位于同一层，或者说根节点到每个叶子节点的长度都相同。
- 每个节点都存有索引和数据，也就是对应的key和value。

###### B树插入

插入的时候，我们需要记住一个规则：**判断当前结点key的个数是否小于等于m-1，如果满足，直接插入即可，如果不满足，将节点的中间的key将这个节点分为左右两部分，中间的节点放到父节点中即可。**

例子：在5阶B树中，结点最多有4个key,最少有2个key（注意：下面的节点统一用一个节点表示key和value）。

- 插入18，70，50,40

![img](https://oscimg.oschina.net/oscnet/eb191e52c4af046dc6d858c793c8ddf3c8e.jpg)

- 插入22

![img](https://oscimg.oschina.net/oscnet/0c6e169e37ff9643c46c72922f42bcb5d46.jpg)

插入22时，发现这个节点的关键字已经大于4了，所以需要进行分裂，分裂的规则在上面已经讲了，分裂之后，如下。

![img](https://oscimg.oschina.net/oscnet/1377e97a1160e07ffa600c0d8cba7cadf1e.jpg)

- 接着插入23，25，39

![img](https://oscimg.oschina.net/oscnet/03e1e510bcd8672a98e1a67dd60a91d3aea.jpg)

分裂，得到下面的。

![img](https://oscimg.oschina.net/oscnet/1155825270844bbe53c9f2701395c3fa9af.jpg)

###### B树的删除操作

B树的删除操作相对于插入操作是相对复杂一些的。

- 现在有一个初始状态是下面这样的B树，然后进行删除操作。

![img](https://oscimg.oschina.net/oscnet/261678b789a5f4dac373093919ea6a41b8a.jpg)

- 删除15，这种情况是删除叶子节点的元素，如果删除之后，节点数还是大于`m/2`，这种情况只要直接删除即可。

![img](https://oscimg.oschina.net/oscnet/0ed340c43b270b4a7932d7db15ebf0acc26.jpg)

![img](https://oscimg.oschina.net/oscnet/11e2970a52f2b9c73b2a30922b623d4e380.jpg)

- 接着，我们把22删除，这种情况的规则：22是非叶子节点，**对于非叶子节点的删除，我们需要用后继key（元素）覆盖要删除的key，然后在后继key所在的子支中删除该后继key**。对于删除22，需要将后继元素24移到被删除的22所在的节点。

![img](https://oscimg.oschina.net/oscnet/6343b9be4ff09d04096e9eac1d500ba0e9d.jpg)

![img](https://oscimg.oschina.net/oscnet/c60eb3ba1f5fac64515eccfc03192f40fdb.jpg)

此时发现26所在的节点只有一个元素，小于2个（m/2），这个节点不符合要求，这时候的规则（向兄弟节点借元素）：**如果删除叶子节点，如果删除元素后元素个数少于（m/2），并且它的兄弟节点的元素大于（m/2），也就是说兄弟节点的元素比最少值m/2还多，将先将父节点的元素移到该节点，然后将兄弟节点的元素再移动到父节点**。这样就满足要求了。

我们看看操作过程就更加明白了。

![img](https://oscimg.oschina.net/oscnet/853a8ead34267d029e8d984958c8d4dbea8.jpg)

![img](https://oscimg.oschina.net/oscnet/0ac5a97219474c5fc16270314e655bf0437.jpg)

- 接着删除28，**删除叶子节点**，删除后不满足要求，所以，我们需要考虑向兄弟节点借元素，但是，兄弟节点也没有多的节点（2个），借不了，怎么办呢？如果遇到这种情况，**首先，还是将先将父节点的元素移到该节点，然后，将当前节点及它的兄弟节点中的key合并，形成一个新的节点**。

![img](https://oscimg.oschina.net/oscnet/e5845ae0ae2fe779a932aa452ce650a4b48.jpg)

移动之后，跟兄弟节点合并。

![img](https://oscimg.oschina.net/oscnet/2dfa4b4474986a121c404f2b7b89579616f.jpg)

##### B+树

- 有m个子树的中间节点包含有m个元素（B树中是k-1个元素），每个元素不保存数据，只用来索引；

- 所有的叶子结点中包含了全部关键字的信息，及指向含有这些关键字记录的指针，且叶子结点本身依关键字的大小自小而大的顺序链接。 (而B 树的叶子节点并没有包括全部需要查找的信息)；

- 所有的非终端结点可以看成是索引部分，结点中仅含有其子树根结点中最大（或最小）关键字。 (而B 树的非终节点也包含需要查找的有效信息)；

- 下面我们看一个B+树的例子

  ![img](https://oscimg.oschina.net/oscnet/bf4d4963b024f6c9fe44d6b26d4f27af54f.jpg)

  ###### 插入操作

  对于插入操作很简单，只需要记住一个技巧即可：**当节点元素数量大于m-1的时候，按中间元素分裂成左右两部分，中间元素分裂到父节点当做索引存储，但是，本身中间元素还是分裂右边这一部分的**。

  下面以一颗5阶B+树的插入过程为例，5阶B+树的节点最少2个元素，最多4个元素。

  - 插入5，10，15，20

  ![img](https://oscimg.oschina.net/oscnet/00114e18a748d2a453007a8b252a43ae8a4.jpg)

  - 插入25，此时元素数量大于4个了，分裂

  ![img](https://oscimg.oschina.net/oscnet/6869626e80377a560361d624f172e1e384f.jpg)

  - 接着插入26，30，继续分裂

  ![img](https://oscimg.oschina.net/oscnet/0a0428579676a0b2543433cafd702029a76.jpg)

  ![img](https://oscimg.oschina.net/oscnet/57e93e2184d5307f303a6f846cdf900345b.jpg)

  有了这几个例子，相信插入操作没什么问题了，下面接着看看删除操作。

  ###### 删除操作

  对于删除操作是比B树简单一些的，因为**叶子节点有指针的存在，向兄弟节点借元素时，不需要通过父节点了，而是可以直接通过兄弟节移动即可（前提是兄弟节点的元素大于m/2），然后更新父节点的索引；如果兄弟节点的元素不大于m/2（兄弟节点也没有多余的元素），则将当前节点和兄弟节点合并，并且删除父节点中的key**，下面我们看看具体的实例。

  - 初始状态

  ![img](https://oscimg.oschina.net/oscnet/365354deaff4bc75d16cf17c3255c46aca4.jpg)

  - 删除10，删除后，不满足要求，发现左边兄弟节点有多余的元素，所以去借元素，最后，修改父节点索引

  ![img](https://oscimg.oschina.net/oscnet/685a53344454eb37d664919a42dac30a45a.jpg)

  - 删除元素5，发现不满足要求，并且发现左右兄弟节点都没有多余的元素，所以，可以选择和兄弟节点合并，最后修改父节点索引

  ![img](https://oscimg.oschina.net/oscnet/f74ef5fff6f4489fbdcaa8d9c113eb48b26.jpg)

  - 发现父节点索引也不满足条件，所以，需要做跟上面一步一样的操作

  ![img](https://oscimg.oschina.net/oscnet/177f38002658f2edfb39a811fed992d58b0.jpg)