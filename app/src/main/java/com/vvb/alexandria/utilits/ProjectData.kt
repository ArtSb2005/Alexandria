package com.vvb.alexandria.utilits

class ProjectData {
    /**set Data*/
    var name :String? = null
    var info:String? = null
    var link:String? = null
    var author:String? = null
    var date:String? = null
    var img:String? = null
    constructor(){}

    constructor(
        name:String?,
        info:String?,
        link:String?,
        author:String?,
        date:String?,
        img:String?,
    ){
        this.name = name
        this.info = info
        this.link = link
        this.author = author
        this.date = date
        this.img = img
    }
}