# -*- coding: utf-8 -*-
from __future__ import print_function

import PIL.Image
import click
import face_recognition
import json
import numpy as np
from json import JSONEncoder
import requests


class NumpyArrayEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, (np.int_, np.intc, np.intp, np.int8,
                            np.int16, np.int32, np.int64, np.uint8,
                            np.uint16, np.uint32, np.uint64)):
            return int(obj)
        elif isinstance(obj, (np.float_, np.float16, np.float32,
                              np.float64)):
            return float(obj)
        elif isinstance(obj, (np.ndarray,)):  # add this line
            return obj.tolist()  # add this line
        return json.JSONEncoder.default(self, obj)


@click.group()
def cli():
    pass


@cli.command()
@click.option('--code', required=True, help='code to compare')
@click.option('--reference', required=True, help='reference code')
def distance(code, reference):
#     code="[-0.13759247958660126, 0.0510345958173275, 0.04146739840507507, -0.005146813578903675, -0.09240668267011642, -0.08594132959842682, -0.040513649582862854, -0.1852920949459076, 0.09282500296831131, -0.08235439658164978, 0.27235984802246094, -0.057188086211681366, -0.1976281851530075, -0.11779750138521194, -0.009316883981227875, 0.12406759709119797, -0.19061914086341858, -0.08415301889181137, -0.04566233605146408, 0.01759224385023117, 0.06114708632230759, -0.00798002164810896, 0.09277407079935074, 0.03398518264293671, -0.10392067581415176, -0.30804330110549927, -0.11396662145853043, -0.10018505156040192, 0.019761603325605392, -0.012736408039927483, -0.06497238576412201, -0.02523917518556118, -0.1651887744665146, -0.08681481331586838, 0.03392038121819496, 0.12124567478895187, -0.022123871371150017, -0.07008414715528488, 0.21839720010757446, -0.06440391391515732, -0.19633662700653076, 0.016853295266628265, 0.05880601331591606, 0.2358706295490265, 0.21294695138931274, 0.01647460088133812, -0.023862598463892937, -0.1426168829202652, 0.11379577964544296, -0.1845851093530655, 0.042932603508234024, 0.11033572256565094, 0.06082820147275925, 0.04239927977323532, -0.003706483170390129, -0.12167409807443619, 0.05911057069897652, 0.14130356907844543, -0.1320601999759674, -0.004314917139708996, 0.08183567970991135, -0.09244123101234436, -0.0031023984774947166, -0.035204220563173294, 0.24001085758209229, 0.08710610121488571, -0.10523464530706406, -0.15491792559623718, 0.06391990929841995, -0.1487291008234024, -0.09561851620674133, 0.05617906153202057, -0.19378075003623962, -0.1812225729227066, -0.3664725124835968, 0.014546608552336693, 0.42002230882644653, 0.08556631952524185, -0.20543958246707916, 0.00668401550501585, -0.05633503943681717, 0.007122093345969915, 0.16476579010486603, 0.13397356867790222, -0.010082184337079525, -0.00806070864200592, -0.16103564202785492, 0.010027166455984116, 0.21457438170909882, -0.09679678082466125, -0.04517609626054764, 0.20417886972427368, 0.020657982677221298, 0.09716451913118362, 0.062355175614356995, 0.02397884428501129, -0.0474812313914299, 0.01970345713198185, -0.16913942992687225, -8.618137799203396E-4, 0.08779779076576233, -0.029192574322223663, 0.028546489775180817, 0.13811053335666656, -0.1639222502708435, 0.07030592858791351, 0.019869131967425346, -0.010377503000199795, 0.0571388341486454, -0.07451233267784119, -0.08769403398036957, -0.07205472141504288, 0.1365070641040802, -0.20450393855571747, 0.21574024856090546, 0.19225072860717773, 0.08726317435503006, 0.1281084418296814, 0.0975717157125473, 0.057235851883888245, -0.048299528658390045, 0.06238628551363945, -0.177903413772583, 0.025292882695794106, 0.06034830957651138, 0.008733147755265236, 0.1154644712805748, 0.009837906807661057]"
#     reference="[-0.12759308516979218, 0.06122016906738281, 0.04346586763858795, -0.029220139607787132, -0.11282569915056229, -0.0734272226691246, -0.04733334481716156, -0.1807301789522171, 0.10537667572498322, -0.09084609895944595, 0.25432461500167847, -0.06602898985147476, -0.17583179473876953, -0.10035666078329086, -0.02487146109342575, 0.1516769826412201, -0.19196997582912445, -0.06204793602228165, -0.07213005423545837, -0.03680295869708061, 0.08120047301054001, 0.003208584152162075, 0.08664976060390472, 0.04271998628973961, -0.12403722107410431, -0.33727315068244934, -0.07975935190916061, -0.096137136220932, 0.023353757336735725, -0.011491441167891026, -0.06260628253221512, 0.013343451544642448, -0.1777184009552002, -0.05604896694421768, 0.023320011794567108, 0.10626544803380966, -0.0351766012609005, -0.07563802599906921, 0.19454577565193176, -0.06925338506698608, -0.19185474514961243, -0.02345963381230831, 0.06568662077188492, 0.2019696831703186, 0.20880550146102905, 0.042672835290431976, -0.008357187733054161, -0.12119979411363602, 0.10696416348218918, -0.20581915974617004, 0.03099277801811695, 0.08725424110889435, 0.015631793066859245, 0.04451507329940796, 0.021394997835159302, -0.12937507033348083, 0.055140987038612366, 0.10052409768104553, -0.10740318894386292, -0.034583333879709244, 0.06450145691633224, -0.12084007263183594, -0.048450153321027756, -0.06411993503570557, 0.26812466979026794, 0.09971063584089279, -0.09482178092002869, -0.14556729793548584, 0.05333561450242996, -0.1564992517232895, -0.08425255864858627, 0.04571535438299179, -0.15126310288906097, -0.15956507623195648, -0.3565978705883026, -0.004591150209307671, 0.41136878728866577, 0.11363056302070618, -0.20055432617664337, 0.007717195898294449, -0.05651985481381416, 0.018084799870848656, 0.19724687933921814, 0.12374819070100784, -0.014740435406565666, 0.007654115557670593, -0.14328020811080933, 0.022970939055085182, 0.20244494080543518, -0.11877480894327164, -0.02981872484087944, 0.24971342086791992, 0.011533288285136223, 0.10766896605491638, 0.013593453913927078, 0.0014790466520935297, -0.07391700148582458, 0.05079347640275955, -0.11084232479333878, 0.0030396776273846626, 0.1541428416967392, 0.021249178797006607, 0.04757579416036606, 0.13109125196933746, -0.17587429285049438, 0.07103108614683151, -0.015553569421172142, 0.025229638442397118, 0.09649762511253357, -0.08384203165769577, -0.10357318073511124, -0.09613631665706635, 0.12652282416820526, -0.20704974234104156, 0.17152215540409088, 0.19682326912879944, 0.04867592081427574, 0.14016690850257874, 0.11064399033784866, 0.05517689883708954, -0.02591029554605484, 0.04386834800243378, -0.20914773643016815, 0.04777367785573006, 0.10393493622541428, -0.02268311008810997, 0.0698806568980217, 0.0042822156101465225]"
    results = face_recognition.face_distance(np.array([json.loads(code)]), np.array(json.loads(reference)))
    if len(results) > 0:
        print(json.dumps(results, cls=NumpyArrayEncoder))
    else:
        print("[]")


@cli.command()
@click.option('--image', required=True, help='image to encode')
def encode(image):
    unknown_image = face_recognition.load_image_file(image)
    # Scale down image if it's giant so things run a little faster
    if max(unknown_image.shape) > 1600:
        pil_img = PIL.Image.fromarray(unknown_image)
        pil_img.thumbnail((1600, 1600), PIL.Image.LANCZOS)
        unknown_image = np.array(pil_img)

    unknown_encodings = face_recognition.face_encodings(unknown_image)
    if len(unknown_encodings) > 0:
        print(json.dumps(unknown_encodings, cls=NumpyArrayEncoder))
    else:
        print("[]")


@cli.command()
@click.option('--image', required=True, help='image to locate')
def locate(image):
    unknown_image = face_recognition.load_image_file(image)
    # Scale down image if it's giant so things run a little faster
    if max(unknown_image.shape) > 1600:
        pil_img = PIL.Image.fromarray(unknown_image)
        pil_img.thumbnail((1600, 1600), PIL.Image.LANCZOS)
        unknown_image = np.array(pil_img)

    unknown_locations = face_recognition.face_locations(unknown_image)
    if len(unknown_locations) > 0:
        print(json.dumps(unknown_locations, cls=NumpyArrayEncoder))
    else:
        print("[]")


@cli.command()
@click.option('--code', required=True, help='face code to match')
@click.option('--cloud', default='127.0.0.1:8080', help='cloud server address, host:port')
def match(code, cloud):
    results = []
    url = 'http://{}/api/media-files/human-face-codes'.format(cloud)
    r = requests.get(url)
    if r.status_code != 200:
        print(results)
        return
    face_codes = r.json()
    codes = [face_code['code'] for face_code in face_codes]
    matches = face_recognition.compare_faces(np.array(codes), np.array(json.loads(code)))
    for i, match in enumerate(matches):
        if match:
            results.append(codes[i]['sourceId'])
    print(results)


if __name__ == "__main__":
    cli()
