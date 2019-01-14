# Exporting function logs to remote syslog service

Fn allows you to push the [function logs to a syslog endpoint](https://github.com/fnproject/docs/blob/master/fn/operate/logging.md#remote-syslog-for-functions). This is an example of how to configure it and test it our using [Papertrail](https://papertrailapp.com)

## step 0 

### create a Papertrail account

Papertrail is a hosted log management service. You can sign up for a [free account](https://papertrailapp.com/signup?plan=free) for the purposes of this tutorial

### create log destination

Go to Settings (top right hand corner) and click on Log Destinations. Create a log destination.

### make a note of your *destination*

On the Papertrail [**Settings**](https://papertrailapp.com/account) choose **Log Destinations** (from the options in the left) and copy the endpoint - you'll use this in subsequent steps

![](settings.jpg)

### For TCP, unselect TLS and select Plain Text

As shown below ... Settings > Log Destination > my.papertrail.com:4242 > Settings

For TCP > Unselect TLS and Select Plain Text

![](unselect-TLS.png)

## Create application

- Get the latest version of Fn CLI - `curl -LSs https://raw.githubusercontent.com/fnproject/cli/master/install | sh`
- Create the application - `fn create app `
- Create an application with `syslog` endpoint info - `fn create app fn-syslog-app --annotation oracle.com/oci/subnetIds=<SUBNETS> --syslog-url tcp://<your papertrail syslog endpoint>` e.g. `fn create app fn-syslog-app --syslog-url tcp://my.papertrail.com:4242`
- Switch to the context for Oracle Functions - `fn use context <context-name>`

## Deploy

- Clone or download this repo
- `cd fn-syslog-example`
- `fn -v deploy --app fn-syslog-app`

Your function should now be deployed. Check it

`fn inspect app fn-syslog-app`

You will see something similar to this - notice the `syslog_url` configuration

	{
                .....
	        "created_at": "2018-08-13T11:39:48.943Z",
	        "id": "01CMSHBGTFNG8G00GZJ0000001",
	        "name": "fn-syslog-app",
	        "syslog_url": "tcp://my.papertrailapp.com:4242",
	        "updated_at": "2018-08-13T11:39:48.943Z"
                .....
	}


## Test

Test using Fn CLI with `fn invoke` command

### Invoke

- `fn invoke fn-syslog-app fn-syslog-func`
- `echo -n 'fun with fn!' | fn invoke fn-syslog-app fn-syslog-func` 

You can repeat this multiple times

### Check your Papertrail a/c

Hop over to your Papertrail [dashboard](https://papertrailapp.com/dashboard) and click **Events** (top right) to see the logs

![](events.jpg)
