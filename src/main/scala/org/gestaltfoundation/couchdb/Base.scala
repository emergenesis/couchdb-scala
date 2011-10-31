package org.gestaltfoundation.couchdb

import dispatch._

trait Base {

    def get ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ) as_str )
        http.shutdown
        new Response ( resp )
    }

    def put ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).PUT.as_str )
        http.shutdown
        new Response ( resp )
    }

    def delete ( urlstring: String ) = {
        val http = new Http
        val resp = http ( url ( urlstring ).DELETE.as_str )
        http.shutdown
        new Response ( resp )
    }
}

// vim: set ts=4 sw=4 et:
