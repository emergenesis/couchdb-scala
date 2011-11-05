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
usage. Full API documentation is available at http://couchdb.docs.emergenesis.com .

__NOTE:__ The URL for the API documentation has changed. See above for new URL.

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

// case classes that inherit the trait Entity are required as they will be
// serialized to JSON; the Entity trait provides convenience features like
// an id field to be populated on save; the string passed to Entity will be set
// as the 'Type' field in the resulting CouchDB document
case class TestObject ( testString: String ) extends Entity ( "test" )

// create a new instance of the object and save it
val obj = TestObject ( "Hello!" )
val success = db.save( obj ) // success: Boolean = true
obj.id // res0: String = d1652d6ee76221dec66e26cb6f0024e7
obj.revision // res1: String = 1-89f9f89dd99c9c282
```

### Destroying a Document

```scala
import org.gestaltfoundation.couchdb._

val conn = new Connection ( "localhost", 5984 )
val db = conn( "testdb" )

case class TestObject ( testString: String ) extends Entity ( "test" )
val obj = TestObject ( "Hello!" )
var success = db.save( obj ) // success: Boolean = true
obj.id // res0: String = d1652d6ee76221dec66e26cb6f0024e7
obj.revision // res1: String = 1-89f9f89dd99c9c282

// destory the document at its current revision
success = db.destroy ( obj ) // success: Boolean true
obj.id // res2: String = ""
obj.revision // res3: String = ""
```

