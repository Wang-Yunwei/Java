# JavaScript使用记录

--- 

### 自定义指令
```text
directives: {
    clear: {
      // 卸载组件时自动清除v-model值
      unbind: (...arg) => arg[2]?.data?.model?.callback(void 0)
    }
}
```

### 路由

```text
{
    path: '/data-manage',
    redirect: '/data-manage',
    component: Layout,
    children: [
      {
        name: 'List',
        path: '/data-manage',
        component: () => import('@/views/data-manage/index'),
        meta: {
          title: '数据管理',
          icon: 'el-icon-edit'
        }
      },
      {
        name: 'Edit',
        path: '/data-manage/edit',
        hidden: true,
        component: () => import('@/views/data-manage/edit'),
        meta: {
          title: '患者编辑',
          activeMenu: '/data-manage'
        }
      }
    ]
  }
```

- 传入参数且地址栏中带上参数内容

> this.$router.push({path/name: '路由',query:{key: value}})

- 取值方式

> this.$route.query.key

- 传入参数且地址栏中不带参数内容

> this.$router.push({name: ' 路由的name ', params: {key: value}})

- 取值方式

> this.$route.params.key

- 关于this.$router.push、replace、go的区别

```text
this.$router.push: 跳转到指定url路径,并在history栈中添加一个记录,点击后退会返回到上一个页面
this.$router.replace: 跳转到指定url路径,但是history栈中不会有记录,点击返回会跳转到上上个页面
this.$router.go(n): 向前或者向后跳转n个页面,n可为正整数或负整数如果n为0的话,将刷新当前页面
```

### 字符串转时间

> this.moment('2023-01-06 15:24').toDate()

### 对象拷贝

- 浅拷贝

> Object.assign()

- 深拷贝

> const obj2 = JSON.parse(JSON.stringify(obj1))

### 检查对象是否为空

> const isEmpty = obj => Reflect.ownKeys(obj).length === 0 && obj.constructor === Object

### 等到一段时间再执行

> const wait = async (milliseconds) => new Promise((resolve) => setTimeout(resolve, milliseconds));

### 获取两个日期之间的日差

- 日期 (Date)

> const daysBetween = (date1, date2) => Math.ceil(Math.abs(date1 - date2) / (1000 * 60 * 60 * 24))

- 日期 (String)

> (this.$moment(dateStringA).toDate().getTime() - this.$moment(dateStringB).toDate().getTime()) / 1000 / 60 / 60 / 24

### 重定向到另一个URL

> const redirect = url => location.href = url

### 检查设备上的触摸支持

> const touchSupported = () => ('ontouchstart' in window || DocumentTouch && document instanceof DocumentTouch)

### 在元素后插入 html 字符串

> const insertHTMLAfter = (html, el) => el.insertAdjacentHTML('afterend', html)

### 随机排列数组

> const shuffle = arr => arr.sort(() => 0.5 - Math.random())

### 在网页上获取选定的文本

> const getSelectedText = () => window.getSelection().toString()

### 获取随机布尔值

> const getRandomBoolean = () => Math.random() >= 0.5

### 计算数组的平均值

> const average = (arr) => arr.reduce((a, b) => a + b) / arr.length

### Array中Object根据某一字段降序排序

```text
[{createTime: '2022-11-29 16:42:17'},{createTime: '2022-11-30 16:42:17'}].sort((a, b) => {
return a.createTime < b.createTime ? 1 : -1
})
```

### 计算数组中的最大值

> Math.max(...Array)