# Introduction

couchdb-scala is a Scala wrapper for CouchDB. On the backend, it uses 
[Dispatch](http://dispatch.databinder.net/Dispatch.html) to query the server 
and [Lift-Json](https://github.com/lift/lift/tree/master/framework/lift-base/lift-json/>)
to process the JSON responses.

This library is not production ready. It is currently in active development for
use in [Emergenesis'](http://www.emergenesis.com>)
[Citeplasm](http://wiki.emergenesis.com/Citeplasm) project.

## Installation

There are no installation scripts or pre-compiled binaries currently. You can
package this yourself using [SBT](https://github.com/harrah/xsbt/wiki):

```bash
$ sbt
sbt> compile
sbt> package
```

# Usage

Below is a summary of the current scope of couchdb-scala along with its basic
usage. Full API documentation is available at http://apis.emergenesis.com/couchdb-scala/ .

## Connecting to a database

```scala
import org.gestaltfoundation.couchdb._

val conn = new Connection ( "localhost", 5984 )

conn.couch_version // res0: String = 1.2.0a-

// db: org.gestaltfoundation.couchdb.Db = org.gestaltfoundation.couchdb.Db@41b0b9ed
val db = conn( "testdb" )
```

## Managing DBs

```scala
import org.gestaltfoundation.couchdb._

// connect to localhost
val conn = new Connection ( "localhost", 5984 )

// get a list of databases
conn.listDatabases // res1: List[String] = List(_replicator, _users)

// create a database
conn.createDatabase ( "testdb" ) // res2: Boolean = true

// get a list of databases
conn.listDatabases // res3: List[String] = List(_replicator, _users, testdb)

// delete the database
conn.deleteDatabase ( "testdb" ) // res4: Boolean = true

// get a list of databases
conn.listDatabases // res5: List[String] = List(_replicator, _users)
```

## Managing Documents

### Creating a New Document

```scala
import org.gestaltfoundation.couchdb._

val conn = new Connection ( "localhost", 5984 )
val db = conn( "testdb" )

// case classes are required as they will be serialized to JSON
case class TestObject ( testString: String )

// create a new instance of the object and save it
val obj = TestObject ( "Hello!" )
val id = db.save( obj ) // id: String = d1652d6ee76221dec66e26cb6f0024e7
```

