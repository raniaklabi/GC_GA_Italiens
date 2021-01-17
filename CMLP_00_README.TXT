Description of instances for WSN-CMLP


The first line is a comment (#) containing the name of the file and optimal lifetime value when it is known.


The next line is made of four numbers

|S| |T| Rs Rc 

Where:


* |S| is the number of sensors in {100, 200, 300, 400, 500}

* |T| is the number of targets in {15, 30}

* Rs  is the maximum sensing range of each sensor in {100, 125}

* Rc  is the maximum communication range of each sensor in {125}


The next line present two numbers corresponding to the location of the base station:

  X Y


* X is the coordinate x of the location of the base station

* Y is the coordinate y of the location of the base station


The next |S| lines present three numbers:

  X Y bi 

Where:


* X is the coordinate x of the location of the sensor

* Y is the coordinate y of the location of the sensor

* bi is the initial amount of energy of the sensor


The next |T| lines present two numbers:

  X Y


* X is the coordinate x of the location of the target

* Y is the coordinate y of the location of the target
