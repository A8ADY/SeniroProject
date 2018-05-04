import processing.serial.*;
import cc.arduino.*;
import org.firmata.*;
import netP5.*;
import oscP5.*;

Arduino arduino;
int servoPin = 0;

OscP5 oscP5;
float directLeft;
float directRight;
float directBackward;
float directForward;
float med;
float excit;
float push;
float pull;

int circlex = 400;
int circley = 400;
void setup() {
  
  oscP5 = new OscP5(this, 57110);
  size(800, 800);
  frameRate(30);
  smooth();
  //arduino = new Arduino(this, "Serial#", 9600);
 // arduino.pinMode(servoPin, Arduino.SERVO);
  
}

void draw() {
  background(0);
  //drawArrow(250,250,60,270);
  textSize(20);
  fill(255);
  text("Meditiation:", 0,50); 
  drawGraph(150, 40, med);
  text("Excitement:", 0,100); 
  drawGraph(150, 90, excit);
  
  //if((directLeft >= 0.5) && (circlex >= 0)) {
  //  circlex -= 5;
  //  float val = map(directLeft, 0, 1, 90, 180);
  //  arduino.servoWrite(servoPin, int(val));
  //} else if ((directRight >= 0.5) && (circlex <= 800)) {
  //  circlex += 5;
  //  float val = map(directLeft, 0, 1, 0, 90);
  //  arduino.servoWrite(servoPin, int(val));
  //} else if ((directForward >= 0.5) && (circley >= 0)) {
  //  circley += 5;
  //} else if ((directForward >= 0.5) && (circley <= 800)) {
  //  circley -= 5;
  //}

  fill(255,0,0);
  ellipse(circlex,circley,200,100);

}

void drawArrow(int cx, int cy, int len, float angle){
  pushMatrix();
  translate(cx, cy);
  rotate(radians(angle));
  line(0,0,len, 0);
  line(len, 0, len - 8, -8);
  line(len, 0, len - 8, 8);
  popMatrix();
}

void drawGraph(int x, int y, float cogVal) {
  
  float len = map(cogVal, 0.0, 1.0, 0, 600);
  rect(x, y, len, 10);
}


void oscEvent(OscMessage theOscMessage) {
  
  //getting value of right (when user thinks of moving/turning right
  if(theOscMessage.checkAddrPattern("/COG/RIGHT") == true) {
    
    directRight = theOscMessage.get(0).floatValue();
    print(" addrpattern: "+theOscMessage.addrPattern());
    println(" typetag: "+theOscMessage.typetag()+"value= "+theOscMessage.get(0).floatValue());
    if ((circlex <= 800)) {
    circlex += 20;
    } 
  }
      
  //value of left
  else if (theOscMessage.checkAddrPattern("/COG/LEFT") == true) {
    
    directLeft = theOscMessage.get(0).floatValue();
    print(" addrpattern: "+theOscMessage.addrPattern());
    println(" typetag: "+theOscMessage.typetag()+"value= "+theOscMessage.get(0).floatValue());
  
    if((circlex >= 0)) {
    circlex -= 20;
    //float val = map(directLeft, 0, 1, 90, 180);
    //arduino.servoWrite(servoPin, int(val));
  }
  }
  
  else if(theOscMessage.checkAddrPattern("/COG/PUSH") == true) {
    
    directBackward = theOscMessage.get(0).floatValue();
    print(" addrpattern: "+theOscMessage.addrPattern());
    println(" typetag: "+theOscMessage.typetag()+"value= "+theOscMessage.get(0).floatValue());
    if ((circley >= 0)) {
    circley -= 20;
  }
  }
  
  else if(theOscMessage.checkAddrPattern("/COG/PULL") == true) {
    
    directBackward = theOscMessage.get(0).floatValue();
    print(" addrpattern: "+theOscMessage.addrPattern());
    println(" typetag: "+theOscMessage.typetag()+"value= "+theOscMessage.get(0).floatValue());
    if ((circley >= 0)) {
    circley += 20;
  }
  }
  
  else if(theOscMessage.checkAddrPattern("/AFF/Meditation") == true) {
    
    med = theOscMessage.get(0).floatValue();
  }
  else if(theOscMessage.checkAddrPattern("/AFF/Excitement") == true) {
    
    excit = theOscMessage.get(0).floatValue();
  }
}