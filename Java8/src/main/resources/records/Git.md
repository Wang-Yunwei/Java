# Git命令集

---

## 生成公密钥

> ssh-keygen -t rsa

## 基础命令

- 暂存本地代码
  > git stash
- 拉取代码
  > git pull --rebase
- 取回暂存代码
  > git stash pop
- 提交代码到dev_ESHub_2.0_zhengqi分支
  > git push origin HEAD:refs/for/dev_ESHub_2.0_zhengqi
- 回滚提交代码
  > git reset --soft HEAD^
- 合并代码
  > git merge --no-ff
- 忽略文件,在[file_name]处输入要忽略的文件
  > git update-index --assume-unchanged [file_name]
- 还原被忽略的文件,在[file_name]处输入要忽略的文件
  > git update-index --no-assume-unchanged [file_name]
- 还原所有被忽略的文件
  > git ls-files -v | grep '^h' | awk '{print $2}' |xargs git update-index --no-assume-unchanged
- 查询代码提交行数
  > git log --since="2021-01-01" --before="2021-01-21" --author="$(git config --get user.name)" --pretty=tformat:
  --numstat | gawk '{ add += $1 ; subs += $2 ; loc += $1 - $2 } END {
  printf "[addedLines:%s,removedLines:%s,totalLines:%s]\n",add,subs,loc }'

## 远程操作

- 查看远程版本库信息
  > git remote -v
- 查看指定远程版本库信息
  > git remote show <remote>
- 添加远程版本库
  > git remote add <remote><url>
- 远程库获取代码
  > git fetch <remote>
- 下载代码及快速合并
  > git pull <remote><branch>
- 上传代码及快速合并
  > git push <remote><branch>
- 删除远程分支或标签
  > git push <remote>:<branch/tag-name>
- 上传所有标签
  > git push --tags

## 合并与衍合

- 合并指定分支到当前分支
  > git merge <branch>
- 衍合指定分支到当前分支
  > git rebase <branch>

## 分支与标签

- 显示所有本地分支
  > git branch
- 显示所有远程分支
  > git branch -r
- 显示当前远程分支结构
  > git branch -a
- 切换到指定分支或标签
  > git checkout <branch/tag>
- 新建本地分支
  > git branch <new_branch>
- 删除本地分支
  > git branch -d <branch>
- 新建远程分支
  > git remote add <name>
- 删除远程分支
  > git remote remove <name>
- 列出所有本地标签
  > git tag
- 基于最新提交创建新标签
  > git tag <tag_name>
- 删除标签
  > git tag -d <tag_name>

## 撤销

- 撤销工作目录中所有未提交文件的修改内容
  > git reset --hard HEAD
- 撤销指定的未提交文件的修改内容
  > git checkout HEAD <file_name>
- 撤销指定的提交
  > git revert <commit>

## 查看提交历史

- 查看提交历史
  > git log
- 查看指定文件的提交历史
  > git log -p <file_name>
- 以列表方式查看指定文件的提交历史
  > git blame <file_name>

## 修改和提交

- 查看状态
  > git status
- 查看变更内容
  > git diff
- 跟踪所有改动过的文件
  > git add .
- 跟踪指定的文件
  > git add <file_name>
- 文件改名
  > git mv <old> <new>
- 删除文件
  > git rm <file>
- 停止跟踪文件但不删除
  > git rm --cached <file>
- 提交所有更新过的文件
  > git commit -m "commit message"

## 创建版本库

- 克隆远程版本库
  > git clone <url>
- 初始化本地版本库
  > git init