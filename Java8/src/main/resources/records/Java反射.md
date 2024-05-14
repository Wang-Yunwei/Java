# JAVA 反射

---

## 三种方式获取Class对象

- 通过实例对象获取Class对象
> User user = new User(); Class<? extends User> userClass = user.getClass();

- 通过类名.class获取Class对象
> Class<User> userClass = User.class;

- 通过Class.forName(String className)获取Class对象
> Class<?> userClass = Class.forName("com.example.User");

## 获取到Class对象,能拿到哪些信息

### 一、名称信息

- 返回Java内部使用的真正的名称
> public String getName();
- 返回的名称不带包信息
> public String getSimpleName();
- 返回的名称更加友好
> public String getCanonicalName();
- 返回包信息
> public String getPackage();

### 二、字段信息

- 返回所有的 public 字段,包括其父类的,如果没有字段,返回空数组
> public Field[] getFields();
- 返回本类声明的所有字段,包括非 public 的,但不包括父类的
> public Field[] getDeclaredFields();
- 返回本类或父类中指定名称的 public 字段,找不到抛出异常 NoSuchFieldException
> public Field getField(String name);
- 返回本类中声明的指定名称的字段,找不到抛出异常 NoSuchFieldException
> public Field getDeclaredField(String name);

#### 获取到Field以后,进一步获取字段信息

- 获取字段的名称
> public String getName();
- 判断当前程序是否有该字段的访问权限
> public boolean isAccessible();
- flag 设为 true 表示忽略 Java 的访问检查机制,以允许读写非 public 的字段
> public void setAccessible(boolean flag);
- 获取指定对象 obj 中该字段的值
> public Object get(Object obj);
- 将指定对象obj中该字段的值设为value
> public void set(Object obj, Object value);
- 返回字段的修饰符
> public int getModifiers();
- 返回字段的类型
> public Class<? > getType();
- 查询字段的注解信息
> public <T extends Annotation> T getAnnotation(Class<T> annotationClass);
> public Annotation[] getDeclaredAnnotations();

### 三、方法信息

- 返回所有的public方法，包括其父类的，如果没有方法，返回空数组
> public Method[] getMethods();
- 返回本类声明的所有方法，包括非public的，但不包括父类的
> public Method[] getDeclaredMethods();
- 返回本类或父类中指定名称和参数类型的public方法,找不到抛出异常NoSuchMethodException
> public Method getMethod(String name, Class<? >... parameterTypes);
- 返回本类中声明的指定名称和参数类型的方法，找不到抛出异常NoSuchMethodException
> public Method getDeclaredMethod(String name, Class<? >... parameterTypes);

#### 获取到Method后,进一步获取方法的详细信息
- 获取方法的名称
> public String getName();
- flag 设为 true 表示忽略 Java 的访问检查机制,以允许调用非public的方法
> public void setAccessible(boolean flag);
- 在指定对象 obj 上调用 Method 代表的方法,传递的参数列表为args
> public Object invoke(Object obj, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;

### 四、创建对象和构造方法

- Class直接创建对象,调用无参构造器
> public T newInstance();

#### Class获取构造器

- 获取所有的 public 构造方法,返回值可能为长度为0的空数组
> public Constructor<? >[] getConstructors();
- 获取所有的构造方法,包括非 public 的
> public Constructor<? >[] getDeclaredConstructors();
- 获取指定参数类型的 public 构造方法,没找到抛出异常 NoSuchMethodException
> public Constructor<T> getConstructor(Class<? >... parameterTypes);
- 获取指定参数类型的构造方法,包括非public的,没找到抛出异常 NoSuchMethodException
> public Constructor<T> getDeclaredConstructor(Class<? >... parameterTypes);

##### 获取到构造器用构造器创建对象
> public T newInstance(Object ... initArgs);

### 五、类型检查和类型转换
- 类型检查
> public native boolean isInstance(Object obj);
- 类型转换
> public T cast(Object obj);
