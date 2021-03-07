# 安装github

#### 学前班问题汇总：

##### GitHub 是什么？

###### 概念：
111
GitHub是通过Git进行版本控制的软件源代码托管服务平台。                        

GitHub里面的项目可以通过标准的Git命令进行访问和操作。同时，所有的Git命令都可以用到GitHub项目上面。                                                                             

网站提供了一系列社交网络具有的功能，例如赞(star)、关注(follow)、评论。用户可以通过复刻(fork)他人项目的形式参与开发，并可通过协作示意图来查看有多少开发者参与了开发并追踪最新的复刻版本。此外网站还有Wiki（通过一个名为 gollum 的软件实现）等功能。       																

GitHub同时允许注册用户和非注册用户在网页中浏览项目，也可以以ZIP格式打包下载。但是用户必须注册登录一个账号才能讨论、创建并编辑项目、参与他人的项目和代码审查。

GitHub支持创建不限数量的公开仓库，已付费用户可以创建私有仓库。2019年1月7日，GitHub宣布免费用户也可以创建私有仓库，私有仓库数量不限但每个仓库最多指定三个合作者。2020年4月14日，GitHub宣布进一步开放核心功能，取消私有仓库合作者数量限制，并降低了收费账号费用。

###### 开发语言

GitHub系统由GitHub公司的开发者Chris Wanstrath开发。系统采用了Ruby on Rails和Erlang作为后端。

###### 主要功能

GitHub通常用于软件开发。GitHub还支持以下格式和功能：

- 文档：包括自动生成的、采用类Markdown语言的Readme文件（称作GitHub Flavored Markdown, GFM）。
- 问题追踪系统（同时可用于功能需求）
- Wiki
- GitHub Pages支持用户通过软件仓库创建静态网站或静态博客（通过一个名为Jekyll的软件实现，但是也支持采用诸如 Hexo 等其他博客引擎搭建）。
- 任务列表
- 甘特图
- 可视化的地理位置分析
- 预览3D渲染文件。预览功能通过WebGL和Three.js实现。
- 预览Adobe Photoshop的PSD文件，甚至可以比较同一文件的不同版本。

##### Git 是什么？

###### 概念

git是一个分布式版本控制软件，最初由林纳斯·托瓦兹创作，于2005年以GPL发布。最初目的是为更好地管理Linux内核开发而设计。应注意的是，这与GNU Interactive Tools（一个类似Norton Commander界面的文件管理器）不同。

git最初的开发动力来自于BitKeeper和Monotone。git最初只是作为一个可以被其他前端（比如Cogito或Stgit）包装的后端而开发的，但后来git内核已经成熟到可以独立地用作版本控制。很多著名的软件都使用git进行版本控制，其中包括Linux内核、X.Org服务器和OLPC内核等项目的开发流程。

###### 主要功能

git是用于Linux内核开发的版本控制工具。与CVS、Subversion一类的集中式版本控制工具不同，它采用了分布式版本库的作法，不需要服务器端软件，就可以运作版本控制，使得源代码的发布和交流极其方便。git的速度很快，这对于诸如Linux内核这样的大项目来说自然很重要。git最为出色的是它的合并追踪（merge tracing）能力。

实际上内核开发团队决定开始开发和使用git来作为内核开发的版本控制系统的时候，世界上开源社区的反对声音不少，最大的理由是git太艰涩难懂，从git的内部工作机制来说，的确是这样。但是随着开发的深入，git的正常使用都由一些友善的命令来执行，使git变得非常好用。现在，越来越多的著名项目采用git来管理项目开发，例如：wine、U-boot等。

作为开源自由原教旨主义项目，git没有对版本库的浏览和修改做任何的权限限制，通过其他工具也可以达到有限的权限控制，比如：gitosis、CodeBeamer MR。原本git的使用范围只适用于Linux/Unix平台，但在Windows平台下的使用也日渐成熟，这主要归功于Cygwin、msysgit环境，以及TortoiseGit这样易用的GUI工具。git的源代码中也已经加入了对Cygwin与MinGW编译环境的支持且逐渐完善，为Windows用户带来福音。

###### 实现原理

git和其他版本控制系统（如CVS）有不小的差别，git本身关心文件的整体性是否有改变，但多数的版本控制系统如CVS或Subversion系统则在乎文件内容的差异。git拒绝保持每个文件的版本修订关系。因此查看一个文件的历史需要遍历各个history快照；git隐式处理文件更名，即同名文件默认为其前身，如果没有同名文件则在前一个版本中搜索具有类似内容的文件。

git更像一个文件系统，直接在本机上获取资料，不必连线到主机端获取资料。 每个开发者都可有全部开发历史的本地副本，changes从这种本地repository复制给其他开发者。这些changes作为新增的开发分支被导入，可以与本地开发分支合并。

分支是非常轻量级的，一个分支仅是对一个commit的引用。
git是用C语言开发的，以追求最高的性能。git自动完成垃圾回收，也可以用命令git gc --prune直接调用。

git存储每个新创建的object作为一个单独文件。为了压缩存储空间占用， packs操作把很多文件（启发式类似名字的文件往往具有类似内容）使用差分压缩入一个文件中（packfile），并创建一个对应的索引文件，指明object在packfile中的偏移值。新创建的对象仍然作为单独文件存在。repacks操作非常费时间，git会在空闲时间自动做此操作。也可用命令git gc来直接启动repack。packfile与索引文件都用SHA-1作为校验和并作为文件名。git fsck命令做校验和的完整性验证。
Git服务器典型的TCP监听端口为9418。

###### 库目录

- hooks：存储钩子的文件夹

- logs：存储日志的文件夹

- refs：存储指向各个分支的指针（SHA-1标识）文件

- objects：存放git对象
- config：存放各种设置文档
- HEAD：指向当前所在分支的指针文件路径，一般指向refs下的某文件

###### 数据结构

Git有两种数据结构：可变的索引（index或stage或cache)用于缓冲工作目录信息与下一次提交的版本信息；不变的、仅追加的对象数据库。
对象数据库包含4类对象：

- blob (二进制大对象)是使用zlib压缩算法对一个文件的内容压缩后的结果。Blobs没有保存文件名、时间戳或其他元数据。Git将其存储在位于隐藏的.git/objects文件夹中。文件的名称为使用SHA-1哈希函数对原文件内容生成的哈希值。这些对象文件称为Blob，每次将新文件添加到存储库时会创建Blob对象。
- tree对象对应于文件目录。包含文件名列表以及文件的类型比特（包含许可权）、到blob（对应于文件）或tree对象的引用。tree对象是源树(source tree)的快照。用默克树实现。
- commit对象链接tree对象在一起而成为history，包含顶层源目录的tree对象名字、一个时间戳、log信息、0个或多个父commit对象的名字。用于保存特定版本的树型文件夹结构以及提交作者，电子邮件地址，日期和描述性提交消息。
- tag对象是一个容器，包含了到另一个对象的引用，也可以增加关于另外对象的元数据。通常它保存需要追溯的特定版本数据的一个commit对象的数字签名。

以上4类的对象用其内容的SHA-1 hash值标识：hash值的前两个字符作为存放的目录名字，其余hash字符作为这个对象的文件名。
Git数据库中不变引用的对象将会被垃圾回收清除。Git命令可以创建、移动、删除引用。"git show-ref"列出所有引用。某些引用类型：

- heads: 引用一个本地对象，是commit的指针。每个head可以指任意一个这样的指针。可以包含任意数量的heads。而"HEAD"（全部大写），仅仅指的是当前有效的head。默认情况下，在每个仓库下都有一个head，叫做master。
- remotes: 引用远程repository中的一个对象
- stash: 引用一个还没有committed的一个对象
- meta: 例如一个bare repository中的一个配置, 用户权限; refs/meta/config名字空间等

- tags:

某些操作（例如，将提交推送到远程存储库，存储太多对象或手动运行Git的垃圾收集命令）可能会导致Git将对象重新打包为打包文件，在打包过程中，采用反向差异并进行压缩以消除多余的内容并减小尺寸。该过程将生成包含对象内容的.pack文件，每个文件都有一个对应的.idx索引文件，其中包含对打包对象及其在打包文件中位置的引用。当将分支推送到远程存储库或从远程存储库拉出分支时，这些打包文件将通过网络传输。提取或获取分支时，将打包文件解压缩以在对象存储库中创建松散对象。

##### 常用Git命令汇总

![img](http://www.ruanyifeng.com/blogimg/asset/2015/bg2015120901.png)

1.添加指定文件到暂存区           																			   git add [file1] [file2] ...

2.添加指定目录到暂存区，包括子目录																   git add [dir]

3.添加当前目录的所有文件到暂存区																	   git add .

4.对于同一个文件的多处变化，可以实现分次提交										        git add -p

5.增加一个新的远程仓库，并命名
git remote add [shortname] [url]

6.提交暂存区到仓库区
git commit -m [message]

7.提交暂存区的指定文件到仓库区
git commit [file1] [file2] ... -m [message]

8.提交工作区自上次commit之后的变化，直接到仓库区
git commit -a

9.提交时显示所有diff信息
git commit -v

10.使用一次新的commit，替代上一次提交  															      如果代码没有任何新变化，则用来改写上一次commit的提交信息
git commit --amend -m [message]

11.重做上一次commit，并包括指定文件的新变化
git commit --amend [file1] [file2] ...

**git push的一般形式为 git push <远程主机名> <本地分支名>  <远程分支名> ，例如 git push origin master：refs/for/master ，即是将本地的master分支推送到远程主机origin上的对应master分支， origin 是远程主机名，第一个master是本地分支名，第二个master是远程分支名。**

12.推送所有分支到远程仓库
git push [remote] --all

13.上传本地指定分支到远程仓库
git push [remote] [branch]

14.强行推送当前分支到远程仓库，即使有冲突
git push [remote] --force

**git pull命令的作用是：取回远程主机某个分支的更新，再与本地的指定分支合并。基本用法：git pull <远程主机名> <远程分支名>:<本地分支名>**

15.将远程主机origin的master分支拉取过来，与本地的brantest分支合并         git pull origin master:brantest

16.将远程origin主机的master分支拉取过来和本地的当前分支进行合并           git pull origin master

git pull = git fetch + git merge 																				git pull --rebase = git fetch + git rebase

**一旦远程主机的版本库有了更新（Git术语叫做commit），需要将这些更新取回本地，这时就要用到git fetch命令。**

17.将某个远程主机的更新，全部取回本地 	                        								   git fetch<远程主机名>

18.如果只想取回特定分支的更新，可以指定分支名	 											git fetch<远程主机名><分支名>

**远程操作的第一步，通常是从远程主机克隆一个版本库，这时就要用到git clone。**

19.从远程主机克隆一个版本库	 																			git clone<版本库的网址>

20.如果要指定不同的目录名，可以将目录名作为git clone命令的第二个参数	 git clone<版本库的网址><本地目录名>

**为了便于管理，Git要求每个远程主机都必须指定一个主机名。git remote命令就用于管理主机名。**

21.列出所有远程主机	 																							git remote

22.查看远程主机的网址    																						 git remote -v

**取回远程主机的更新以后，可以在他的基础上，使用git checkout命令创建一个新的分支。**

23.在origin/master的基础上，创建一个新的分支												git checkout -b newBrach origin/master

24.在本地分支上合并远程分支 																				git merge origin/master 																							git rebase origin/master

**Git的设置文件为.gitconfig，它可以在用户主目录下（全局配置），也可以在项目目录下（项目配置）。**

25.显示当前的Git配置
git config --list

26.编辑Git配置文件
git config -e [--global]

27.设置提交代码时的用户信息
git config [--global] user.name "[name]"
git config [--global] user.email "[email address]"

28.删除工作区文件，并且将这次删除放入暂存区
git rm [file1] [file2] ...

**分支**

29.列出所有本地分支
git branch

30.列出所有远程分支
git branch -r

31.列出所有本地分支和远程分支
git branch -a

**标签**

32.列出所有tag
git tag

33.新建一个tag在当前commit
git tag [tag]

34.删除本地tag
git tag -d [tag]

35.删除远程tag
git push origin :refs/tags/[tagName]

36.查看tag信息
git show [tag]

37.提交所有tag
git push [remote] --tags

38.提交指定tag
git push [remote] [tag]

**查看信息**

39.显示有变更的文件
git status

40.显示当前分支的版本历史
git log

41.搜索提交历史，根据关键词
git log -S [keyword]

42.显示今天你写了多少行代码
git diff --shortstat "@{0 day ago}"

**撤销**

43.恢复暂存区的指定文件到工作区
git checkout [file]

44.恢复某个commit的指定文件到暂存区和工作区
git checkout [commit] [file]

45.恢复暂存区的所有文件到工作区
git checkout .

**其他**

46.生成一个可供发布的压缩包
git archive



**由于家里实在太吵以及琐碎事物繁多，硅谷只看完第二季。并且没有看到什么技术错误。**



