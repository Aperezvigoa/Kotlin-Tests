package org.example

class User constructor(_username: String, _realname: String) {
    var username: String = _username
        set (value) {
            if (value.isNotBlank()) {
                field = value
                println("new username: $field assigned succesfully")
            }
            else
                println("Not valid username")
        }
        get () {
            return field
        }

    val realName: String = _realname
        get() {
            println("Real name of the user: $field")
            return field
        }

    private val _totalFollowers: MutableSet<User> = mutableSetOf<User>()
    val totalFollowers: Set<User> get() = _totalFollowers

    private val _totalFollowing: MutableSet<User> = mutableSetOf<User>()
    val totalFollowing: Set<User> get() = _totalFollowing

    private val _publications: MutableList<Publication> = mutableListOf()
    val publications: List<Publication> get() = _publications

    init {
        println("User registered successfully!")
    }

    fun followUser (user: User) {
        if (!totalFollowers.contains(user)) {
            _totalFollowing.add(user)
            user._totalFollowers.add(this)
            println("${this.username} followed ${user.username} successfully!")
        }
        else
            println("You are following ${user.username}")
    }

    fun unfollowUser (user: User) {
        if (totalFollowing.contains(user)) {
            _totalFollowing.remove(user)
            user._totalFollowers.remove(this)
            println("${user.username} unfollowed successfully!")
        }
        else
            println("You are not following ${user.username}")
    }

    fun newPublication() {
        println("What's happening?")
        var tweet:String
        do {
            tweet = readln()
        } while(tweet.isBlank())

        val publication = Publication(tweet, this)
        _publications.add(publication)
    }

    private fun _likePost (publication: Publication) {
        publication.recieveLike(this)
    }

    private fun _showUserPost (user: User) {
        var index = 1
        for (post in user.publications) {
            println("$index.-")
            println(post.content)
            println("=======================")
            index++
        }
    }

    fun likePost(user: User) {
        if (user.publications.isNotEmpty()) {
            _showUserPost(user)
            println("Choose the post ❤")
            var choice: Int = readln().toInt() - 1
            if (choice < 0 || choice >= user.publications.size)
                println("Publication not found... 😒")
            else
                _likePost(user.publications[choice])
        } else
            println("${user.username} have not posted anything yet.")
    }

    fun feed() {
        if (totalFollowing.isNotEmpty()) {
            for (followingUser in totalFollowing) {
                if (followingUser.publications.isNotEmpty()) {
                    for (publication in followingUser.publications) {
                        println()
                        println("${followingUser.username} posted:")
                        println("-------------$publication-------------")
                        println(publication.content)
                        println("${publication.likes.size} ❤")
                    }
                } else
                    println("${followingUser.username} have not posted anything yet.")
            }
        } else
            println("You should follow other users... ")
    }
}

class Publication constructor(_content: String, _author: User) {
    val content: String = _content
    val author: User = _author
    private val _likes: MutableList<User> = mutableListOf()
    val likes: List<User> get() = _likes

    init {
        println("Publicated successfully")
    }

    fun recieveLike(user: User) {
        if (!likes.contains(user)) {
            _likes.add(user)
            println("${user.username} ❤ this post!")
        } else
            println("Cant give another like!")
    }
}

class SocialMedia {
    val name: String = "Kotlin today"
    val _registeredUsers: MutableList<User> = mutableListOf()
    val registeredUsers: List<User> get() = _registeredUsers
    private val _usedUsernames: MutableList<String> = mutableListOf()

    private fun _checkUsername(username: String) = _usedUsernames.contains(username)

    fun newRegister() {
        println("Please, enter the username.")
        var username = readln()
        if (_checkUsername(username) || username.isBlank()) {
            do {
                println("Username not available. Try again.")
                username = readln()
                if (!_checkUsername(username) && username.isNotBlank())
                    break
            } while (true)
        }
        _usedUsernames.add(username)
        println("Please, enter your real name.")
        val realName = readln()

        val newUser = User(username, realName)
        _registeredUsers.add(newUser)
    }

    fun findUser() {
        println("Find by username:")
        if (registeredUsers.isNotEmpty()) {
            val username = readln()
            for (user in registeredUsers) {
                if(username == user.username) {
                    println(user.username)
                    println("Followers: ${user.totalFollowers.size} Following: ${user.totalFollowing.size}")
                }
            }
        } else
            println("The social media is empty 🐱‍🐉")
    }

    fun moreFollowedUser() {
        var topUser: User? = null
        for (user in registeredUsers) {
            topUser = user
            if (topUser.totalFollowers.size < user.totalFollowers.size)
                topUser = user
        }

        println("The most followed user is ${topUser?.username} ")
    }

    fun moreLikedPost() {
        var topPost: Publication? = null
        for (user in registeredUsers) {
            for (post in user.publications) {
                topPost = post
                if (topPost.likes.size > post.likes.size)
                    topPost = post
            }
        }
        println("The TOP 1 post is: ")
        println(topPost?.content)
        println("${topPost?.likes?.size} ❤")
    }
}

fun main() {
    val socialMedia = SocialMedia()
    socialMedia.moreFollowedUser()
    socialMedia.findUser()
    socialMedia.newRegister()
    socialMedia.moreFollowedUser()
    socialMedia.findUser()
}