###############################################################################################################################################################
# How to play:
#    you have a total of 3 live
#    you have ten bullets to shoot the asteroids before they hit the gun
#    if you run out of bullets, you blow up and you lose 1 life
#    shooting a star will give you an extra 2 bullets but stars disappear if the asteroids hit them
#    The game gets harder with time
#    Aliens appear at difficulty 2
#    watch out for aliens, you lose 5 bullets if they hit the gun, however you gain 5 bullets if you shoot them
#    you need to shoot twice to kill an alien
#    Each time you get an additional score of 5, you get 1 extra bullet
#    If your score is up to 100, you get an extra life and your score count begins again
#    
#    Bonus sound by Dan Knoflicek
#    Alien.png by Bert-o-Naught
#    star.png by Rafaelchm
#    screamsound by Fernando Carmona
#    Rest of the images and sounds used from Professor John Howatt lecture Sample game
#    Game modified from Professor John Howatt's lecture sample game
###############################################################################################################################################################

from __future__ import division
import pygame
import math
import random

def collides(info1, info2):
	(image1, rect1) = info1
	(image2, rect2) = info2
	mask1 = pygame.mask.from_surface(image1)
	mask2 = pygame.mask.from_surface(image2)
	dx = rect2.left - rect1.left
	dy = rect2.top - rect1.top
	return mask1.overlap(mask2, (dx,dy)) != None


def generate_asteroid(width, height):
	zone = random.choice(["north", "south", "east", "west"])
	if zone == "north":
		x = random.randint(0, width)
		y = 0
		angle = random.randint(181, 359)
		return Asteroid((x,y), angle, True)
	elif zone == "south":
		x = random.randint(0, width)
		y = height
		angle = random.randint(1, 179)
		return Asteroid((x,y), angle, True)
	elif zone == "west":
		x = 0
		y = random.randint(0, height)
		angle = (270 + random.randint(1,179)) % 360
		return Asteroid((x,y), angle, True)
	else:
		x = width
		y = random.randint(0, height)
		angle = random.randint(91, 269)
		return Asteroid((x,y), angle, True)
		
def generate_stars(width, height):
	zone = random.choice(["north", "south", "east", "west"])
	if zone == "north":
		x = random.randint(0, width)
		y = 0
		angle = random.randint(181, 359)
		return Star((x,y), angle)
	elif zone == "south":
		x = random.randint(0, width)
		y = height
		angle = random.randint(1, 179)
		return Star((x,y), angle)
	elif zone == "west":
		x = 0
		y = random.randint(0, height)
		angle = (270 + random.randint(1,179)) % 360
		return Star((x,y), angle)
	else:
		x = width
		y = random.randint(0, height)
		angle = random.randint(91, 269)
		return Star((x,y), angle)
		
def generate_aliens(width, height):
	zone = random.choice(["north", "south", "east", "west"])
	if zone == "north":
		x = random.randint(0, width)
		y = 0
		angle = random.randint(181, 359)
		return Alien((x,y), angle)
	elif zone == "south":
		x = random.randint(0, width)
		y = height
		angle = random.randint(1, 179)
		return Alien((x,y), angle)
	elif zone == "west":
		x = 0
		y = random.randint(0, height)
		angle = (270 + random.randint(1,179)) % 360
		return Alien((x,y), angle)
	else:
		x = width
		y = random.randint(0, height)
		angle = random.randint(91, 269)
		return Alien((x,y), angle)
	
class Gun(object):
	def __init__(self, position):
		self.frames = []
		for i in range(0,17):
			self.frames.append(pygame.transform.scale(pygame.image.load("images/firing/%02d.png" % i), (125,125)))
		self.frame = 0
		self.firing = False
		self.position = position
		self.angle = 0
		
		self.dying = False
		self.explosion = pygame.image.load("images/explosion.png")
		self.dead = False
		self.deathframecount = 0
	
	def fire(self):
		if not self.firing:
			self.firing = True
			self.frame = 0
			return Missile(self.position, self.angle)
	
	def point_at(self, target):
		dx = target[0] - self.position[0]
		dy = target[1] - self.position[1]
		angle = math.degrees(math.atan2(-dx, -dy))
		self.angle += (angle - self.angle + 90) % 360
	
	def draw(self, screen):
		if self.dying:
			rect = self.explosion.get_rect()
			rect.center = self.position
			screen.blit(self.explosion, rect)
			self.deathframecount += 1
			if self.deathframecount == 2 * fps:
				self.dead = True
			return
			
		rotated = pygame.transform.rotate(self.frames[self.frame], self.angle)
		rotatedrect = rotated.get_rect()
		rotatedrect.center = self.position
		screen.blit(rotated, rotatedrect)
		
		if self.firing:
			self.frame += 1
			if self.frame == len(self.frames):
				self.frame = 0
				self.firing = False
				
	def collision_info(self):
		rotated = pygame.transform.rotate(self.frames[self.frame], self.angle)
		rotatedrect = rotated.get_rect()
		rotatedrect.center = self.position
		return (rotated, rotatedrect)
		
class Star(object):
	def __init__(self, position, angle):
		self.image = pygame.transform.scale(pygame.image.load("star.png"),(100,100))
		self.position = position
		self.angle = angle
		
	def move(self, speed):
		dx = speed * math.cos(math.radians(self.angle))
		dy = speed * math.sin(math.radians(self.angle))
		self.position = ( self.position[0] + dx, self.position[1] - dy )
		
	def draw(self, screen):
		rect = self.image.get_rect()
		rect.center = self.position
		screen.blit(self.image, rect)
	
	def on_screen(self, screen):
		rect = self.image.get_rect()
		rect.center = self.position
		return screen.get_rect().colliderect(rect)
		
	def collision_info(self):
		rect = self.image.get_rect()
		rect.center = self.position
		return (self.image, rect)
		
class Alien(object):
	def __init__(self, position, angle):
		self.image = pygame.transform.scale(pygame.image.load("alien.png"),(100,100))
		self.position = position
		self.angle = angle
		self.shottimes = 0
		
	def move(self, speed):
		dx = speed * math.cos(math.radians(self.angle))
		dy = speed * math.sin(math.radians(self.angle))
		self.position = ( self.position[0] + dx, self.position[1] - dy )
		
	def draw(self, screen):
		rect = self.image.get_rect()
		rect.center = self.position
		screen.blit(self.image, rect)
	
	def on_screen(self, screen):
		rect = self.image.get_rect()
		rect.center = self.position
		return screen.get_rect().colliderect(rect)
		
	def collision_info(self):
		rect = self.image.get_rect()
		rect.center = self.position
		return (self.image, rect)
				
class Missile(object):
	def __init__(self, position, angle):
		self.image = pygame.transform.rotate(pygame.transform.scale(pygame.image.load("images/missile.png"), (40,30)), (angle-90) % 360)
		self.position = position
		self.angle = angle
		
	def move(self, speed):
		dx = speed * math.cos(math.radians(self.angle))
		dy = speed * math.sin(math.radians(self.angle))
		self.position = ( self.position[0] + dx, self.position[1] - dy )
	
	def on_screen(self, screen):
		rect = self.image.get_rect()
		rect.center = self.position
		return screen.get_rect().colliderect(rect)
		
	def draw(self, screen):
		rect = self.image.get_rect()
		rect.center = self.position
		screen.blit(self.image, rect)
		
	def collision_info(self):
		rect = self.image.get_rect()
		rect.center = self.position
		return (self.image, rect)
		
class Asteroid(object):
	def __init__(self, position, angle, big):
		asteroid = random.choice(["a1","a3","b1","b3","c1","c3","c4"])
		self.frames = []
		for i in range(0,16):
			frame = pygame.image.load("images/asteroids/%s%04d.png" % (asteroid, i))
			if big:
				frame = pygame.transform.scale(frame, (160,120))
			else:
				frame = pygame.transform.scale(frame, (80,60))
			self.frames.append(frame)
		
		self.big = big
		self.frame = 0
		self.position = position
		self.angle = angle
		self.exploding = False
		self.explodingframe = 0
		self.exploded = False
		self.explosion = pygame.image.load("images/explosion.png")
		
	def move(self, speed):
		if not self.exploding:
			dx = speed * math.cos(math.radians(self.angle))
			dy = speed * math.sin(math.radians(self.angle))
			self.position = ( self.position[0] + dx, self.position[1] - dy )

	def on_screen(self, screen):
		rect = self.frames[self.frame//2].get_rect()
		rect.center = self.position
		return screen.get_rect().colliderect(rect)
		
	def draw(self, screen):
		if self.exploding:
			self.explodingframe += 1
			if self.explodingframe >= fps//2:
				self.exploded = True
				return
			else:
				rect = self.explosion.get_rect()
				rect.center = self.position
				screen.blit(self.explosion, rect)
		else:
			rect = self.frames[self.frame//2].get_rect()
			rect.center = self.position
			screen.blit(self.frames[self.frame//2], rect)
			self.frame += 1
			if self.frame == 2*len(self.frames):
				self.frame = 0
			
	def collision_info(self):
		rect = self.frames[self.frame//2].get_rect()
		rect.center = self.position
		return (self.frames[self.frame//2], rect)
	
pygame.init()
width = 800
height = 600
size = (width,height)
fps = 60
count = 0

screen = pygame.display.set_mode(size)
clock = pygame.time.Clock()

background = pygame.image.load("images/background.png")

alien = pygame.image.load("alien.png")


soundtrack = pygame.mixer.Sound("sounds/soundtrack.ogg")
soundtrack.set_volume(0.5)
soundtrack.play(-1)
shootsound = pygame.mixer.Sound("sounds/shoot.wav")
shootsound.set_volume(0.8)
explosionsound = pygame.mixer.Sound("sounds/explosion.wav")
explosionsound.set_volume(0.9)
bonussound = pygame.mixer.Sound("sounds/bonus.wav")
bonussound.set_volume(0.9)
screamsound = pygame.mixer.Sound("sounds/scream.wav")
screamsound.set_volume(0.9)

font = pygame.font.Font(None, 36)



gun = Gun((width//2, height//2))
missiles = []
missileCount = 10
asteroids = []

stars = []
aliens = []

counter = 0
difficulty = 1
life = 3
shots = 10
count = 0
aCount = 0
score = 0
n = 0

while True:
	clock.tick(fps)
	
	counter += 1
	count += 1
	aCount += 1
	if counter == 5 * fps:
		difficulty += 1
		counter = 0
		
	if score == n + 5:
		missileCount += 1
		n = score
		
	if score == 100:
		life += 1
		n = 0
		score = 0
		
	for event in pygame.event.get():
		if event.type == pygame.QUIT:
			exit()
		elif event.type == pygame.MOUSEMOTION:
			gun.point_at(event.pos)
		elif event.type == pygame.MOUSEBUTTONDOWN:
			if missileCount > 0:
				missile = gun.fire()
				shootsound.play()
				if missile is not None:
					missiles.append(missile)
					missileCount -= 1
					
			elif missileCount <= 0 and not gun.dying:
				explosionsound.play()
				gun.dying = True
	
	screen.blit(background, background.get_rect())

	for missile in missiles:
		missile.draw(screen)
		missile.move(3)
	missiles = [ missile for missile in missiles if missile.on_screen(screen) ]
	
	for asteroid in asteroids:
		asteroid.draw(screen)
		asteroid.move(difficulty)
	asteroids = [ asteroid for asteroid in asteroids if asteroid.on_screen(screen) ]
	while len([ asteroid for asteroid in asteroids if asteroid.big ]) < 5 + difficulty:
		asteroids.append(generate_asteroid(width,height))
		
	for star in stars:
		star.draw(screen)
		star.move(difficulty)
	stars = [star for star in stars if star.on_screen(screen) ]
		
	for alien in aliens:
		alien.draw(screen)
		alien.move(difficulty)
	aliens = [alien for alien in aliens if alien.on_screen(screen) ]
		
	if difficulty >= 1 and aCount == 200-(difficulty*5):
	    if len(aliens) < 3:
			aliens.append(generate_aliens(width,height))
			aCount = 0
		
		
	if count == difficulty * fps:
		stars.append(generate_stars(width,height))
		count = 0
		
	guninfo = gun.collision_info()
	for asteroid in asteroids:
		asteroidinfo = asteroid.collision_info()
		
		if collides(asteroidinfo, guninfo) and not gun.dying:
			explosionsound.play()
			gun.dying = True
		
		for missile in missiles:
			missileinfo = missile.collision_info()
			if collides(asteroidinfo, missileinfo) and not asteroid.exploding:
				if not asteroid.big:
					score += 2
				explosionsound.play()
				asteroid.exploding = True
				missiles.remove(missile)
				if asteroid.big:
					score += 1
					small1 = Asteroid(asteroid.position, asteroid.angle + 30, False)
					small2 = Asteroid(asteroid.position, asteroid.angle - 30, False)
					asteroids.append(small1)
					asteroids.append(small2)
		
	for star in stars:
		starinfo = star.collision_info()
		for asteroid in asteroids:
			asteroidinfo = asteroid.collision_info()
			if collides(asteroidinfo, starinfo):
				stars.remove(star)
			
		for missile in missiles:
			missileinfo = missile.collision_info()
			if collides(starinfo, missileinfo):
				bonussound.play()
				stars.remove(star)
				missileCount += 2
	
	for alien in aliens:
		alieninfo = alien.collision_info()
		for missile in missiles:
			missileinfo = missile.collision_info()
			if collides(alieninfo, missileinfo):
				screamsound.fadeout(3000)
				alien.shottimes += 1
				missiles.remove(missile)
			if alien.shottimes == 2:
				aliens.remove(alien)
				bonussound.play()
				missileCount += 5
				
		if collides(alieninfo, guninfo):
			explosionsound.play()
			aliens.remove(alien)
			missileCount -= 5
					

	asteroids = [ asteroid for asteroid in asteroids if not asteroid.exploded ]
	
	if gun.dead and life > 0:
		life = life-1
		missileCount = 10
		asteroids = []
		gun.dead = False
		gun.dying = False
		gun.deathframecount = 0
		
		
		
	gun.draw(screen)
	
	texts = font.render("Lives: %d" %life, 1, (255, 255, 255))
	rect = texts.get_rect()
	rect = rect.move(5, height - rect.height - 5)
	screen.blit(texts, rect)
	
	textTwo = font.render(("Bullets: %d" %missileCount),1, (250, 250, 250))
	rect = textTwo.get_rect()
	rect = rect.move(width - rect.width - 5, height - rect.height - 5)
	screen.blit(textTwo, rect)
	
	textThree = font.render(("Score: %d" %score),1, (250, 250, 250))
	rect = textThree.get_rect()
	rect = rect.move(width - rect.width - 5, 5)
	screen.blit(textThree, rect)
	
	pygame.display.flip()
	
	if gun.dead and life == 0:
		exit()