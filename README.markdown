# Introduction

couchdb-scala is a Scala wrapper for CouchDB. On the backend, it uses 
`Dispatch <http://dispatch.databinder.net/Dispatch.html>` to query the server 
and `Lift-Json <https://github.com/lift/lift/tree/master/framework/lift-base/lift-json/>` 
to process the JSON responses.

This library is not production ready. It is currently in active development for
use in `Emergenesis' <http://www.emergenesis.com>` 
`Citeplasm <http://wiki.emergenesis.com/Citeplasm>` project.

## Installation

There are no installation scripts or pre-compiled binaries currently. You can
package this yourself using `SBT <https://github.com/harrah/xsbt/wiki>`:

```bash
$ sbt
sbt> compile
sbt> package
```

# Usage

## Connecting to a database

```scala
import org.gestaltfoundation.couchdb._

val conn = new Connection ( "localhost", 5984 )
val databases = conn.listDatabases
```
