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

screen = pygame.display.set_mode(size)
clock = pygame.time.Clock()

background = pygame.image.load("images/background.png")

soundtrack = pygame.mixer.Sound("sounds/soundtrack.ogg")
soundtrack.set_volume(0.5)
soundtrack.play(-1)
shootsound = pygame.mixer.Sound("sounds/shoot.wav")
shootsound.set_volume(0.8)
explosionsound = pygame.mixer.Sound("sounds/explosion.wav")
explosionsound.set_volume(0.9)

gun = Gun((width//2, height//2))
missiles = []
asteroids = []

counter = 0
difficulty = 1

while True:
	clock.tick(fps)
	
	counter += 1
	if counter == 5 * fps:
		difficulty += 1
		counter = 0
		
	for event in pygame.event.get():
		if event.type == pygame.QUIT:
			exit()
		elif event.type == pygame.MOUSEMOTION:
			gun.point_at(event.pos)
		elif event.type == pygame.MOUSEBUTTONDOWN:
			if len(missiles) < 4:
				missile = gun.fire()
				shootsound.play()
				if missile is not None:
					missiles.append(missile)
	
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
		
	guninfo = gun.collision_info()
	for asteroid in asteroids:
		asteroidinfo = asteroid.collision_info()
		
		if collides(asteroidinfo, guninfo) and not gun.dying:
			explosionsound.play()
			gun.dying = True
		
		for missile in missiles:
			missileinfo = missile.collision_info()
			if collides(asteroidinfo, missileinfo) and not asteroid.exploding:
				explosionsound.play()
				asteroid.exploding = True
				missiles.remove(missile)
				if asteroid.big:
					small1 = Asteroid(asteroid.position, asteroid.angle + 30, False)
					small2 = Asteroid(asteroid.position, asteroid.angle - 30, False)
					asteroids.append(small1)
					asteroids.append(small2)

	asteroids = [ asteroid for asteroid in asteroids if not asteroid.exploded ]
	
	gun.draw(screen)
	pygame.display.flip()
	
	if gun.dead:
		exit()