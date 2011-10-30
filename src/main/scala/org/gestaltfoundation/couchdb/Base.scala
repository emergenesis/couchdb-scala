package org.gestaltfoundation.couchdb

import dispatch._

trait Base {
    val http = new Http

    def getResponse ( urlstring: String ) = {
        var resp = http ( url ( urlstring ) as_str )
        new Response ( resp )
    }
}

// vim: set ts=4 sw=4 et:
