# SlurpSlurp

Slurp is an extension for Burp Suite. It aims to automate some really basic but tedious checks that are often forgotten or overlooked during Web Security Assessment / Pentest. 

## Synopsis & Motivation

I started developing this small Burp extension when I was working as a pentester at [XMCO](https://www.xmco.fr/). I was tired of always running the same basic checks manually. On the other hand I do not like relying on fully-automated tools. They can have unpredictable behaviours and easily make a poorly coded app or badly managed environment crash...

I wrote this small Burp extension in order to automate these checks in a safe and reliable way so I can focus on the "real pentesting" part of the assessment.

And yes, I do get the irony of programming a (mostly) automated tool in order to avoid automated tools...

## Features

Slurp implements 2 types of checks : passive and active.

Passive checks are just *parsing the content* of the requests and responses received by Burp. Slurp actually implements checks that detect:

* Forms and AJAX forms
* Password and hidden fields
* Upload features
* Authentication over HTTP
* Comments in pages source code, IP and links in them
* Missing cookie attributes
* Common error messages
* HTTP headers
* Local IP addresses
* Possibly reflected parameters and basic XSS attempts
* WSDL documentation
* Custom regex

Active checks issue *additional requests* in order to discover useful information or vulnerabilities. Slurp actually implements checks that:

* Test for the presence of :
  * crossdomain files
  * robots.txt
  * trace.axd
  * sitemap
* Try various HTTP methods
* Attempt VERB tampering authentication bypass upon receiving a 401 error code
* Split the URI on '/' and try to find:
  * Directory listings
  * Backup files
  * Common files (CHANGELOG, README, ...)
  * Repository artifacts (.git, .svn, etc.)
  * WSDL files

I have plan to implement other checks in both of these categories, but not much time to implement them. So, stay tuned :-)

## Limitations and bugs

Probably so many that I will not list them. Feel free to open an issue / feature request, I might fix / implement it one day :-)

## Building Slurp

I use Jetbrains' excellent IDE Intellij to develop this extension, but the included `pom.xml` file should allow building and packaging the project with Maven.

Building the project should be as easy as:

```
git clone <this project>
mvn package
...
Profit!
```

## Usage

Just load the jar file into Burp Extender, you can then see checks results in the "Slurp" tab. Please note that Slurp only run checks on *in-scope* targets.

More details later, maybe even screenshots too ;-)

## Implementing new checks / contributing

All checks in Slurp are implementations of abstract classes and instantiated using a simple (and probably badly implemented) Factory design pattern. If you can read Java (oh, lucky you), it should be fairly easy to write your own.

More on this later ;-)

## Disclaimer

I am in no way a professional Java developer (and sincerely hope that I will never be) and this extension is meant to be used only by me. You should consider this program as badly coded, probably bugged and absolutely not optimized in any way. You have been warned :-)

## Credits & contributors

I would like to thanks all members of the pentest Team and the CERT at [XMCO](https://www.xmco.fr/) for their valuable inputs and feedback.
Many thanks to my former managers for giving me time to work on this small tool.

Thanks to [Portswigger](https://portswigger.net/) for making a free version of Burp Suite and releasing an extension API.

## License

Copyright (c) 2016, Marc Lebrun

Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
