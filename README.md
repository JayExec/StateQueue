# StateQueue
An implementation of a bidirectional queue, allowing for effective *Undo* and *Redo* operations. Extensively uses the *Snapshot* design pattern.

## Using
1. Clone the project repository:
```shell
$ git clone https://github.com/JayExec/JavaUtilities.git # HTTPS
$ git clone git@github.com:JayExec/JavaUtilities.git # SSH
```
2. Install the JAR file to the local Maven repository:
```shell
$ mvn install
```
3. Add following lines to Your project's `pom.xml` file:
```xml
<dependencies>
    <dependency>
        ...
        <groupId>io.github.jayexec</groupId>
        <artifactId>statequeue</artifactId>
        <version>1.0</version>
        ...
    </dependency>
</dependencies>
```
4. Import required classes.
```java
import io.github.jayexec.statequeue.StateQueue;
```